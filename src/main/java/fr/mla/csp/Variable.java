package fr.mla.csp;

import java.util.Set;

public abstract class Variable<T extends Comparable<T>, U extends Value<?>> {

  protected T v;

  public T get() {
    return v;
  }

  public void set(T v) {
    this.v = v;
  }

  @Override
  public String toString() {
    return v.toString();
  }

  protected abstract Set<U> getInitialDomainValues();

}
