package fr.mla.searchplanning;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
public class Tree<S> {

  private final Node<S> root;

  public Tree(S root) {
    this.root = new Node<>(root);
  }

  @Getter
  @ToString(onlyExplicitlyIncluded = true)
  public static class Node<S> {

    @ToString.Include
    private final S value;

    @ToString.Include
    private long backwardCost;

    private Node<S> parent;

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
