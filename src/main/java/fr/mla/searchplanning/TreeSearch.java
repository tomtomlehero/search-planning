package fr.mla.searchplanning;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import fr.mla.searchplanning.Tree.Node;
import fr.mla.searchplanning.problem.npuzzle.eightpuzzle.NPuzzleProblem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TreeSearch {

  public static void main(String[] args) {

    Problem problem = new NPuzzleProblem(3, 1, 2, 4, 7, 0, 6, 8, 5);

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

    long expandedNodes = 0;

    while (true) {
      if (fringe.isEmpty()) {
        log.info("Expanded nodes : {}", expandedNodes);
        throw new NoSolutionException();
      }

      Node<State> node = fringe.poll();
      if (problem.isTerminal(node.getValue())) {
        log.info("Expanded nodes : {}", expandedNodes);
        return node;
      }

      if (closedSet.contains(node.getValue())) {
        continue;
      }
      closedSet.add(node.getValue());

      for (Successor i : node.getValue().getSuccessors()) {
        expandedNodes++;
        Node<State> child = new Node<>(i.getState(), i.getCost() + node.getBackwardCost());
        node.addChild(child);
        fringe.add(child);
      }
    }
  }

}