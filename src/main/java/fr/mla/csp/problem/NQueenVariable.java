package fr.mla.csp.problem;

import java.util.List;

import fr.mla.csp.Value;
import fr.mla.csp.Variable;

public class NQueenVariable extends Variable<Integer> {

  public NQueenVariable(Integer value) {
    this.setValue(value);
  }

  @Override
  public List<Value> getOrderDomainValues() {
    return List.of();
  }

}
