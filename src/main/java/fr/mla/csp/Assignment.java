package fr.mla.csp;

import java.util.HashMap;

public abstract class Assignment extends HashMap<Variable<?, ?>, Value<?>> {

  protected abstract boolean isComplete();

}
