package br.edu.ifsp.cmp.asw_ed2.tinkerscript;

import java.util.List;

import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer.Scanner;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer.ScannerException;
import br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer.Token;

public class Main {
	public static void main(String[] args) {
		try {
			List<Token> tokens = Scanner.read("exemplo.tinkerscript");
			for (Token t : tokens) System.out.println(t.getCategory().toString() + '\t' + t.getLexeme());
		} catch (ScannerException e) { e.printStackTrace();	}
	}
	
//	public static void main(String[] args) {
//		try (java.util.Scanner s = new java.util.Scanner(new File("exemplo.tinkerscript"))) {
//			s.useDelimiter("\\s|;");
//			while(s.hasNext(Pattern.compile(":"))) System.out.printf("'%s'\n", s.next(Pattern.compile(":")));
//		} catch(FileNotFoundException e) { System.out.println("File not found."); }
//	}
}
