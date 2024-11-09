package fr.mla.csp.problem;

import fr.mla.NoSolutionException;
import fr.mla.csp.CSP;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class MapColoring extends CSP<MapColoringVariable, MapColoringValue> {

  public MapColoring() {
    this.variables = Arrays.stream(MapColoringVariable.State.values()).map(MapColoringVariable::new).collect(Collectors.toSet());
  }

  @Override
  protected boolean isConsistent(MapColoringVariable variable, MapColoringValue value,
                                 Map<MapColoringVariable, MapColoringValue> assignment) {
    return true;
  }

  public static void main(String[] args) {
    MapColoring mapColoring = new MapColoring();
    try {
      Map<MapColoringVariable, MapColoringValue> assignment = mapColoring.backtrackingSearch();
      for (MapColoringVariable v : assignment.keySet()) {
        log.info("{} : {}", v, assignment.get(v));
      }
    } catch (NoSolutionException e) {
      log.error("No Solution Found");
    }
  }


}
