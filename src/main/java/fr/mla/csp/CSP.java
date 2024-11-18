package fr.mla.csp;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.mla.NoSolutionException;

public abstract class CSP<V extends Variable<?, U>, U extends Value<?>> {

  protected Set<V> variables;

  public Map<V, U> backtrackingSearch() throws NoSolutionException {
    return backtrackingSearch(new HashMap<>(), getInitialDomains());
  }

  private Map<V, Set<U>> getInitialDomains() {
    Map<V, Set<U>> domains = new HashMap<>();
    variables.forEach(v -> domains.put(v, v.getInitialDomainValues()));
    return domains;
  }

  private Map<V, U> backtrackingSearch(Map<V, U> assignment, Map<V, Set<U>> domains) throws NoSolutionException {
    if (isComplete(assignment)) {
      return assignment;
    }
    V variable = selectUnassigned(assignment);
    for (U value : variable.getInitialDomainValues()) {
      if (isConsistent(variable, value, assignment)) {
        assignment.put(variable, value);
        try {
          Map<V, Set<U>> domainsLookAhead = lookAhead(domains);
          return backtrackingSearch(assignment, domainsLookAhead);
        } catch (NoSolutionException e) {
          assignment.remove(variable);
        }
      }
    }
    throw new NoSolutionException();
  }

  private Map<V, Set<U>> lookAhead(Map<V, Set<U>> domains) {
    Map<V, Set<U>> domainsAhead = new HashMap<>();
    domains.forEach((key, value) -> {
      Set<U> prunedDomain = new HashSet<>(value);
      domainsAhead.put(key, prunedDomain);
    });
    return domainsAhead;
  }

  private boolean isComplete(Map<V, U> assignment) {
    return assignment.size() == variables.size();
  }

  protected V selectUnassigned(Map<V, U> assignment) {
    return variables.stream()
        .filter(v -> !assignment.containsKey(v))
        .min(Comparator.comparingInt(o -> o.getInitialDomainValues().size()))
        .orElseThrow();
  }

  protected abstract boolean isConsistent(V variable, U value, Map<V, U> assignment);

}
