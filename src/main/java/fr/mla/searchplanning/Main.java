package fr.mla.searchplanning;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import fr.mla.searchplanning.Tree.Node;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args) {

    Problem problem = new EightPuzzleProblem(3, 1, 2, 4, 7, 0, 6, 8, 5);

    try {
      State goal = treeSearch(problem);
      log.info("Found ;-) {}", goal);
    } catch (NoSolutionException e) {
      log.error("No solution found :-(");
    }
  }


  private static State treeSearch(Problem problem) throws NoSolutionException {

    State initialState = problem.getInitialState();
    Tree<State> searchTree = new Tree<>(initialState);
    PriorityQueue<Node<State>> fringe = new PriorityQueue<>((o1, o2) -> 0);
    fringe.add(searchTree.getRoot());
    Set<State> closedSet = new HashSet<>();

    while (true) {
      if (fringe.isEmpty()) {
        throw new NoSolutionException();
      }

      Node<State> node = fringe.poll();
      if (problem.isTerminal(node.getValue())) {
        return node.getValue();
      }

      if (closedSet.contains(node.getValue())) {
        continue;
      }
      closedSet.add(node.getValue());

      List<Node<State>> successors = node.getValue().getSuccessors().stream().map(Node::new).toList();

      node.addChildren(successors);
      fringe.addAll(successors);
    }
  }

}