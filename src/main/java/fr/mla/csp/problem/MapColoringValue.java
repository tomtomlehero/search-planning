package fr.mla.csp.problem;

import fr.mla.csp.Value;

public class MapColoringValue extends Value<MapColoringValue.Color> {

  public enum Color {
    RED, BLUE, GREEN
  }

  public MapColoringValue(Color value) {
    this.set(value);
  }


}
