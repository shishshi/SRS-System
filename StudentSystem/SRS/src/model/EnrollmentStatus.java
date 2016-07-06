package model;

public enum EnrollmentStatus {
	success("选课成功！"), secFull("选课失败：该课程已满员！"), prereq("选课失败：请先学习该课程的先修课程"), prevEnroll("选课失败：您已经修过该课程！");

	private final String value;

	EnrollmentStatus(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
