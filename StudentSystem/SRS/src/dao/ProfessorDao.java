package dao;

import java.util.List;

import model.Professor;
import model.Section;

public interface ProfessorDao {
	String getPassword(String Sssn);
	List<Professor> getAllProfessors();
	Professor getProfessor(String Pssn);
	List<Section> getSectionTeached(Professor professor);
	void addProfessor(Professor professor);
	void deleteProfessor(String Pssn);
	void updateProfessor(Professor professor);
	void teachSection(Professor professor, Section section);
	String searchPSsn(String name);
	Professor searchProfessor(String Pssn);
}
