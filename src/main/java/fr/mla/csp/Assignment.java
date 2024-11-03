package fr.mla.csp;

public abstract class Assignment {

    protected abstract boolean isComplete();

    protected abstract void add(Variable variable, Value value);

    protected abstract void remove(Variable variable, Value value);

}
