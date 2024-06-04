package fr.mla.searchplanning;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import fr.mla.searchplanning.Tree.Node;
import fr.mla.searchplanning.problem.npuzzle.NPuzzleProblem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TreeSearch {

  public static void main(String[] args) {

    Problem problem = new NPuzzleProblem(3, 11, 2, 8, 12, 5, 7, 1, 6, 14, 9, 15, 0, 4, 13, 10);

    try {
      Node<State> goal = doSearch(problem);

      Node<State> n = goal;
      int i = 0;
      log.info("Found ;-)");
      while (n != null) {
        log.info("{} {}", i++, n);
        n = n.getParent();
      }

    } catch (NoSolutionException e) {
      log.error("No solution found :-(");
    }
  }


  private static Node<State> doSearch(Problem problem) throws NoSolutionException {

    State initialState = problem.getInitialState();
    Tree<State> searchTree = new Tree<>(initialState);
    PriorityQueue<Node<State>> fringe = new PriorityQueue<>();
    fringe.add(searchTree.getRoot());
    Set<State> closedSet = new HashSet<>();

    while (true) {
      if (fringe.isEmpty()) {
        throw new NoSolutionException();
      }

      Node<State> node = fringe.poll();
      if (problem.isTerminal(node.getValue())) {
        log.info("Expanded nodes: {}", expandedNodes);
        return node;
      }

      if (closedSet.contains(node.getValue())) {
        continue;
      }
      closedSet.add(node.getValue());

      for (Successor i : node.getValue().getSuccessors()) {
        logProgression(node);
        Node<State> child = new Node<>(i.getState(), i.getCost() + node.getBackwardCost());
        node.addChild(child);
        fringe.add(child);
      }
    }
  }

  private static void logProgression(Node node) {
    if (++expandedNodes % 100_000 == 0) {
      log.info("{}", node.getValue());
      log.info("Expanded nodes : {}", expandedNodes);
      log.info("Backward cost: {} - Forward cost: {} - Estimated cost: {}", node.getBackwardCost(), node.getForwardCost(),
          node.getBackwardCost() + node.getForwardCost());
    }

  }

  private static long expandedNodes = 0;

}