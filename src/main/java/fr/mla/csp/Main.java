package fr.mla.csp;

import java.util.Comparator;
import java.util.Map;

import fr.mla.NoSolutionException;
import fr.mla.csp.problem.NQueen;
import fr.mla.csp.problem.NQueenValue;
import fr.mla.csp.problem.NQueenVariable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args) {
    NQueen nQueen = new NQueen();
    try {
      Map<NQueenVariable, NQueenValue> assignment = nQueen.backtrackingSearch();
      for (NQueenVariable v : assignment.keySet().stream().sorted(Comparator.comparing(Variable::get)).toList()) {
        log.info("{} : {}", v.toString(), assignment.get(v));
      }
    } catch (NoSolutionException e) {
      log.error("No Solution Found");
    }
  }

}
