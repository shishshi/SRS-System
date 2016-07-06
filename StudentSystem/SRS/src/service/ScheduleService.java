package service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.ScheduleOfClassesDao;
import dao.dataAccess;
import model.ScheduleOfClasses;
import model.Section;

public class ScheduleService {
	public String getAllSchedules(String semester){
		JSONArray ja = new JSONArray();
		
		ScheduleOfClassesDao scd=dataAccess.createScheduleOfClassesDao();
		ScheduleOfClasses sc=scd.getScheduleOfClassess(semester);
		List<Section> sections=scd.getAllSectionsOffered(sc);
		
		for(Section s:sections){
			JSONObject jo=new JSONObject();
			jo.put("sectionNo", s.getSectionNo());
			jo.put("day", s.getDayOfWeek());
			jo.put("time",s.getTimeOfDay());
			jo.put("room", s.getRoom());
			jo.put("sCapacity", s.getSeatingCapacity());
			jo.put("courseName", s.getRepresentedCourse().getCourseName());
			jo.put("professor", s.getInstructor().getName());
			ja.put(jo);
		}
		return ja.toString();
	}
}
