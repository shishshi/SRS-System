package dao;

import java.util.List;

import model.Section;
import model.Student;

public interface StudentDao {
	String getPassword(String SSsn);
	List<Student> getAllStudents();
	Student getStudent(String Sssn);
	List<Section> getEnrolledSections(Student student);
	void addStudent(Student student);
	void deleteStudent(Student student);
	void updateStudent(Student student);
	void addEnrolledSection(Student student, int sectionNo);
	void dropEnrolledSection(Student student, int sectionNo);
	Student searchStudent(String Sssn);
}
