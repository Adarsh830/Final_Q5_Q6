package com.arithmetic.lang;

import com.arithmetic.lang.AST.*;
import com.arithmetic.lang.Exception.SemanticsException;

import java.util.*;

import static com.arithmetic.lang.TokenType.ASSIGNMENT;

public class Executor {

	public static void executeAProgram(ArrayList<StmtNode> stmtNodes) {
		stmtNodes.removeIf(stmtNode -> stmtNode instanceof EmptyNode);
		ArrayDeque<Map<String, Integer>> symbolTable = new ArrayDeque<>();
		symbolTable.push(new HashMap<>());
		for (StmtNode stmtNode : stmtNodes)
			eval(stmtNode, symbolTable);
	}

	public static void eval(StmtNode node, ArrayDeque<Map<String, Integer>> symbolTable) {
		if (node instanceof EmptyNode) {
			return;
		} else if (node instanceof BlockStatement) {
			BlockStatement blockStatement = (BlockStatement) node;
			symbolTable.push(new HashMap<>());
			for (StmtNode stmtNode : blockStatement.statements)
				eval(stmtNode, symbolTable);
			symbolTable.pop();
			return;
		} else if (node instanceof IfNode) {
			IfNode ifNode = (IfNode) node;
			symbolTable.push(new HashMap<>());
			if (valueToBoolean(ifNode.condition.getValue(symbolTable)))
				eval(ifNode.ifStatement, symbolTable);
			else if (node instanceof IfElseNode) {
				IfElseNode ifElseNode = (IfElseNode) node;
				eval(ifElseNode.elseStatement, symbolTable);
			}
			symbolTable.pop();
			return;
		} else if (node instanceof WhileNode) {
			WhileNode whileNode = (WhileNode) node;
			symbolTable.push(new HashMap<>());
			while (valueToBoolean(whileNode.condition.getValue(symbolTable))) {
				eval(whileNode.statement, symbolTable);
			}
			symbolTable.pop();
			return;

		} else if (node instanceof DoWhileNode) {
			DoWhileNode doWhileNode = (DoWhileNode) node;
			symbolTable.push(new HashMap<>());
			do {
				eval(doWhileNode.statement, symbolTable);
			} while (valueToBoolean(doWhileNode.condition.getValue(symbolTable)));
			return;
		} else if (node instanceof ForNode) {
			ForNode forNode = (ForNode) node;
			symbolTable.push(new HashMap<>());
			for (eval(forNode.stmtNodeLeft, symbolTable); valueToBoolean(
					forNode.conditionMiddle.getValue(symbolTable)); eval(forNode.operationRight, symbolTable))
				eval(forNode.statement, symbolTable);
			symbolTable.pop();
			return;
		} else if (node instanceof ExprStmtNode) {
			ExprStmtNode exprStmtNode = (ExprStmtNode) node;
			for (ExprNode exprNode : exprStmtNode.expr)
				evalExpr(exprNode, symbolTable);
			return;
		} else if (node instanceof PrintNode) {
			PrintNode printNode = (PrintNode) node;
			System.out.println(printNode.var.getValue(symbolTable));
			return;
		}
		throw new IllegalStateException(node.toString());
	}

	public static int evalExpr(ExprNode node, ArrayDeque<Map<String, Integer>> symbolTable) {
		if (node instanceof NumberNode || node instanceof VarNode) {
			return node.getValue(symbolTable);
		} else if (node instanceof UnaryOpNode) {
			UnaryOpNode unaryOpNode = (UnaryOpNode) node;
			switch (unaryOpNode.operation.type) {
				case NOT:
					return binaryUnsignedNOT(unaryOpNode.operand.getValue(symbolTable));
				case SUB:
					return -unaryOpNode.operand.getValue(symbolTable);
				case DEC:
					if (unaryOpNode.operand instanceof VarNode) {
						VarNode varNode = (VarNode) unaryOpNode.operand;
						varNode.setValue(varNode.getValue(symbolTable) - 1, symbolTable);
						return varNode.getValue(symbolTable);
					} else
						throw new SemanticsException("Expected a variable", unaryOpNode.operation);
				case INC:
					if (unaryOpNode.operand instanceof VarNode) {
						VarNode varNode = (VarNode) unaryOpNode.operand;
						varNode.setValue(varNode.getValue(symbolTable) + 1, symbolTable);
						return varNode.getValue(symbolTable);
					} else
						throw new SemanticsException("Expected a variable", unaryOpNode.operation);
			}
		} else if (node instanceof BinOpNode) {
			BinOpNode binOp = (BinOpNode) node;
			Integer right = binOp.right.getValue(symbolTable);

			if (binOp.op.type == ASSIGNMENT) {
				if (binOp.left instanceof VarNode) {
					VarNode var = (VarNode) binOp.left;
					var.setValue(right, symbolTable);
					return right;
				} else
					throw new SemanticsException("Expected a variable", binOp.op);
			}

			Integer left = binOp.left.getValue(symbolTable);
			switch (binOp.op.type) {
				case ADD:
					return left + right;
				case SUB:
					return left - right;
				case MUL:
					return left * right;
				case DIV:
					return left / right;
				case XOR:
					return left ^ right;
				case AND:
					return left & right;
				case OR:
					return left | right;
				case EQUAL:
					if (left.equals(right))
						return 1;
					else
						return 0;
				case NEQUAL:
					if (!left.equals(right))
						return 1;
					else
						return 0;
				case LESS:
					if (left < right)
						return 1;
					else
						return 0;
				case LESS_EQUAL:
					if (left <= right)
						return 1;
					else
						return 0;
				case GREATER:
					if (left > right)
						return 1;
					else
						return 0;
				case GREATER_EQUAL:
					if (left >= right)
						return 1;
					else
						return 0;
				case LAND:
					if (valueToBoolean(left) && valueToBoolean(right))
						return 1;
					else
						return 0;
				case LOR:
					if (valueToBoolean(left) || valueToBoolean(right))
						return 1;
					else
						return 0;
				default:
					Integer result = executeSpecialOperation(left, right, binOp.op);
					if (result != null) {
						if (binOp.left instanceof VarNode) {
							VarNode var = (VarNode) binOp.left;
							var.setValue(result, symbolTable);
						} else
							throw new SemanticsException("Expected a variable", binOp.op);
						return result;
					}

			}
		}
		throw new IllegalStateException(node.toString());
	}

	private static Integer executeSpecialOperation(Integer left, Integer right, Token operation) {
		switch (operation.type) {
			case ASSIGNMENT_ADD:
				return left + right;
			case ASSIGNMENT_SUB:
				return left - right;
			case ASSIGNMENT_DIV:
				return left / right;
			case ASSIGNMENT_MUL:
				return left * right;
			case ASSIGNMENT_AND:
				return left & right;
			case ASSIGNMENT_XOR:
				return left ^ right;
			case ASSIGNMENT_OR:
				return left | right;
			default:
				return null;
		}
	}

	private static int binaryUnsignedNOT(int number) {
		String binary = Integer.toBinaryString(number);
		StringBuilder sum = new StringBuilder();
		for (char ch : binary.toCharArray()) {
			if (ch == '0')
				sum.append("1");
			else
				sum.append("0");
		}
		return Integer.parseInt(sum.toString(), 2);
	}

	private static boolean valueToBoolean(Integer integer) { return integer != null && integer != 0; }

}
