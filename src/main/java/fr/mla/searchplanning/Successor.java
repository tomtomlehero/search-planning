package fr.mla.searchplanning;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Successor {

  private State state;
  private long cost;

}
