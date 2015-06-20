package br.edu.ifsp.cmp.asw_ed2.tinkerscript.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RulesStateMachine {
	private String current;
	
	private Map<String,Set<String>> states;
	
	public RulesStateMachine(String initial) {
		states = new HashMap<>();
		current = initial;
		
		addState(initial);
	}
	
	public void addState(String state) {
		states.putIfAbsent(state, new HashSet<String>());
	}
	
	public void addTransition(String fromState, String toState) {
		addState(fromState);
		
		states.get(fromState).add(toState);
	}
	
	public boolean change(String state) {
		if (states.get(current).contains(state)) {
			current = state;
			return true;
		} else return false;
	}
	
	public String state() {
		return current;
	}
}
