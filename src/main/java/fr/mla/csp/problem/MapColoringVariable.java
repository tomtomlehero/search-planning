package fr.mla.csp.problem;

import fr.mla.csp.Variable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapColoringVariable extends Variable<MapColoringVariable.State, MapColoringValue> {

  public enum State {
    WA, NT, Q, NSW, V, SA, T
  }

  public MapColoringVariable(State value) {
    this.set(value);
  }

  @Override
  public List<MapColoringValue> getOrderDomainValues() {
    return Arrays.stream(MapColoringValue.Color.values()).map(MapColoringValue::new).collect(Collectors.toList());
  }

}
