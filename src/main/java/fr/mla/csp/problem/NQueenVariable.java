package fr.mla.csp.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import fr.mla.csp.Variable;
import static fr.mla.csp.problem.NQueen.N;

public class NQueenVariable extends Variable<Integer, NQueenValue> {

  public NQueenVariable(Integer value) {
    this.set(value);
  }

  @Override
  public List<NQueenValue> getOrderDomainValues() {
    List<NQueenValue> orderDomainValues = new ArrayList<>();
    IntStream.range(0, N).forEach(k -> orderDomainValues.add(new NQueenValue(k)));
    return orderDomainValues;
  }

}
