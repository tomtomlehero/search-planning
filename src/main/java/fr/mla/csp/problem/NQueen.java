package fr.mla.csp.problem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.mla.csp.Assignment;
import fr.mla.csp.CSP;
import fr.mla.csp.Value;

public class NQueen extends CSP<NQueenVariable> {

  private static final int N = 8;

  public NQueen() {
    Set<NQueenVariable> variables = new HashSet<>();
    for (int i = 0; i < N; i++) {
      variables.add(new NQueenVariable(i));
    }
    Map<NQueenVariable, List<Value>> domain = null;
    this.variables = variables;
    this.domain = domain;
  }

  @Override
  protected NQueenVariable selectUnassigned(HashMap<NQueenVariable, Value> assignment) {
    return null;
  }

  @Override
  protected boolean isConsistent(Value value, HashMap<NQueenVariable, Value> assignment) {
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
