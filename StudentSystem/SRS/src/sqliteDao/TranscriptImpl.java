package sqliteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.TranscriptDao;
import model.Course;
import model.Professor;
import model.Section;
import model.Student;
import model.Transcript;
import model.TranscriptEntry;
import util.DBUtil;

public class TranscriptImpl implements TranscriptDao {
	@Override
	public Transcript getTranscriptByStudent(Student student) {
		String Sssn = student.getSsn();
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "select * from TranscriptEntry,Section,Course,Professor where TranscriptEntry.sectionNo=Section.sectionNo and Section.courseNo=Course.courseNo and Section.Pssn=Professor.Pssn and TranscriptEntry.Sssn='"
				+ Sssn + "'";
		Transcript t = new Transcript(student);
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int transEntryNo = rs.getInt("transEntryNo");
				Course course = new Course(rs.getString("courseNo"), rs.getString("courseName"),
						rs.getDouble("credits"));
				Section section = new Section(rs.getInt("sectionNo"), rs.getString("dayOfWeek"),
						rs.getString("timeOfDay"), course, rs.getString("room"), rs.getInt("seatingCapacity"));
				Professor professor = new Professor(rs.getString("professorName"), rs.getString("Pssn"),rs.getString("password"),
						rs.getString("title"), rs.getString("department"));
				section.setInstructor(professor);

				String gradeReceived = rs.getString("gradeReceived");
				TranscriptEntry te = new TranscriptEntry(transEntryNo, student, gradeReceived, section);
				t.addTranscriptEntry(te);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
}
