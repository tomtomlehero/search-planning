package fr.mla.searchplanning;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class EightPuzzleState implements State {

  private final List<Integer> positions;

  public EightPuzzleState(Integer... i) {

    positions = Arrays.asList(i);

    boolean valid = positions.size() == 9 && Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8).allMatch(positions::contains);
    if (!valid) {
      throw new IllegalArgumentException("Invalid State " + positions);
    }
  }

}
