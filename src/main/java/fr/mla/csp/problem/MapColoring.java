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
    return true;
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
