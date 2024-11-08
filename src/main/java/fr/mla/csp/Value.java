package fr.mla.csp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Value<T> {

  T v;

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

}
