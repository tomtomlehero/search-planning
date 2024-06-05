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

    Problem problem =
        new NPuzzleProblem(12, 20, 10, 14, 3, 5, 6, 7, 8, 9, 21, 13, 23, 22, 4, 15, 16, 17, 18, 19, 2, 11, 1, 24, 25, 35, 26, 27, 28, 29,
            30, 31, 32, 33, 44, 34, 36, 37, 38, 39, 40, 41, 42, 43, 45, 55, 47, 48, 58, 49, 50, 51, 52, 53, 54, 65, 46, 67, 59, 78, 60, 61,
            62, 63, 75, 66, 57, 56, 68, 69, 70, 71, 72, 84, 64, 74, 87, 76, 79, 89, 80, 81, 82, 93, 73, 83, 85, 77, 98, 88, 90, 91, 0, 92,
            94, 95, 86, 96, 97, 99);

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

  private static void logProgression(Node<State> node) {
    if (++expandedNodes % 100_000 == 0) {
      log.info("{}", node.getValue());
      log.info("Expanded nodes : {}", expandedNodes);
      log.info("Backward cost: {} - Forward cost: {} - Estimated cost: {}", node.getBackwardCost(), node.getForwardCost(),
          node.getBackwardCost() + node.getForwardCost());
    }

  }

  private static long expandedNodes = 0;

}