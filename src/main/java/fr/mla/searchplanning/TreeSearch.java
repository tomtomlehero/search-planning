package fr.mla.searchplanning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import fr.mla.NoSolutionException;
import fr.mla.searchplanning.Tree.Node;
import fr.mla.searchplanning.problem.npuzzle.NPuzzleProblem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TreeSearch {

  public static void main(String[] args) {

    Problem problem =
        new NPuzzleProblem(1, 2, 3, 9, 4, 5, 13, 7, 8, 6, 10, 11, 14, 12, 15, 21, 16, 17, 18, 19, 20, 27, 22, 23, 24, 25, 0, 32, 28, 29, 30,
            31, 33, 26, 34, 35);

    try {
      Node<State> goal = doSearch(problem);
      log.info("Found ;-)");

      showSolution(goal);

    } catch (NoSolutionException e) {
      log.error("No solution found :-(");
    }
  }

  private static void showSolution(Node<State> goal) {
    List<State> s = new ArrayList<>();
    Node<State> n = goal;
    while (n != null) {
      s.add(n.getValue());
      n = n.getParent();
    }
    Collections.reverse(s);
    s.forEach(move -> {

      try {
        System.out.println(move);

//      log.info("\f");
//      log.info("{}", move);
        Thread.sleep(300);
        Runtime.getRuntime().exec("cls");
      } catch (InterruptedException | IOException e) {
      }
    });
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