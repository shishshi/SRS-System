package service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.TranscriptDao;
import dao.dataAccess;
import model.Student;
import model.Transcript;
import model.TranscriptEntry;

public class TranscriptService {
	public String getTranscriptJSON(Student student){
		JSONArray ja = new JSONArray();

		TranscriptDao td=dataAccess.createTranscriptDao();
		Transcript transcript=td.getTranscriptByStudent(student);
		List<TranscriptEntry> transEntry=transcript.getTranscriptEntries();
		
		for (TranscriptEntry te : transEntry) {
			JSONObject jo = new JSONObject();
			jo.put("sectionNo", te.getSection().getSectionNo());
			jo.put("courseNo", te.getSection().getRepresentedCourse().getCourseNo());
			jo.put("courseName", te.getSection().getRepresentedCourse().getCourseName());
			jo.put("professorName", te.getSection().getInstructor().getName());
			jo.put("credits", te.getSection().getRepresentedCourse().getCredits());
			jo.put("grade", te.getGrade());
			ja.put(jo);
		}
		return ja.toString();
	}
}
