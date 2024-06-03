package fr.mla.searchplanning;

import fr.mla.searchplanning.Tree.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

@Slf4j
public class Main {

    public static void main(String[] args) {

        Problem problem = new Problem() {
            @Override
            public State getInitialState() {
                return new State() {
                };
            }

            @Override
            public List<State> getSuccessors(State state) {
                return List.of();
            }

            @Override
            public boolean isTerminal(State state) {
                return false;
            }
        };

        try {
            State goal = treeSearch(problem);
            log.info("Found !! {}", goal);
        } catch (NoSolutionException e) {
            log.error("No solution found");
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

            List<Node<State>> successors = problem.getSuccessors(node.getValue())
                    .stream().map(Node::new).toList();

            node.addChildren(successors);
            fringe.addAll(successors);
        }
    }

}