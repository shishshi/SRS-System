package model;

import java.util.HashMap;

public class Faculty {
	private HashMap<String, Professor> professors;

	public Faculty() {
		professors = new HashMap<String, Professor>();
	}

	public void addProfessor(Professor p) {
		professors.put(p.getSsn(), p);
	}

	public Professor findProfessor(String ssn) {
		return (Professor) professors.get(ssn);
	}

	public boolean isEmpty() {
		if (professors.size() == 0)
			return true;
		else
			return false;
	}
}
