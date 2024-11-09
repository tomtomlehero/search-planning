package fr.mla.csp.problem;

import fr.mla.csp.Value;

import static fr.mla.csp.problem.NQueen.N;

public class NQueenValue extends Value<Integer> {

  public NQueenValue(Integer value) {
    this.set(value);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < N; ++i) {
      result.append(i == v ? " X" : " .");
    }
    return result.toString();
  }

}
