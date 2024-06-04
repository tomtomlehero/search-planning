package fr.mla.searchplanning.problem.npuzzle;

import java.util.Random;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import static fr.mla.searchplanning.problem.Constant.NPuzzleProblem.N;


@Slf4j
public class Scrambler {

  private static final int NUMBER_OF_MOVES = 10_000_000;

  public static void main(String[] args) {

    NPuzzleState initial = new NPuzzleState(IntStream.range(0, N * N).boxed().toList());
    Random rand = new Random();

    for (int i = 0; i < NUMBER_OF_MOVES; i++) {

      int d = rand.nextInt(4);
      NPuzzleState s = switch (d) {
        case 0 -> initial.moveNorth();
        case 1 -> initial.moveEast();
        case 2 -> initial.moveSouth();
        case 3 -> initial.moveWest();
        default -> throw new IllegalStateException("Unexpected value: " + d);
      };

      if (s != null) {
        initial = s;
      }
    }
    log.info("{}", initial);
    log.info("{}", initial.getTiles());

  }

}
