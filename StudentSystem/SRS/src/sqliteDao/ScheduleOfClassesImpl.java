package sqliteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import dao.ScheduleOfClassesDao;
import model.Course;
import model.Professor;
import model.ScheduleOfClasses;
import model.Section;
import util.DBUtil;

public class ScheduleOfClassesImpl implements ScheduleOfClassesDao {
	@Override
	public ScheduleOfClasses getScheduleOfClassess(String semester) {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "select * from ScheduleOfClasses,Professor,Course,Section where ScheduleOfClasses.courseNo=Course.courseNo and ScheduleOfClasses.sectionNo=Section.sectionNo and Section.Pssn=Professor.Pssn and ScheduleOfClasses.semester='" + semester + "'";
		ScheduleOfClasses schedule = new ScheduleOfClasses(semester);
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Course course=new Course(rs.getString("courseNo"),rs.getString("courseName"),rs.getDouble("credits"));
				Section section = new Section(rs.getInt("sectionNo"),rs.getString("dayOfWeek"),rs.getString("timeOfDay"),course,rs.getString("room"),rs.getInt("seatingCapacity"));
				Professor professor=new Professor(rs.getString("professorName"),rs.getString("Pssn"),rs.getString("password"),rs.getString("title"),rs.getString("department"));
				section.setInstructor(professor);
				schedule.addSection(section);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedule;
	}

	@Override
	public List<Section> getAllSectionsOffered(ScheduleOfClasses schedule) {
		List<Section> sectionsList = new ArrayList<Section>();
		HashMap<String, Section> sectionsOffered = schedule.getSectionsOffered();
		Iterator<Entry<String, Section>> iter = sectionsOffered.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Section> entry = (Entry<String, Section>) iter.next();
			/* String fullSectionNo = entry.getKey(); */
			Section section = entry.getValue();
			sectionsList.add(section);
		}
		return sectionsList;
	}

	@Override
	public void addScheduleOfClasses(ScheduleOfClasses schedule) {
		String semester = schedule.getSemester();
		HashMap<String, Section> sections = schedule.getSectionsOffered();
		Iterator<Entry<String, Section>> iter = sections.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Section> entry = (Entry<String, Section>) iter.next();
			/* String fullSectionNo = entry.getKey(); */
			Section section = entry.getValue();
			int sectionNo = section.getSectionNo();
			String courseNo = section.getRepresentedCourse().getCourseNo();
			String sql = "insert into ScheduleOfClasses values('" + semester + "','" + sectionNo + "','" + courseNo
					+ "')";
			Connection conn = DBUtil.getSqliteConnection();
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("����γ̼ƻ��쳣��" + e.getMessage());
			}
		}
	}

	@Override
	public void deleteScheduleOfClasses(ScheduleOfClasses schedule) {
		String semester = schedule.getSemester();
		HashMap<String, Section> sections = schedule.getSectionsOffered();
		Iterator<Entry<String, Section>> iter = sections.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Section> entry = (Entry<String, Section>) iter.next();
			/* String fullSectionNo = entry.getKey(); */
			Section section = entry.getValue();
			int sectionNo = section.getSectionNo();
			String courseNo = section.getRepresentedCourse().getCourseNo();
			String sql = "delete from ScheduleOfClasses where semester='" + semester + "' and sectionNo=" + sectionNo
					+ " and courseNo='" + courseNo + "'";
			Connection conn = DBUtil.getSqliteConnection();
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("ɾ���γ̼ƻ��쳣��" + e.getMessage());
			}
		}
	}
}
