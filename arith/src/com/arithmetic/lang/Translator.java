package com.arithmetic.lang;

import com.arithmetic.lang.AST.StmtNode;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Translator {

    public static void translate(String path) {
        String code = "";
        try {
            code = Files.readString(FileSystems.getDefault().getPath(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Lexer l = new Lexer(code);
        List<Token> tokens = l.lex();
        
        Parser p = new Parser(tokens);
        ArrayList<StmtNode> stmtNodes = (ArrayList<StmtNode>) p.parseProgram();
        
        Executor.executeAProgram(stmtNodes);
    }
}
