package fr.mla.csp.problem;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import fr.mla.NoSolutionException;
import fr.mla.csp.CSP;
import fr.mla.csp.Variable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

  public static void main(String[] args) {
    NQueen nQueen = new NQueen();
    try {
      Map<NQueenVariable, NQueenValue> assignment = nQueen.backtrackingSearch();
      for (NQueenVariable v : assignment.keySet().stream().sorted(Comparator.comparing(Variable::get)).toList()) {
        log.info("{}", assignment.get(v));
      }
    } catch (NoSolutionException e) {
      log.error("No Solution Found");
    }
  }

}
