package fr.mla.csp.problem;

import fr.mla.csp.Value;

public class SquadraValue extends Value<SquadraValue.Pilot> {

  public SquadraValue(String name) {
    this.set(new Pilot(name));
  }

  @Override
  public String toString() {
    return v.name();
  }

  record Pilot(String name) {
  }

}
