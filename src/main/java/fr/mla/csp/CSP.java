package fr.mla.csp;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.mla.NoSolutionException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CSP<V extends Variable<?, U>, U extends Value<?>> {

  private long visitedNodes = 0;

  protected Set<V> variables;

  public Map<V, U> backtrackingSearch() throws NoSolutionException {
    Map<V, U> assignments = backtrackingSearch(new HashMap<>(), getInitialDomains());
    log.info("Visited nodes: {}", visitedNodes);
    return assignments;
  }

  private Map<V, U> backtrackingSearch(Map<V, U> assignment, Map<V, Set<U>> domains) throws NoSolutionException {
    if (isComplete(assignment)) {
      return assignment;
    }
    V variable = selectUnassigned(assignment, domains);
    for (U value : domains.get(variable)) {
      visitedNodes++;
      if (isConsistent(variable, value, assignment)) {
        assignment.put(variable, value);
        try {
          Map<V, Set<U>> domainsLookAhead = lookAhead(assignment, domains, variable, value);
//          log.info("Assignment {}", assignment);
//          log.info("Assigning {} := {}", variable, value);
//          log.info("Domain after {}", domainsLookAhead);
          if (anyDomainsEmpty(domainsLookAhead)) {
            continue;
          }
          return backtrackingSearch(assignment, domainsLookAhead);

        } catch (NoSolutionException e) {
          assignment.remove(variable);
        }
      }
    }
    throw new NoSolutionException();
  }

  private boolean anyDomainsEmpty(Map<V, Set<U>> domains) {
    return domains.values().stream().anyMatch(Set::isEmpty);
  }

  private Map<V, Set<U>> lookAhead(Map<V, U> assignment, Map<V, Set<U>> domains, V variable, U value) {
    Map<V, Set<U>> domainsAhead = new HashMap<>();
    domains.forEach((v, values) -> {
      Set<U> prunedDomain = new HashSet<>(values);
      if (!assignment.containsKey(v)) {
        if (v.get() == variable.get()) {
          prunedDomain.removeIf(u -> !u.get().equals(value.get()));
        } else {
          prunedDomain.removeIf(u -> !isConsistent(v, u, assignment));
        }
      }
      domainsAhead.put(v, prunedDomain);
    });
    return domainsAhead;
  }

  private boolean isComplete(Map<V, U> assignment) {
    return assignment.size() == variables.size();
  }


  private Map<V, Set<U>> getInitialDomains() {
    Map<V, Set<U>> domains = new HashMap<>();
    variables.forEach(v -> domains.put(v, v.getInitialDomainValues()));
    return domains;
  }

  private V selectUnassigned(Map<V, U> assignment, Map<V, Set<U>> domains) {
    return variables.stream()
        .filter(v -> !assignment.containsKey(v))
        .min(Comparator.comparingInt(o -> domains.get(o).size()))
        .orElseThrow();
  }

  protected abstract boolean isConsistent(V variable, U value, Map<V, U> assignment);

}
