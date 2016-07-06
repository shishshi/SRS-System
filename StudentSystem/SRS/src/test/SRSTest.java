package test;

import java.util.List;

import dao.CourseDao;
import dao.SectionDao;
import dao.StudentDao;
import dao.TranscriptDao;
import dao.dataAccess;
import model.Course;
import model.EnrollmentStatus;
import model.ScheduleOfClasses;
import model.Section;
import model.Student;
import model.Transcript;

public class SRSTest {
	public static List<Course> courseCatalog;
	public static ScheduleOfClasses scheduleOfClasses = new ScheduleOfClasses("2016-2");

	public static void main(String[] args) {
		Student s1, s2, s3;
		Course c1, c2, c3, c4, c5;
		Section sec1, sec2, sec3, sec4, sec5, sec6, sec7;

		// 初始化 Students
		StudentDao sd = dataAccess.createStudentDao();
		List<Student> students = sd.getAllStudents();
		s1 = students.get(0);
		s2 = students.get(1);
		s3 = students.get(2);

		List<Section> enrolledSections = sd.getEnrolledSections(s1);
		s1.setAttends(enrolledSections);
		enrolledSections.clear();
		enrolledSections = sd.getEnrolledSections(s2);
		s2.setAttends(enrolledSections);
		enrolledSections.clear();
		enrolledSections = sd.getEnrolledSections(s3);
		s3.setAttends(enrolledSections);

		TranscriptDao td = dataAccess.createTranscriptDao();
		Transcript trans1 = td.getTranscriptByStudent(s1);
		s1.setTranscript(trans1);// s1已选sec2(c1)，并有sec6(CMP999)和sec7(ART101)的成绩
		Transcript trans2 = td.getTranscriptByStudent(s2);
		s2.setTranscript(trans2);
		Transcript trans3 = td.getTranscriptByStudent(s3);
		s3.setTranscript(trans3);// s2和s3为新生，无成绩单和选课数据

		// 初始化 Courses
		CourseDao cd = dataAccess.createCourseDao();
		courseCatalog = cd.getAllCourses();
		c1 = courseCatalog.get(0);// CMP101
		c2 = courseCatalog.get(1);// OBJ101
		c3 = courseCatalog.get(2);// CMP283
		c4 = courseCatalog.get(3);// CMP999
		c5 = courseCatalog.get(4);// ART101

		// 设置先修顺序
		setPrerequisites(cd, c1);// 无先修课程
		setPrerequisites(cd, c2);// 先修课程CMP999
		setPrerequisites(cd, c3);// 先修课程OBJ101
		setPrerequisites(cd, c4);// 先修课程CMP283
		setPrerequisites(cd, c5);// 无先修课程

		// 初始化 Sections(Professor对象已经封装到对应的Section中)
		SectionDao scd = dataAccess.createSectionDao();
		List<Section> sections = scd.getSectionsByCourse(c1.getCourseNo());// 数据库中已有两个数据
		sec1 = sections.get(0);
		sec2 = sections.get(1);

		sections.clear();
		sections = scd.getSectionsByCourse(c2.getCourseNo());// 数据库中已有两个数据
		sec3 = sections.get(0);
		sec4 = sections.get(1);

		sec5 = scd.getSectionsByCourse(c3.getCourseNo()).get(0);// 一个数据
		sec6 = scd.getSectionsByCourse(c4.getCourseNo()).get(0);
		sec7 = scd.getSectionsByCourse(c5.getCourseNo()).get(0);

		// 由于添加了先修顺序，重新为section对象设置representedCourse属性
		sec1.setRepresentedCourse(c1);
		sec2.setRepresentedCourse(c1);
		sec3.setRepresentedCourse(c2);
		sec4.setRepresentedCourse(c2);
		sec5.setRepresentedCourse(c3);
		sec6.setRepresentedCourse(c4);
		sec7.setRepresentedCourse(c5);

		// 添加可选班次
		scheduleOfClasses.addSection(sec1);
		scheduleOfClasses.addSection(sec2);
		scheduleOfClasses.addSection(sec3);
		scheduleOfClasses.addSection(sec4);
		scheduleOfClasses.addSection(sec5);
		scheduleOfClasses.addSection(sec6);
		scheduleOfClasses.addSection(sec7);

		System.out.println("===============================");
		System.out.println("学生选课开始：");
		System.out.println("===============================");
		System.out.println();

		System.out.println("学生 " + s1.getName() + " 尝试选择班次：" + sec1.getRepresentedCourse().getCourseNo() + "-"
				+ sec1.getSectionNo());

		// s1选择sec1(c1)，成功
		EnrollmentStatus status = sec1.enroll(s1);
		reportStatus(status);

		// s1选择sec2(c1)，失败（已选）
		attemptToEnroll(s1, sec2);

		// s2选择sec2(c1)，成功
		attemptToEnroll(s2, sec2);

		// s2选择sec3(c2)，失败，先修课程c4
		attemptToEnroll(s2, sec3);

		// s2选择sec7(c5)，成功
		attemptToEnroll(s2, sec7);
		// s3选择sec1(c1)，成功
		attemptToEnroll(s3, sec1);

		System.out.println("===============================");
		System.out.println("选课测试结束");

		sec1.postGrade(s1, "C+");
		sec1.postGrade(s3, "A");
		sec2.postGrade(s2, "B+");
		sec7.postGrade(s2, "A-");
	}

	// 返回选课结果
	private static void reportStatus(EnrollmentStatus s) {
		System.out.println("result:" + s.value());
		System.out.println();
	}

	// 选课
	private static void attemptToEnroll(Student s, Section sec) {
		System.out.println(
				"学生 " + s.getName() + " 尝试选择班次：" + sec.getRepresentedCourse().getCourseNo() + "-" + sec.getSectionNo());
		reportStatus(sec.enroll(s));
	}

	// 设置先修课程
	private static void setPrerequisites(CourseDao cd, Course c) {
		List<Course> prerequisites = cd.getPrerequisites(c);
		if (prerequisites.size() != 0) {
			c.setPrerequisites(prerequisites);
		}
		prerequisites.clear();
	}
}
