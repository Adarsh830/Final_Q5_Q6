package com.arithmetic.lang.AST;

public class DoWhileNode extends StmtNode {

	public final StmtNode statement;
	public final ExprNode condition;

	public DoWhileNode(StmtNode statement, ExprNode condition) {
		this.statement = statement;
		this.condition = condition;
	}

	@Override
	public String toString() { return "do " + statement.toString() + "\n while(" + statement.toString() + ")"; }
}
