package fr.mla.csp.problem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import fr.mla.csp.Assignment;
import fr.mla.csp.CSP;

public class NQueen extends CSP<NQueenVariable, NQueenValue> {

  static final int N = 8;

  public NQueen() {
    Set<NQueenVariable> variables = new HashSet<>();
    IntStream.range(0, N).forEach(i -> variables.add(new NQueenVariable(i)));
    for (int i = 0; i < N; i++) {
      variables.add(new NQueenVariable(i));
    }
    Map<NQueenVariable, List<NQueenValue>> domain = null;
    this.variables = variables;
    this.domain = domain;
  }

  @Override
  protected NQueenVariable selectUnassigned(HashMap<NQueenVariable, NQueenValue> assignment) {
    return null;
  }

  @Override
  protected boolean isConsistent(NQueenValue value, HashMap<NQueenVariable, NQueenValue> assignment) {
    return true;
  }

  @Override
  protected Assignment emptyAssignment() {
    return new Assignment() {
      @Override
      protected boolean isComplete() {
        return false;
      }
    };
  }

}
