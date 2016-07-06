package dao;

import model.Student;
import model.Transcript;

public interface TranscriptDao {
	Transcript getTranscriptByStudent(Student student);
}
