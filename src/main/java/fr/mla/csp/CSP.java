package fr.mla.csp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.mla.NoSolutionException;

public abstract class CSP<V extends Variable<?>> {

  protected Set<V> variables;
  protected Map<V, List<Value>> domain;

  public Map<V, Value> backtrackingSearch() throws NoSolutionException {
    return backtrackingSearch(new HashMap<>());
  }

  private HashMap<V, Value> backtrackingSearch(HashMap<V, Value> assignment) throws NoSolutionException {
    if (isComplete(assignment)) {
      return assignment;
    }
    V variable = selectUnassigned(assignment);
    for (Value value : variable.getOrderDomainValues()) {
      if (isConsistent(value, assignment)) {
        assignment.put(variable, value);
        try {
          return backtrackingSearch(assignment);
        } catch (NoSolutionException e) {
          assignment.remove(variable);
        }
      }
    }
    throw new NoSolutionException();
  }

  private boolean isComplete(HashMap<V, Value> assignment) {
    return assignment.size() == variables.size();
  }

  protected abstract V selectUnassigned(HashMap<V, Value> assignment);

  protected abstract boolean isConsistent(Value value, HashMap<V, Value> assignment);

  protected abstract Assignment emptyAssignment();

}
