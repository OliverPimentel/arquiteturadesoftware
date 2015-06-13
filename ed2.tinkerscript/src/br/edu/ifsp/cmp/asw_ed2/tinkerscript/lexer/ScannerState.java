package br.edu.ifsp.cmp.asw_ed2.tinkerscript.lexer;

public enum ScannerState {
	BEGIN_PROGRAM,
	
	DECL_BEGIN,
	DECL_LEFT,
	DECL_RIGHT,
	DECL_END,
	
	CMD_BEGIN,
	CMD_POS_X,
	CMD_POS_Y,
	CMD_RULE_START,
	CMD_EXPRESSION,
	CMD_RULE_STOP
}
