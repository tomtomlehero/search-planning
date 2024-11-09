package fr.mla.csp;

import java.util.List;

public abstract class Variable<T, U extends Value<?>> {

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

  protected abstract List<U> getOrderDomainValues();

}
