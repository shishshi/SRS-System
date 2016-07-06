package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class CourseCatalog {

	private HashMap<String, Course> courses;
	
	public CourseCatalog() {
		courses = new HashMap<String, Course>();
	}
	
	public List<Course> getCourses(){
		List<Course> allCourses = new ArrayList<Course>();
		for(Entry<String, Course> entry : courses.entrySet()){
			allCourses.add(entry.getValue());
		}
		return allCourses;
	}
	
	public void addCourse(Course c) {
		String key = c.getCourseNo();
		courses.put(key, c);
	}
	
	public Course findCourse(String courseNo) {
		return courses.get(courseNo);
	}

	public boolean isEmpty() {
		if (courses.size() == 0)
			return true;
		else
			return false;
	}
}
