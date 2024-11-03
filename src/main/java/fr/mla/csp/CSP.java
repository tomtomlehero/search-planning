package fr.mla.csp;

import fr.mla.NoSolutionException;

import java.util.Set;

public abstract class CSP {

  Set<Variable> variables;

  public Assignment backtrackingSearch() throws NoSolutionException {
    return backtrackingSearch(emptyAssignment());
  }

  private Assignment backtrackingSearch(Assignment assignment) throws NoSolutionException {
    if (assignment.isComplete()) {
      return assignment;
    }
    Variable variable = selectUnassigned(assignment);
    for (Value value : variable.getOrderDomainValues()) {
      if (isConsistent(value, assignment)) {
        assignment.add(variable, value);
        try {
          return backtrackingSearch(assignment);
        } catch (NoSolutionException e) {
          assignment.remove(variable, value);
        }
      }
    }
    throw new NoSolutionException();
  }

  protected abstract Variable selectUnassigned(Assignment assignment);

  protected abstract boolean isConsistent(Value value, Assignment assignment);

  protected abstract Assignment emptyAssignment();

}
