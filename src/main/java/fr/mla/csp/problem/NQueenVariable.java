package fr.mla.csp.problem;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import fr.mla.csp.Variable;
import static fr.mla.csp.problem.NQueen.N;

public class NQueenVariable extends Variable<Integer, NQueenValue> {

  public NQueenVariable(Integer value) {
    this.set(value);
  }


  @Override
  public Set<NQueenValue> getInitialDomainValues() {
    Set<NQueenValue> orderDomainValues = new HashSet<>();
    IntStream.range(0, N).forEach(k -> orderDomainValues.add(new NQueenValue(k)));
    return orderDomainValues;
  }

}
