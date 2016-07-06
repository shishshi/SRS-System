package mysqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.StudentDao;
import model.Course;
import model.Professor;
import model.Section;
import model.Student;
import util.DBUtil;


public class StudentImpl implements StudentDao {
	@Override
	public List<Student> getAllStudents() {
		Connection Conn = DBUtil.getMySqlConnection();
		String sql = "select * from Student";
		List<Student> studentList = new ArrayList<Student>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String Sssn = rs.getString("Sssn");
				String password = rs.getString("password");
				String major = rs.getString("major");
				String degree = rs.getString("degree");
				String studentName = rs.getString("studentName");
				Student student = new Student(studentName,Sssn,password,major,degree);
				studentList.add(student);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}
@Override
public String getPassword(String Sssn){
		
		String sp = "";
		Connection Conn = DBUtil.getMySqlConnection();
		String getPassword = "select password from Student where Sssn='" + Sssn + "'";
		try {
			PreparedStatement pstmt = Conn.prepareStatement(getPassword);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				sp = rs.getString("password");
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sp;
		
	}
@Override	
public Student searchStudent(String Sssn) {
	Student student = null ;
	String searchStudent="select * from Student where Sssn='"+Sssn+"'";
	Connection Conn = DBUtil.getMySqlConnection();
	try {
		PreparedStatement pstmt = Conn.prepareStatement(searchStudent);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			String name = rs.getString("name");
			String password = rs.getString("password");
			String major = rs.getString("major");
			String degree = rs.getString("degree");
			student=new Student(Sssn,name,password,major,degree);			
		}
		rs.close();
		pstmt.close();
		Conn.close();
	}catch(Exception e){
	    e.printStackTrace();	
	}
	return student;
}

	

	@Override
	public List<Section> getEnrolledSections(Student student) {
		String Sssn = student.getSsn();
		List<Section> enrolledSections = new ArrayList<Section>();
		String sql = "select * from Student_Section,Professor,Section,Course where Student_Section.sectionNo=Section.sectionNo and Section.Pssn=Professor.Pssn and Section.courseNo=Course.courseNo and Student_Section.Sssn='"
				+ Sssn + "'";
		Connection Conn = DBUtil.getMySqlConnection();
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
				enrolledSections.add(section);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enrolledSections;
	}

	@Override
	public void addStudent(Student student) {
		String Sssn = student.getSsn();
		String major = student.getMajor();
		String degree = student.getDegree();
		String studentName = student.getName();

		String sql = "insert into Student values('" + Sssn + "','" + studentName + "','" + major + "','" + degree
				+ "')";
		Connection conn = DBUtil.getMySqlConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("�����ʦ�쳣��" + e.getMessage());
		}
	}

	@Override
	public void deleteStudent(Student student) {
		String Sssn = student.getSsn();
		Connection conn = DBUtil.getMySqlConnection();
		String sql = "delete from Student where Sssn='" + Sssn + "'";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ����ʦ�쳣��" + e.getMessage());
		}
	}

	@Override
	public void updateStudent(Student student) {
		Connection conn = DBUtil.getMySqlConnection();
		String sql = "update Student set studentName='" + student.getName() + "',major='" + student.getMajor()
				+ "',degree='" + student.getDegree() + "' where Sssn='" + student.getSsn() + "'";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ����ʦ�쳣��" + e.getMessage());
		}
	}

	@Override
	public void addEnrolledSection(Student student, int sectionNo) {
		Connection conn = DBUtil.getMySqlConnection();
		String sql = "insert into Student_Section (Sssn,sectionNo) values('" + student.getSsn() + "'," + sectionNo
				+ ")";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ����ʦ�쳣��" + e.getMessage());
		}
	}

	@Override
	public void dropEnrolledSection(Student student, int sectionNo) {
		Connection conn = DBUtil.getMySqlConnection();
		String sql = "delete from Student_Section where Sssn='" + student.getSsn() + "' and sectionNo=" + sectionNo;
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ����ʦ�쳣��" + e.getMessage());
		}
	}
	@Override
	public Student getStudent(String Sssn) {
		// TODO Auto-generated method stub
		return null;
	}
}