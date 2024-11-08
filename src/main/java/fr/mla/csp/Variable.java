package fr.mla.csp;

import java.util.List;

public abstract class Variable<T, U extends Value<?>> {

  T v;

  public T get() {
    return v;
  }

  public void set(T v) {
    this.v = v;
  }

  protected abstract List<U> getOrderDomainValues();

}
