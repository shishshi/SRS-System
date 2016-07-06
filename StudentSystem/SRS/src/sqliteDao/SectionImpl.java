package sqliteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dao.SectionDao;
import model.Course;
import model.Professor;
import model.Section;
import model.Student;
import model.TranscriptEntry;
import util.DBUtil;

public class SectionImpl implements SectionDao {
	@Override
	public List<Section> getAllSections() {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "select * from Section,Course,Professor where Section.Pssn=Professor.Pssn and Section.courseNo=Course.courseNo";
		List<Section> sectionList = new ArrayList<Section>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int sectionNo = rs.getInt("sectionNo");
				Course course = new Course(rs.getString("courseNo"), rs.getString("courseName"),
						rs.getDouble("credits"));
				Section section = new Section(sectionNo, rs.getString("dayOfWeek"), rs.getString("timeOfDay"), course,
						rs.getString("room"), rs.getInt("seatingCapacity"));
				Professor professor = new Professor(rs.getString("professorName"), rs.getString("Pssn"),rs.getString("password"),
						rs.getString("title"), rs.getString("department"));
				section.setInstructor(professor);
				sectionList.add(section);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sectionList;
	}

	@Override
	public Section getSection(int sectionNo) {
		Section section = new Section();
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "select * from Section,Course,Professor where Section.Pssn=Professor.Pssn and Section.courseNo=Course.courseNo and Section.sectionNo='"
				+ sectionNo + "'";
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Course course = new Course(rs.getString("courseNo"), rs.getString("courseName"),
						rs.getDouble("credits"));
				section = new Section(sectionNo, rs.getString("dayOfWeek"), rs.getString("timeOfDay"), course,
						rs.getString("room"), rs.getInt("seatingCapacity"));
				Professor professor = new Professor(rs.getString("professorName"), rs.getString("Pssn"),rs.getString("password"),
						rs.getString("title"), rs.getString("department"));
				section.setInstructor(professor);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return section;
	}

	@Override
	public List<Section> getSectionsByCourse(String courseNo) {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "select * from Section,Course,Professor where Section.Pssn=Professor.Pssn and Section.courseNo=Course.courseNo and Section.courseNo='"
				+ courseNo + "'";
		List<Section> sectionList = new ArrayList<Section>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int sectionNo = rs.getInt("sectionNo");
				Course course = new Course(rs.getString("courseNo"), rs.getString("courseName"),
						rs.getDouble("credits"));
				Section section = new Section(sectionNo, rs.getString("dayOfWeek"), rs.getString("timeOfDay"), course,
						rs.getString("room"), rs.getInt("seatingCapacity"));
				Professor professor = new Professor(rs.getString("professorName"), rs.getString("Pssn"),rs.getString("password"),
						rs.getString("title"), rs.getString("department"));
				section.setInstructor(professor);
				sectionList.add(section);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sectionList;
	}

	@Override
	public List<Student> getEnrolledStudents(int sectionNo) {
		List<Student> enrolledStudents = new ArrayList<Student>();
		String sql = "select * from Student_Section,Student where Student_Section.Sssn=Student.Sssn and Student_Section.sectionNo='"
				+ sectionNo + "'";
		Connection Conn = DBUtil.getSqliteConnection();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Student student = new Student(rs.getString("studentName"), rs.getString("Sssn"),rs.getString("password"), rs.getString("major"),
						rs.getString("degree"));
				enrolledStudents.add(student);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enrolledStudents;
	}

	@Override
	public HashMap<Student, TranscriptEntry> getAssignedGrades(Section section) {
		int sectionNo = section.getSectionNo();
		String sql = "select * from TranscriptEntry,Student where TranscriptEntry.Sssn=Student.Sssn and TranscriptEntry.sectionNo='"
				+ sectionNo + "'";
		HashMap<Student, TranscriptEntry> assignedGrades = new HashMap<Student, TranscriptEntry>();
		Connection Conn = DBUtil.getSqliteConnection();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Student student = new Student(rs.getString("studentName"), rs.getString("Sssn"), rs.getString("password"),rs.getString("major"),
						rs.getString("degree"));
				TranscriptEntry transEntry = new TranscriptEntry(rs.getInt("transEntryNo"), student,
						rs.getString("grade"), section);
				assignedGrades.put(student, transEntry);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assignedGrades;
	}

	@Override
	public void addSection(Section section) {
		int sectionNo = section.getSectionNo();
		String dayOfWeek = section.getDayOfWeek();
		String timeOfDay = section.getTimeOfDay();
		String courseNo = section.getRepresentedCourse().getCourseNo();
		String pssn = section.getInstructor().getSsn();
		String room = section.getRoom();
		int seatingCapacity = section.getSeatingCapacity();

		String sql = "insert into Section values('" + sectionNo + "','" + dayOfWeek + "','" + timeOfDay + "','"
				+ courseNo + "','" + pssn + "','" + room + "','" + seatingCapacity + "')";
		Connection conn = DBUtil.getSqliteConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("�������쳣��" + e.getMessage());
		}
	}

	@Override
	public void deleteSection(Section section) {
		int sectionNo = section.getSectionNo();
		Connection conn = DBUtil.getSqliteConnection();
		String sql = "delete from Section where sectionNo='" + sectionNo + "'";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ������쳣��" + e.getMessage());
		}
	}

	@Override
	public void updateSection(Section section) {
		Connection conn = DBUtil.getSqliteConnection();
		String sql = "update Section set dayOfWeek='" + section.getDayOfWeek() + "',timeOfDay='"
				+ section.getTimeOfDay() + "',courseNo='" + section.getRepresentedCourse().getCourseNo() + "',Pssn='"
				+ section.getInstructor().getSsn() + "',room='" + section.getRoom() + "',seatingCapacity="
				+ section.getSeatingCapacity() + " where sectionNo=" + section.getSectionNo();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ������쳣��" + e.getMessage());
		}
	}
}
