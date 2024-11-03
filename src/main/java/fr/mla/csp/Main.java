package fr.mla.csp;

import fr.mla.NoSolutionException;
import fr.mla.csp.problem.NQueen;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args) {
    CSP nQueen = new NQueen();
    try {
      Assignment assignment = nQueen.backtrackingSearch();
    } catch (NoSolutionException e) {
      log.error("No Solution Found");
    }
  }

}
