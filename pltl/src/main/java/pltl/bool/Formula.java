package pltl.bool;

public interface Formula {

    @Override
    public String toString();
    public Formula get(int offset);
    public boolean equals(Object o);
}
