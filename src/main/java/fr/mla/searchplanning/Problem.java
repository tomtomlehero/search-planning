package fr.mla.searchplanning;

public interface Problem {

  State getInitialState();

  boolean isTerminal(State state);

}
