package fr.mla.searchplanning.problem.eightpuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import fr.mla.searchplanning.State;
import fr.mla.searchplanning.Successor;
import lombok.AllArgsConstructor;
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

  private static final Map<Integer, Coord> FINAL_STATE_TILES_COORDS_MAP = Map.of(
      1, new Coord(1, 0),
      2, new Coord(2, 0),
      3, new Coord(0, 1),
      4, new Coord(1, 1),
      5, new Coord(2, 1),
      6, new Coord(0, 2),
      7, new Coord(1, 2),
      8, new Coord(2, 2));


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
  public long getHeuristic() {
    return distanceToFinal(getTilesCoordMap());
  }

  private Map<Integer, Coord> getTilesCoordMap() {

    Map<Integer, Coord> tilesCoordMap = new HashMap<>();
    Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).forEach(i -> {
      int index = tiles.indexOf(i);
      tilesCoordMap.put(i, new Coord(index % 3, index / 3));
    });
    return tilesCoordMap;
  }

  private long distanceToFinal(Map<Integer, Coord> map) {
    long result = 0;
    for (Map.Entry<Integer, Coord> entry : FINAL_STATE_TILES_COORDS_MAP.entrySet()) {
      Coord c1 = map.get(entry.getKey());
      Coord c2 = entry.getValue();
      result += Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y);
    }
    return result;
  }


  @Override
  public List<Successor> getSuccessors() {
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

    return successors.stream().map(state -> new Successor(state, 1)).toList();
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


  @AllArgsConstructor
  static class Coord {

    private final int x;
    private final int y;
  }

}
