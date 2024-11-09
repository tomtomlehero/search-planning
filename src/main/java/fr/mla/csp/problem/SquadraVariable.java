package fr.mla.csp.problem;

import fr.mla.csp.Variable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static fr.mla.csp.problem.Squadra.PILOT_NUMBER;

public class SquadraVariable extends Variable<SquadraVariable.Pair, SquadraValue> {

  private static final List<SquadraValue> pilots = new ArrayList<>();
  static {
    IntStream.range(0, PILOT_NUMBER).forEach(i -> pilots.add(new SquadraValue("Pilot #" + (i + 1))));
  }

  public SquadraVariable(Leg leg, Role role) {
    this.set(new Pair(leg, role));
  }

  @Override
  public List<SquadraValue> getOrderDomainValues() {
    return pilots;
  }


  public enum Role {
    PILOT, COPILOT
  }

  @Getter
  @RequiredArgsConstructor
  public static class Leg implements Comparable<Leg> {

    private final int day;
    private final int rank;

    private final int length;
    private final double rating;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Leg leg = (Leg) o;
      return day == leg.day && rank == leg.rank;
    }

    @Override
    public int hashCode() {
      return Objects.hash(day, rank);
    }

    @Override
    public String toString() {
      return "Leg[" +
              "day=" + day + ", " +
              "rank=" + rank + ", " +
              "length=" + length + ", " +
              "rating=" + rating + ']';
    }

    @Override
    public int compareTo(@NonNull Leg other) {
      if (this.equals(other)) {
        return 0;
      }
      return Comparator.comparing(Leg::getDay).thenComparing(Leg::getRank).compare(this, other);
    }
  }

  @Getter
  @RequiredArgsConstructor
  static class Pair implements Comparable<Pair> {

    private final Leg leg;
    private final Role role;

    @Override
    public int compareTo(Pair other) {
      if (other.leg.equals(leg)) {
        return role.compareTo(other.role);
      }
      return leg.compareTo(other.leg);
    }

    @Override
    public String toString() {
      return String.format("%s : %s", leg, role);
    }

  }

}
