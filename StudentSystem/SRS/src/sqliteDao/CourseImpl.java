package sqliteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.CourseDao;
import model.Course;
import model.Professor;
import model.Section;
import util.DBUtil;

public class CourseImpl implements CourseDao {
	@Override
	public List<Course> getAllCourses() {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "select * from Course";
		List<Course> courseList = new ArrayList<Course>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String courseNo = rs.getString("courseNo");
				String courseName = rs.getString("courseName");
				double credits = rs.getDouble("credits");
				Course course = new Course(courseNo,courseName,credits);
				courseList.add(course);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseList;
	}

	@Override
	public Course getCourse(String courseNo) {
		String sql = "select * from Course where courseNo='" + courseNo + "'";
		Connection Conn = DBUtil.getSqliteConnection();
		Course course = new Course();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				course = new Course(courseNo,rs.getString("courseName"),rs.getDouble("credits"));
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return course;
	}

	@Override
	public List<Section> getAllOfferedAsSection(Course course) {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "select * from Section,Professor where Section.Pssn=Professor.Pssn and Section.courseNo='"
				+ course.getCourseNo() + "'";
		List<Section> offeredAsSection = new ArrayList<Section>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int sectionNo = rs.getInt("sectionNo");
				Section section = new Section(sectionNo, rs.getString("dayOfWeek"), rs.getString("timeOfDay"), course,
						rs.getString("room"), rs.getInt("seatingCapacity"));
				Professor professor = new Professor(rs.getString("professorName"), rs.getString("Pssn"),rs.getString("password"),
						rs.getString("title"), rs.getString("department"));
				section.setInstructor(professor);
				offeredAsSection.add(section);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offeredAsSection;
	}

	@Override
	public List<Course> getPrerequisites(Course course) {
		String courseNo = course.getCourseNo();
		List<Course> prerequisites = new ArrayList<Course>();
		String sql = "select * from PreRequisites,Course where PreRequisites.preCourseNo=Course.courseNo and PreRequisites.courseNo='"
				+ courseNo + "'";
		Connection Conn = DBUtil.getSqliteConnection();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String preCourseNo = rs.getString("preCourseNo");
				Course preCourse = new Course(preCourseNo, rs.getString("courseName"), rs.getDouble("credits"));
				prerequisites.add(preCourse);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return prerequisites;
	}

	@Override
	public void addCourse(Course course, String preCourseNo) {
		String courseNo = course.getCourseNo();
		String courseName = course.getCourseName();
		double credits = course.getCredits();

		String sql = "insert into Course values('" + courseNo + "','" + courseName + "','" + credits + "')";
		Connection conn = DBUtil.getSqliteConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("����γ��쳣��" + e.getMessage());
		}
		addPrerequisite(courseNo, preCourseNo);
	}

	@Override
	public void addPrerequisite(String courseNo, String preCourseNo) {
		String sql = "insert into PreRequisites values('" + courseNo + "','" + preCourseNo + "')";
		Connection conn = DBUtil.getSqliteConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("����γ��쳣��" + e.getMessage());
		}
	}

	@Override
	public void updatePrerequisite(Course course, String preCourseNo) {
		String sql = "update PreRequisites set preCourseNo='" + preCourseNo + "' where courseNo='"
				+ course.getCourseNo() + "' and preCourseNo='" + getPrerequisites(course).get(0).getCourseNo() + "'";
		Connection conn = DBUtil.getSqliteConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("����γ��쳣��" + e.getMessage());
		}
	}

	@Override
	public void deleteCourse(String courseNo) {
		Connection conn = DBUtil.getSqliteConnection();
		String sql = "delete from Course where courseNo='" + courseNo + "'";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ���γ��쳣��" + e.getMessage());
		}
	}

	@Override
	public void updateCourse(Course course) {
		Connection conn = DBUtil.getSqliteConnection();
		String sql = "update Course set courseName='" + course.getCourseName() + "',credits=" + course.getCredits()
				+ " where courseNo='" + course.getCourseNo() + "'";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ���γ��쳣��" + e.getMessage());
		}
	}
}
