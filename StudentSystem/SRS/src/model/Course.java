package model;

import java.util.ArrayList;
import java.util.List;

public class Course {
	private String courseNo;
	private String courseName;
	private double credits;

	private ArrayList<Section> offeredAsSection;
	private ArrayList<Course> prerequisites;

	public Course(String cNo, String cName, double credits) {
		setCourseNo(cNo);
		setCourseName(cName);
		setCredits(credits);
		offeredAsSection = new ArrayList<Section>();
		prerequisites = new ArrayList<Course>();
	}

	public Course() {
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public double getCredits() {
		return credits;
	}

	public void setCredits(double credits) {
		this.credits = credits;
	}

	public void setPrerequisites(List<Course> preCourses) {
		for (Course c : preCourses) {
			prerequisites.add(c);
		}
	}

	public void addSection(Section s) {
		offeredAsSection.add(s);
	}

	public void addPrerequisite(Course c) {
		prerequisites.add(c);
	}

	public boolean hasPrerequisites() {
		if (prerequisites.size() > 0)
			return true;
		else
			return false;
	}

	public List<Course> getPrerequisites() {
		return prerequisites;
	}

	public Section scheduleSection(String day, String time, String room, int capacity) {
		Section s = new Section();
		s.setDayOfWeek(day);
		s.setTimeOfDay(time);
		s.setRepresentedCourse(this);
		s.setRoom(room);
		s.setSectionNo(offeredAsSection.size() + 1);
		addSection(s);
		return s;
	}
	
	public boolean isCourseInSimilar(List<Course> courses){
		boolean flag=false;
		for(Course c:courses){
			if(courseNo.equals(c.getCourseNo())||courseName.equals(c.getCourseName())){
				flag=true;
				break;
			}
		}
		return flag;
	}
}
