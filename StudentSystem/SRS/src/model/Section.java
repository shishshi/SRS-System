package model;

import java.util.HashMap;
import java.util.List;

public class Section {
	private int sectionNo;
	private String dayOfWeek;
	private String timeOfDay;
	private String room;
	private int seatingCapacity;
	private Course representedCourse;
	private ScheduleOfClasses offeredIn;
	private Professor instructor;

	private HashMap<String, Student> enrolledStudents;
	private HashMap<Student, TranscriptEntry> assignedGrades;

	public Section(int sNo, String day, String time, Course course, String room, int seatingCapacity) {
		setSectionNo(sNo);
		setDayOfWeek(day);
		setTimeOfDay(time);
		setRepresentedCourse(course);
		setRoom(room);
		setSeatingCapacity(seatingCapacity);

		setInstructor(null);

		enrolledStudents = new HashMap<String, Student>();
		assignedGrades = new HashMap<Student, TranscriptEntry>();
	}

	public Section() {
	}

	public void setSectionNo(int no) {
		sectionNo = no;
	}

	public int getSectionNo() {
		return sectionNo;
	}

	public void setDayOfWeek(String day) {
		dayOfWeek = day;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setTimeOfDay(String time) {
		timeOfDay = time;
	}

	public String getTimeOfDay() {
		return timeOfDay;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setInstructor(Professor prof) {
		instructor = prof;
	}

	public Professor getInstructor() {
		return instructor;
	}

	public void setRepresentedCourse(Course c) {
		representedCourse = c;
	}

	public Course getRepresentedCourse() {
		return representedCourse;
	}

	public void setOfferedIn(ScheduleOfClasses soc) {
		offeredIn = soc;
	}

	public ScheduleOfClasses getOfferedIn() {
		return offeredIn;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public void setEnrolledStudents(List<Student> students) {
		for (Student s : students) {
			enrolledStudents.put(s.getSsn(), s);
		}
	}

	public String getFullSectionNo() {
		return getRepresentedCourse().getCourseNo() + " - " + getSectionNo();
	}

	public EnrollmentStatus enroll(Student s) {

		Transcript transcript = s.getTranscript();
		if (s.isCurrentlyEnrolledInSimilar(this) || transcript.verifyCompletion(this.getRepresentedCourse())) {
			return EnrollmentStatus.prevEnroll;
		}

		Course c = this.getRepresentedCourse();
		if (c.hasPrerequisites()) {
			for (Course pre : c.getPrerequisites()) {

				if (!transcript.verifyCompletion(pre)) {
					return EnrollmentStatus.prereq;
				}
			}
		}

		if (!this.confirmSeatAvailability()) {
			return EnrollmentStatus.secFull;
		}

		enrolledStudents.put(s.getSsn(), s);
		s.addSection(this);

		return EnrollmentStatus.success;
	}

	private boolean confirmSeatAvailability() {
		if (enrolledStudents.size() < this.getSeatingCapacity()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean drop(Student s) {

		if (!s.isEnrolledIn(this))
			return false;
		else {

			enrolledStudents.remove(s.getSsn());

			s.dropSection(this);
			return true;
		}
	}

	public int getTotalEnrollment() {
		return enrolledStudents.size();
	}

	public String getGrade(Student s) {
		String grade = null;

		TranscriptEntry te = assignedGrades.get(s);
		if (te != null) {
			grade = te.getGrade();
		}

		return grade;
	}

	public boolean postGrade(Student s, String grade) {

		if (!TranscriptEntry.validateGrade(grade))
			return false;

		if (assignedGrades.get(s) != null)
			return false;

		TranscriptEntry te = new TranscriptEntry();
		te.setSection(this);
		te.setGrade(grade);
		te.setStudent(s);

		assignedGrades.put(s, te);
		return true;
	}

	public boolean isSectionOf(Course c) {
		if (c.getCourseNo().equals(representedCourse.getCourseNo()))
			return true;
		else
			return false;
	}
}
