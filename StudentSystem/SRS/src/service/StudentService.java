package service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.SectionDao;
import dao.StudentDao;
import dao.dataAccess;
import model.Section;
import model.Student;

public class StudentService {
	public String getEnrolledSections(Student student) {
		JSONArray ja = new JSONArray();

		StudentDao sd = dataAccess.createStudentDao();
		List<Section> sections = sd.getEnrolledSections(student);

		for (Section s : sections) {
			JSONObject jo = new JSONObject();
			jo.put("sectionNo", s.getSectionNo());
			jo.put("day", s.getDayOfWeek());
			jo.put("time", s.getTimeOfDay());
			jo.put("room", s.getRoom());
			jo.put("sCapacity", s.getSeatingCapacity());
			jo.put("courseName", s.getRepresentedCourse().getCourseName());
			jo.put("professor", s.getInstructor().getName());
			ja.put(jo);
		}
		return ja.toString();
	}
	
	public void dropEnrolledSection(Student student, int sectionNo){
		StudentDao sd=dataAccess.createStudentDao();
		sd.dropEnrolledSection(student, sectionNo);
		
		SectionDao scd=dataAccess.createSectionDao();
		Section section=scd.getSection(sectionNo);
		
		student.dropSection(section);
	}
}
