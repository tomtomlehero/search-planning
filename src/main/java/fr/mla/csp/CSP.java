package fr.mla.csp;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.mla.NoSolutionException;

public abstract class CSP<V extends Variable<?, U>, U extends Value<?>> {

  protected Set<V> variables;
  protected Map<V, List<U>> domain;

  public Map<V, U> backtrackingSearch() throws NoSolutionException {
    return backtrackingSearch(new HashMap<>());
  }

  private HashMap<V, U> backtrackingSearch(HashMap<V, U> assignment) throws NoSolutionException {
    if (isComplete(assignment)) {
      return assignment;
    }
    V variable = selectUnassigned(assignment);
    for (U value : domain.get(variable)) {
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

  private boolean isComplete(HashMap<V, U> assignment) {
    return assignment.size() == variables.size();
  }

  protected V selectUnassigned(HashMap<V, U> assignment) {
    return variables.stream()
        .filter(v -> !assignment.containsKey(v))
        .min(Comparator.comparingInt(o -> o.getOrderDomainValues().size()))
        .orElseThrow();
  }

  protected abstract boolean isConsistent(U value, HashMap<V, U> assignment);

  protected abstract Assignment emptyAssignment();

}
