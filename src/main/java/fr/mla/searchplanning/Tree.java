package fr.mla.searchplanning;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Tree<S> {

    private final Node<S> root;

    public Tree(S root) {
        this.root = new Node<>(root);
    }

    public static class Node<S> {

        @Getter
        private final S value;

        private Node<S> parent;

        @Getter
        private final List<Node<S>> children = new ArrayList<>();

        public Node(S s) {
            this.value = s;
        }

        public void addChild(Node<S> child) {
            child.parent = this;
            children.add(child);
        }

        public void addChildren(List<Node<S>> children) {
            children.forEach(this::addChild);
        }

    }


}
