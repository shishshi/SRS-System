package sqliteDao;

import java.util.List;

import dao.FacultyDao;
import dao.ProfessorDao;
import dao.dataAccess;
import model.Faculty;
import model.Professor;

public class FacultyImpl implements FacultyDao {
	
	@Override
	public Faculty getFaculty() {
		Faculty faculty = new Faculty();
		ProfessorDao pd = dataAccess.createProfessorDao();
		List<Professor> professors = pd.getAllProfessors();
		for (Professor p : professors) {
			faculty.addProfessor(p);
		}
		return faculty;
	}
}
