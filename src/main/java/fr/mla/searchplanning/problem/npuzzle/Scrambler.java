package fr.mla.searchplanning.problem.npuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.IntStream;

import com.google.common.math.Stats;

import lombok.extern.slf4j.Slf4j;
import static fr.mla.searchplanning.problem.Constant.NPuzzleProblem.N;


@Slf4j
public class Scrambler {

  private static final int NUMBER_OF_MOVES = 1000000;

  private static final Random rand = new Random();

  public static void main(String[] args) {

//    Map<Integer, StatisticsOnListOfLongs> stat = stat();
//    log.info(stat.toString());

//    NPuzzleState initial = scramble(NUMBER_OF_MOVES);

    for (int h = 0; h <= 200; h++) {
      NPuzzleState initial = scrambleToTargetHeuristic(h);
      log.info("{}", initial);
      log.info("{}", initial.getTiles());
      log.info("Heuristic: {}", initial.getHeuristic());
    }
  }


  private static NPuzzleState scramble(int moves) {

    NPuzzleState result = new NPuzzleState(IntStream.range(0, N * N).boxed().toList());
    for (int i = 0; i < moves; i++) {
      result = moveRandom(result);
    }
    return result;
  }

  private static NPuzzleState scrambleToTargetHeuristic(long h) {

    NPuzzleState result = new NPuzzleState(IntStream.range(0, N * N).boxed().toList());
    while (result.getHeuristic() != h) {
      result = moveRandom(result);
    }
    return result;
  }


  private static NPuzzleState moveRandom(NPuzzleState state) {
    NPuzzleState result = null;

    while (result == null) {
      int d = rand.nextInt(4);
      result = switch (d) {
        case 0 -> state.moveNorth();
        case 1 -> state.moveEast();
        case 2 -> state.moveSouth();
        case 3 -> state.moveWest();
        default -> throw new IllegalStateException("Unexpected value: " + d);
      };
    }

    return result;
  }

  private static Map<Integer, StatisticsOnListOfLongs> stat() {

    Map<Integer, StatisticsOnListOfLongs> statMap = new TreeMap<>();

    IntStream.of(1, 2, 3, 4, 5, 10, 50, 100, 500, 1000, 10000).forEach(moves -> {
      log.info("Computing: {} moves", moves);
      List<Long> list = new ArrayList<>();
      for (int i = 0; i < 10000; i++) {
        NPuzzleState state = scramble(moves);
        list.add(state.getHeuristic());
      }

      StatisticsOnListOfLongs stats = new StatisticsOnListOfLongs(list);
      statMap.put(moves, stats);
    });

    return statMap;
  }


  static class StatisticsOnListOfLongs {

    private final List<Long> values;

    private final long count;
    private final double mean;
    private final double sumOfSquaresOfDeltas;
    private final double min;
    private final double max;


    public StatisticsOnListOfLongs(List<Long> values) {
      this.values = values;
      Stats stats = Stats.of(values);
      this.count = stats.count();
      this.min = stats.min();
      this.max = stats.max();
      this.mean = stats.mean();
      this.sumOfSquaresOfDeltas = stats.sampleStandardDeviation();
    }

    @Override
    public String toString() {
      return "{values=" + values.stream().limit(10).toList() + "...\n" +
          ", count=" + count + "\n" +
          ", mean=" + mean + "\n" +
          ", sumOfSquaresOfDeltas=" + sumOfSquaresOfDeltas + "\n" +
          ", min=" + min + "\n" +
          ", max=" + max + "\n" +
          '}';
    }
  }

}
