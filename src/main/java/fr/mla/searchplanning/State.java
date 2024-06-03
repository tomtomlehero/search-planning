package fr.mla.searchplanning;

import java.util.List;

public interface State {

  List<State> getSuccessors();

}
