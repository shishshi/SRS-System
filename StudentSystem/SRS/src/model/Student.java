package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Student extends Person {
	private String major;
	private String degree;
	private Transcript transcript;
	private ArrayList<Section> attends;

	public Student(String ssn, String name, String password, String major, String degree) {
		super(ssn, name, password);

		this.setMajor(major);
		this.setDegree(degree);

		this.setTranscript(new Transcript(this));

		attends = new ArrayList<Section>();
	}

	

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMajor() {
		return major;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDegree() {
		return degree;
	}

	public void setTranscript(Transcript t) {
		transcript = t;
	}

	public Transcript getTranscript() {
		return transcript;
	}

	public void setAttends(List<Section> enrolledSections) {
		for (Section s : enrolledSections) {
			attends.add(s);
		}
	}

	public void addSection(Section s) {
		attends.add(s);
	}

	public void dropSection(Section s) {
		attends.remove(s);
	}

	public boolean isEnrolledIn(Section s) {
		if (attends.contains(s))
			return true;
		else
			return false;
	}

	public boolean isCurrentlyEnrolledInSimilar(Section s1) {
		boolean foundMatch = false;

		Course c1 = s1.getRepresentedCourse();
		String cNo1 = c1.getCourseNo();
		for (Section s2 : attends) {
			Course c2 = s2.getRepresentedCourse();
			String cNo2 = c2.getCourseNo();
			if (cNo1.equals(cNo2)) {

				if (s2.getGrade(this) == null) {

					foundMatch = true;
					break;
				}
			}
		}

		return foundMatch;
	}

	public Collection<Section> getEnrolledSections() {
		return attends;
	}
}
