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
    V variable = selectUnassigned(assignment, domains);
    for (U value : domains.get(variable)) {
      if (isConsistent(variable, value, assignment)) {
        assignment.put(variable, value);
        try {
          Map<V, Set<U>> domainsLookAhead = lookAhead(assignment, domains, variable, value);
          if (allDomainsNotEmpty(domainsLookAhead)) {
            return backtrackingSearch(assignment, domainsLookAhead);
          }
        } catch (NoSolutionException e) {
          assignment.remove(variable);
        }
      }
    }
    throw new NoSolutionException();
  }

  private boolean allDomainsNotEmpty(Map<V, Set<U>> domains) {
    return domains.values().stream().noneMatch(Set::isEmpty);
  }

  private Map<V, Set<U>> lookAhead(Map<V, U> assignment, Map<V, Set<U>> domains, V variable, U value) {
    Map<V, Set<U>> domainsAhead = new HashMap<>();
    domains.forEach((v, values) -> {
      Set<U> prunedDomain = new HashSet<>(values);
      if (v.get() == variable.get()) {
        prunedDomain.removeIf(u -> !u.get().equals(value.get()));
      } else {
        prunedDomain.removeIf(u -> !isConsistent(v, u, assignment));
      }
      domainsAhead.put(v, prunedDomain);
    });
    return domainsAhead;
  }

  private boolean isComplete(Map<V, U> assignment) {
    return assignment.size() == variables.size();
  }

  protected V selectUnassigned(Map<V, U> assignment, Map<V, Set<U>> domains) {
    return variables.stream()
        .filter(v -> !assignment.containsKey(v))
        .min(Comparator.comparingInt(o -> domains.get(o).size()))
        .orElseThrow();
  }

  protected abstract boolean isConsistent(V variable, U value, Map<V, U> assignment);

}
