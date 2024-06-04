package fr.mla.searchplanning.problem.eightpuzzle;

import fr.mla.searchplanning.Problem;
import fr.mla.searchplanning.State;
import lombok.Getter;

public class EightPuzzleProblem implements Problem {

  @Getter
  private final State initialState;

  private final State finalState = new EightPuzzleState(0, 1, 2, 3, 4, 5, 6, 7, 8);

  public EightPuzzleProblem(Integer... i) {
    initialState = new EightPuzzleState(i);
  }

  @Override
  public boolean isTerminal(State state) {
    return finalState.equals(state);
  }

}
