package fr.mla.csp.problem;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import fr.mla.NoSolutionException;
import fr.mla.csp.CSP;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapColoring extends CSP<MapColoringVariable, MapColoringValue> {

  public MapColoring() {
    this.variables = Arrays.stream(MapColoringVariable.State.values()).map(MapColoringVariable::new).collect(Collectors.toSet());
  }

  @Override
  protected boolean isConsistent(MapColoringVariable variable, MapColoringValue value,
      Map<MapColoringVariable, MapColoringValue> assignment) {

    switch (variable.get()) {
      case WA -> {
        return checkAdjacent(MapColoringVariable.State.NT, value, assignment)
            && checkAdjacent(MapColoringVariable.State.SA, value, assignment);
      }
      case NT -> {
        return checkAdjacent(MapColoringVariable.State.WA, value, assignment)
            && checkAdjacent(MapColoringVariable.State.SA, value, assignment)
            && checkAdjacent(MapColoringVariable.State.Q, value, assignment);
      }
      case Q -> {
        return checkAdjacent(MapColoringVariable.State.NT, value, assignment)
            && checkAdjacent(MapColoringVariable.State.SA, value, assignment)
            && checkAdjacent(MapColoringVariable.State.NSW, value, assignment);
      }
      case NSW -> {
        return checkAdjacent(MapColoringVariable.State.Q, value, assignment)
            && checkAdjacent(MapColoringVariable.State.SA, value, assignment)
            && checkAdjacent(MapColoringVariable.State.V, value, assignment);
      }
      case V -> {
        return checkAdjacent(MapColoringVariable.State.NSW, value, assignment)
            && checkAdjacent(MapColoringVariable.State.SA, value, assignment);
      }
      case SA -> {
        return checkAdjacent(MapColoringVariable.State.WA, value, assignment)
            && checkAdjacent(MapColoringVariable.State.NT, value, assignment)
            && checkAdjacent(MapColoringVariable.State.Q, value, assignment)
            && checkAdjacent(MapColoringVariable.State.NSW, value, assignment)
            && checkAdjacent(MapColoringVariable.State.V, value, assignment);
      }
      case T -> {
        return true;
      }
    }
    throw new IllegalStateException("Unexpected value: " + variable.get());
  }

  private boolean checkAdjacent(MapColoringVariable.State state, MapColoringValue value,
      Map<MapColoringVariable, MapColoringValue> assignment) {
    return assignment.entrySet().stream()
        .noneMatch(e -> e.getKey().get() == state && e.getValue().get() == value.get());
  }


  public static void main(String[] args) {
    MapColoring mapColoring = new MapColoring();
    try {
      Map<MapColoringVariable, MapColoringValue> assignment = mapColoring.backtrackingSearch();
      for (Map.Entry<MapColoringVariable, MapColoringValue> entry : assignment.entrySet()) {
        log.info("{} : {}", entry.getKey(), entry.getValue());
      }
    } catch (NoSolutionException e) {
      log.error("No Solution Found");
    }
  }

}
