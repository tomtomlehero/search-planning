package fr.mla.csp.problem;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import fr.mla.csp.Variable;

public class MapColoringVariable extends Variable<MapColoringVariable.State, MapColoringValue> {

  public enum State {
    WA, NT, Q, NSW, V, SA, T
  }

  public MapColoringVariable(State value) {
    this.set(value);
  }

  @Override
  public Set<MapColoringValue> getInitialDomainValues() {
    return Arrays.stream(MapColoringValue.Color.values()).map(MapColoringValue::new).collect(Collectors.toSet());
  }

}
