package fr.mla.csp.problem;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import fr.mla.csp.CSP;

public class NQueen extends CSP<NQueenVariable, NQueenValue> {

  static final int N = 25;

  public NQueen() {
    Set<NQueenVariable> variables = new HashSet<>();
    IntStream.range(0, N).forEach(i -> variables.add(new NQueenVariable(i)));
    this.variables = variables;
  }

  @Override
  protected boolean isConsistent(NQueenVariable variable, NQueenValue value, Map<NQueenVariable, NQueenValue> assignment) {
    for (Map.Entry<NQueenVariable, NQueenValue> entry : assignment.entrySet()) {
      if (entry.getValue().get().equals(value.get())) {
        return false;
      }
      if (Math.abs(entry.getValue().get() - value.get()) == Math.abs(entry.getKey().get() - variable.get())) {
        return false;
      }
    }
    return true;
  }

}
