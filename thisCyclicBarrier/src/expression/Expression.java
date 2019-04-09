package expression;

import datatypes.AbstractDictionary;

public abstract class Expression {
    public abstract int eval(AbstractDictionary<String, Integer> symbolTable,AbstractDictionary<Integer,Integer> heap);
}
