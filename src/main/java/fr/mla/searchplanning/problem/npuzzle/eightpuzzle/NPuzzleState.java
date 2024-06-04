package fr.mla.searchplanning.problem.npuzzle.eightpuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import fr.mla.searchplanning.State;
import fr.mla.searchplanning.Successor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import static fr.mla.searchplanning.problem.Constant.NPuzzleProblem.N;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NPuzzleState implements State {

  private static final List<Integer> NORTH_TILES_INDEXES = IntStream.range(0, N).boxed().toList();
  private static final List<Integer> EAST_TILES_INDEXES = IntStream.range(0, N).map(i -> (i + 1) * N - 1).boxed().toList();
  private static final List<Integer> SOUTH_TILES_INDEXES = IntStream.range(0, N).map(i -> N * (N - 1) + i).boxed().toList();
  private static final List<Integer> WEST_TILES_INDEXES = IntStream.range(0, N).map(i -> i * N).boxed().toList();

  private static final Map<Integer, Coord> FINAL_TILES_COORDS_MAP = getFinalTilesCoordsMap();


  @EqualsAndHashCode.Include
  private final List<Integer> tiles;

  public NPuzzleState(List<Integer> tiles) {
    this.tiles = tiles;
  }


  @Override
  public long getHeuristic() {
    return distanceToFinal(getTilesCoordMap());
  }

  private Map<Integer, Coord> getTilesCoordMap() {
    Map<Integer, Coord> tilesCoordMap = new HashMap<>();
    IntStream.rangeClosed(1, N * N).forEach(i -> {
      int index = tiles.indexOf(i);
      tilesCoordMap.put(i, new Coord(index % N, index / N));
    });
    return tilesCoordMap;
  }


  private static Map<Integer, Coord> getFinalTilesCoordsMap() {
    Map<Integer, Coord> tilesCoordMap = new HashMap<>();
    IntStream.range(1, N * N).forEach(i -> tilesCoordMap.put(i, new Coord(i % N, i / N)));
    return tilesCoordMap;
  }

  private long distanceToFinal(Map<Integer, Coord> map) {
    long result = 0;
    for (Map.Entry<Integer, Coord> entry : FINAL_TILES_COORDS_MAP.entrySet()) {
      Coord c1 = map.get(entry.getKey());
      Coord c2 = entry.getValue();
      result += Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y);
    }
    return result;
  }


  @Override
  public List<Successor> getSuccessors() {
    List<State> successors = new ArrayList<>();
    NPuzzleState n = moveNorth();
    if (n != null) {
      successors.add(n);
    }
    NPuzzleState e = moveEast();
    if (e != null) {
      successors.add(e);
    }
    NPuzzleState s = moveSouth();
    if (s != null) {
      successors.add(s);
    }
    NPuzzleState w = moveWest();
    if (w != null) {
      successors.add(w);
    }

    return successors.stream().map(state -> new Successor(state, 1)).toList();
  }

  private NPuzzleState moveNorth() {
    int zIndex = tiles.indexOf(0);
    if (SOUTH_TILES_INDEXES.contains(zIndex)) {
      return null;
    }
    return swap(zIndex, zIndex + N);
  }

  private NPuzzleState moveEast() {
    int zIndex = tiles.indexOf(0);
    if (WEST_TILES_INDEXES.contains(zIndex)) {
      return null;
    }
    return swap(zIndex, zIndex - 1);
  }

  private NPuzzleState moveSouth() {
    int zIndex = tiles.indexOf(0);
    if (NORTH_TILES_INDEXES.contains(zIndex)) {
      return null;
    }
    return swap(zIndex, zIndex - N);
  }

  private NPuzzleState moveWest() {
    int zIndex = tiles.indexOf(0);
    if (EAST_TILES_INDEXES.contains(zIndex)) {
      return null;
    }
    return swap(zIndex, zIndex + 1);
  }

  private NPuzzleState swap(int i, int j) {
    NPuzzleState result = new NPuzzleState(new ArrayList<>(tiles));
    Collections.swap(result.tiles, i, j);
    return result;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("\n");
    IntStream.range(0, N).forEach(l -> {
      IntStream.range(0, N).forEach(c -> {
        int t = tiles.get(N * l + c);
        if (t == 0) {
          s.append("    ");
        } else {
          s.append("   ").append(t);
        }
      });
      s.append("\n\n");
    });
    return s.toString();
  }

  @AllArgsConstructor
  static class Coord {

    private final int x;
    private final int y;
  }

}
