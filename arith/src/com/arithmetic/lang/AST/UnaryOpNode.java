package com.arithmetic.lang.AST;

import com.arithmetic.lang.Executor;
import com.arithmetic.lang.Token;

import java.util.ArrayDeque;
import java.util.Map;

public class UnaryOpNode extends ExprNode {

    public final Token operation;
    public final ExprNode operand;

    public UnaryOpNode(Token operation, ExprNode operand) {
        this.operation = operation;
        this.operand = operand;
    }

    @Override
    public int getValue(ArrayDeque<Map<String, Integer>> variables){
        return Executor.evalExpr(this, variables);
    }

    @Override
    public String toString() {
        return operation.text + " " + (operand == null ? "null" : operand.toString());
    }
}
