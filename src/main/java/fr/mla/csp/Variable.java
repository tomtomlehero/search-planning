package fr.mla.csp;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Variable<T> {

  T value;

  protected abstract List<Value> getOrderDomainValues();

}
