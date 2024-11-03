package fr.mla.csp.problem;

import fr.mla.csp.Assignment;
import fr.mla.csp.CSP;
import fr.mla.csp.Value;
import fr.mla.csp.Variable;

public class NQueen extends CSP {

  @Override
  protected Variable selectUnassigned(Assignment assignment) {
    return null;
  }

  @Override
  protected boolean isConsistent(Value value, Assignment assignment) {
    return false;
  }

  @Override
  protected Assignment emptyAssignment() {
    return null;
  }

}
