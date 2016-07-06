package mysqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.TranscriptEntryDao;
import model.Course;
import model.Professor;
import model.Section;
import model.Student;
import model.TranscriptEntry;
import util.DBUtil;

public class TranscriptEntryImpl implements TranscriptEntryDao {
	@Override
	public List<TranscriptEntry> getAllTranscriptEntrys() {
		Connection Conn = DBUtil.getMySqlConnection();
		String sql = "select * from TranscriptEntry,Student,Section,Course,Professor where TranscriptEntry.Sssn=Student.Sssn and TranscriptEntry.sectionNo=Section.sectionNo and Section.courseNo=Course.courseNo and Section.Pssn=Professor.Pssn";
		List<TranscriptEntry> transcriptEntryList = new ArrayList<TranscriptEntry>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int transcriptEntryNo = rs.getInt("transEntryNo");
				String gradeReceived = rs.getString("gradeReceived");
				Student student = new Student(rs.getString("studentName"), rs.getString("Sssn"), rs.getString("password"),rs.getString("major"),
						rs.getString("degree"));
				Course course = new Course(rs.getString("courseNo"), rs.getString("courseName"),
						rs.getDouble("credits"));
				Section section = new Section(rs.getInt("sectionNo"), rs.getString("dayOfWeek"),
						rs.getString("timeOfDay"), course, rs.getString("room"), rs.getInt("seatingCapacity"));
				Professor professor = new Professor(rs.getString("professorName"), rs.getString("Pssn"),rs.getString("password"),
						rs.getString("title"), rs.getString("department"));
				section.setInstructor(professor);

				TranscriptEntry transcriptEntry = new TranscriptEntry(transcriptEntryNo, student, gradeReceived,
						section);
				transcriptEntryList.add(transcriptEntry);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transcriptEntryList;
	}

	@Override
	public TranscriptEntry getTranscriptEntry(int transEntryNo) {
		String sql = "select * from TranscriptEntry,Student,Section,Course,Professor where TranscriptEntry.Sssn=Student.Sssn and TranscriptEntry.sectionNo=Section.sectionNo and Section.courseNo=Course.courseNo and Section.Pssn=Professor.Pssn and TranscriptEntry.transEntryNo='"
				+ transEntryNo + "'";
		Connection Conn = DBUtil.getMySqlConnection();
		TranscriptEntry transcriptEntry = null;
		List<TranscriptEntry> transcriptEntryList = new ArrayList<TranscriptEntry>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String gradeReceived = rs.getString("gradeReceived");
				Student student = new Student(rs.getString("studentName"), rs.getString("Sssn"), rs.getString("password"),rs.getString("major"),
						rs.getString("degree"));

				Course course = new Course(rs.getString("courseNo"), rs.getString("courseName"),
						rs.getDouble("credits"));
				Section section = new Section(rs.getInt("sectionNo"), rs.getString("dayOfWeek"),
						rs.getString("timeOfDay"), course, rs.getString("room"), rs.getInt("seatingCapacity"));
				Professor professor = new Professor(rs.getString("professorName"), rs.getString("Pssn"),rs.getString("password"),
						rs.getString("title"), rs.getString("department"));
				section.setInstructor(professor);

				transcriptEntry = new TranscriptEntry(transEntryNo, student, gradeReceived, section);
				transcriptEntryList.add(transcriptEntry);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transcriptEntry;
	}

	@Override
	public void addTranscriptEntry(TranscriptEntry transcriptEntry) {
		String Sssn = transcriptEntry.getStudent().getSsn();
		int sectionNo = transcriptEntry.getSection().getSectionNo();
		String gradeReceived = transcriptEntry.getGrade();

		String sql = "insert into TranscriptEntry (Sssn,sectionNo,gradeReceived) values('" + Sssn + "','" + sectionNo
				+ "','" + gradeReceived + "')";
		Connection conn = DBUtil.getMySqlConnection();
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
	public void deleteTranscriptEntry(TranscriptEntry transcriptEntry) {
		int transcriptEntryNo = transcriptEntry.getTransEntryNo();
		Connection conn = DBUtil.getMySqlConnection();
		String sql = "delete from TranscriptEntry where transEntryNo='" + transcriptEntryNo + "'";
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
	public void updateTranscriptEntry(TranscriptEntry transcriptEntry) {
		Connection conn = DBUtil.getMySqlConnection();
		String sql = "update TranscriptEntry set Sssn='" + transcriptEntry.getStudent().getSsn() + "',sectionNo="
				+ transcriptEntry.getSection().getSectionNo() + ",gradeReceived='" + transcriptEntry.getGrade()
				+ "' where transEntryNo=" + transcriptEntry.getTransEntryNo();
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
