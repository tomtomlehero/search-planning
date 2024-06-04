package fr.mla.searchplanning.problem.npuzzle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import fr.mla.searchplanning.Problem;
import fr.mla.searchplanning.State;
import lombok.Getter;
import static fr.mla.searchplanning.problem.Constant.NPuzzleProblem.N;

public class NPuzzleProblem implements Problem {

  @Getter
  private final State initialState;

  private final State finalState = new NPuzzleState(IntStream.range(0, N * N).boxed().toList());

  public NPuzzleProblem(Integer... tiles) {
    List<Integer> tileList = Arrays.asList(tiles);
    boolean valid = (tileList.size() == (N * N)) && IntStream.range(0, N).allMatch(tileList::contains);
    if (!valid) {
      throw new IllegalArgumentException("Invalid State " + tileList);
    }

    initialState = new NPuzzleState(tileList);
  }

  @Override
  public boolean isTerminal(State state) {
    return finalState.equals(state);
  }

}
