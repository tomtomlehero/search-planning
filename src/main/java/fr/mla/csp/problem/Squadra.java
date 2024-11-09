package fr.mla.csp.problem;

import fr.mla.NoSolutionException;
import fr.mla.csp.CSP;
import fr.mla.csp.Variable;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Squadra extends CSP<SquadraVariable, SquadraValue> {

  static final int PILOT_NUMBER = 6;

  public Squadra() {
    Set<SquadraVariable> variables = new HashSet<>();
    for (SquadraVariable.Role role : SquadraVariable.Role.values()) {
      variables.add(new SquadraVariable(new SquadraVariable.Leg(1, 1, 345, 1.5), role));
      variables.add(new SquadraVariable(new SquadraVariable.Leg(1, 2, 235, 1.8), role));
      variables.add(new SquadraVariable(new SquadraVariable.Leg(1, 3, 258, 2.4), role));
    }
    this.variables = variables;
  }

  @Override
  protected boolean isConsistent(SquadraVariable variable, SquadraValue value, Map<SquadraVariable, SquadraValue> assignment) {
    if (!isXXXX1(variable, value, assignment)) {
      return false;
    }
    if (!isXXXX2(variable, value, assignment)) {
      return false;
    }
    if (!isXXXX3(variable, value, assignment)) {
      return false;
    }
    if (!isXXXX4(variable, value, assignment)) {
      return false;
    }
    return true;
  }

  private boolean isXXXX1(SquadraVariable variable, SquadraValue value, Map<SquadraVariable, SquadraValue> assignment) {
    return true;
  }

  private boolean isXXXX2(SquadraVariable variable, SquadraValue value, Map<SquadraVariable, SquadraValue> assignment) {
    return true;
  }

  private boolean isXXXX3(SquadraVariable variable, SquadraValue value, Map<SquadraVariable, SquadraValue> assignment) {
    return true;
  }

  private boolean isXXXX4(SquadraVariable variable, SquadraValue value, Map<SquadraVariable, SquadraValue> assignment) {
    return true;
  }





  public static void main(String[] args) {
    Squadra squadra = new Squadra();
    try {
      Map<SquadraVariable, SquadraValue> assignment = squadra.backtrackingSearch();
      for (SquadraVariable v : assignment.keySet().stream().sorted(Comparator.comparing(Variable::get)).toList()) {
        log.info("{} : {}", v, assignment.get(v));
      }
    } catch (NoSolutionException e) {
      log.error("No Solution Found");
    }
  }

}
