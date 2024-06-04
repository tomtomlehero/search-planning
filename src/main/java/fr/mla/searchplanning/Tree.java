package fr.mla.searchplanning;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
public class Tree<S extends State> {

  private final Node<S> root;

  public Tree(S root) {
    this.root = new Node<>(root, 0);
  }

  @Getter
  @ToString(onlyExplicitlyIncluded = true)
  @EqualsAndHashCode
  public static class Node<S extends State> implements Comparable<Node<S>> {

    @ToString.Include
    private final S value;

    @ToString.Include
    private final long backwardCost;

    private Node<S> parent;

    private final List<Node<S>> children = new ArrayList<>();


    public Node(S value, long backwardCost) {
      this.value = value;
      this.backwardCost = backwardCost;
    }

    public long getForwardCost() {
      return value.getHeuristic();
    }

    public void addChild(Node<S> child) {
      child.parent = this;
      children.add(child);
    }

    public long getCost() {
      return getBackwardCost() + getForwardCost();
    }

    @Override
    public int compareTo(Node<S> o) {
      return Long.compare(getCost(), o.getCost());
    }

  }

}
