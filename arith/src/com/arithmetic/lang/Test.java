package com.arithmetic.lang;

import java.util.Scanner;
import com.arithmetic.lang.Exception.*;

public class Test {
	public static void main(String[] args) {
		if (args.length == 1) {
			Translator.translate(args[0]);
		} else {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				try {
					System.out.println('\n');
					menu();
					int com = scanner.nextInt();
					switch (com) {
						case 1:
							System.err.println("Running : ./src/com/arithmetic/lang/resources/code.txt");
							Translator.translate("./src/com/arithmetic/lang/resources/code.txt");
							break;
						case 2:
							System.err.println("Running : ./src/com/arithmetic/lang/resources/factorial.txt");
							Translator.translate("./src/com/arithmetic/lang/resources/factorial.txt");
							break;
						case 3:
							System.err.println("Running : ./src/com/arithmetic/lang/resources/error.txt");
							Translator.translate("./src/com/arithmetic/lang/resources/error.txt");
							break;
						case 4:
							Translator.translate(scanner.next());
							break;
						case 5:
							scanner.close();
							return;
					}
				} catch (LexerException | ParserException | SemanticsException e) {
					System.err.println(
							e.getClass()
								.getSimpleName() + " *: " + e.getMessage());
				}
				System.out.println('\n');
			}
		}
	}

	public static void menu() {
		System.out.println("Select an option:");
		System.out.println("1. Run basic arithmetic operations.");
		System.out.println("2. Run factorial test code.");
		System.out.println("3. Run code with error.");
		System.out.println("4. Enter path to code.");
		System.out.println("5. Exit.");
		System.out.print("> ");
	}
}
