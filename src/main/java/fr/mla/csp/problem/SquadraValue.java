package fr.mla.csp.problem;

import fr.mla.csp.Value;

import java.util.Objects;

public class SquadraValue extends Value<SquadraValue.Pilot> {

  public SquadraValue(int id, String name) {
    this.set(new Pilot(id, name));
  }

  @Override
  public String toString() {
    return v.name();
  }

  record Pilot(int id, String name) {

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Pilot pilot = (Pilot) o;
      return id == pilot.id;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(id);
    }
  }

}
