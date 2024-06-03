package fr.mla.searchplanning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EightPuzzleState implements State {

  private static final List<Integer> NORTH_TILES_INDEXES = List.of(0, 1, 2);
  private static final List<Integer> EAST_TILES_INDEXES = List.of(2, 5, 8);
  private static final List<Integer> SOUTH_TILES_INDEXES = List.of(6, 7, 8);
  private static final List<Integer> WEST_TILES_INDEXES = List.of(0, 3, 6);


  @EqualsAndHashCode.Include
  private final List<Integer> tiles;

  public EightPuzzleState(Integer... tiles) {
    this(Arrays.asList(tiles));
  }

  public EightPuzzleState(List<Integer> tiles) {

    boolean valid = tiles.size() == 9 && Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8).allMatch(tiles::contains);
    if (!valid) {
      throw new IllegalArgumentException("Invalid State " + tiles);
    }

    this.tiles = tiles;
  }

  @Override
  public List<State> getSuccessors() {
    List<State> successors = new ArrayList<>();
    EightPuzzleState n = moveNorth();
    if (n != null) {
      successors.add(n);
    }
    EightPuzzleState e = moveEast();
    if (e != null) {
      successors.add(e);
    }
    EightPuzzleState s = moveSouth();
    if (s != null) {
      successors.add(s);
    }
    EightPuzzleState w = moveWest();
    if (w != null) {
      successors.add(w);
    }

    return successors;
//    return Stream.of(moveNorth(), moveEast(), moveSouth(), moveWest()).filter(Objects::nonNull).toList();
  }

  private EightPuzzleState moveNorth() {
    int zIndex = tiles.indexOf(0);
    if (SOUTH_TILES_INDEXES.contains(zIndex)) {
      return null;
    }
    return swap(zIndex, zIndex + 3);
  }

  private EightPuzzleState moveEast() {
    int zIndex = tiles.indexOf(0);
    if (WEST_TILES_INDEXES.contains(zIndex)) {
      return null;
    }
    return swap(zIndex, zIndex - 1);
  }

  private EightPuzzleState moveSouth() {
    int zIndex = tiles.indexOf(0);
    if (NORTH_TILES_INDEXES.contains(zIndex)) {
      return null;
    }
    return swap(zIndex, zIndex - 3);
  }

  private EightPuzzleState moveWest() {
    int zIndex = tiles.indexOf(0);
    if (EAST_TILES_INDEXES.contains(zIndex)) {
      return null;
    }
    return swap(zIndex, zIndex + 1);
  }

  private EightPuzzleState swap(int i, int j) {
    EightPuzzleState result = new EightPuzzleState(new ArrayList<>(tiles));
    Collections.swap(result.tiles, i, j);
    return result;
  }

}
