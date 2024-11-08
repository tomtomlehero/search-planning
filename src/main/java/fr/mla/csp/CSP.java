package fr.mla.csp;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.mla.NoSolutionException;

public abstract class CSP<V extends Variable<?, U>, U extends Value<?>> {

  protected Set<V> variables;

  public Map<V, U> backtrackingSearch() throws NoSolutionException {
    return backtrackingSearch(new HashMap<>());
  }

  private Map<V, U> backtrackingSearch(Map<V, U> assignment) throws NoSolutionException {
    if (isComplete(assignment)) {
      return assignment;
    }
    V variable = selectUnassigned(assignment);
    for (U value : variable.getOrderDomainValues()) {
      if (isConsistent(variable, value, assignment)) {
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

  private boolean isComplete(Map<V, U> assignment) {
    return assignment.size() == variables.size();
  }

  protected V selectUnassigned(Map<V, U> assignment) {
    return variables.stream()
        .filter(v -> !assignment.containsKey(v))
        .min(Comparator.comparingInt(o -> o.getOrderDomainValues().size()))
        .orElseThrow();
  }

  protected abstract boolean isConsistent(V variable, U value, Map<V, U> assignment);

}
