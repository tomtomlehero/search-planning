package fr.mla.searchplanning;

import java.util.List;

public interface Problem {

    State getInitialState();

    List<State> getSuccessors(State state);

    boolean isTerminal(State state);

}
