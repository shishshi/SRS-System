package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.dataAccess;
import dao.ProfessorDao;
import dao.StudentDao;
import model.Professor;
import model.Student;


/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String Ssn = request.getParameter("Ssn");
		String Pwd = request.getParameter("Pwd");
		int role = Integer.parseInt(request.getParameter("role"));
		
		Student student=null;Professor professor=null;
		boolean isValidated=false;
		if(role==1){//role1:学生
			StudentDao sd = dataAccess.createStudentDao();
			String sp=sd.getPassword(Ssn);	
			if(sp.equals(Pwd)){
				isValidated=true;
				student=sd.searchStudent(Ssn);
			}	
		}else if(role==2){//role2:教师
			ProfessorDao pd= dataAccess.createProfessorDao();
			String pp=pd.getPassword(Ssn);
			if(pp.equals(Pwd)){
				isValidated=true;
				professor=pd.searchProfessor(Ssn);
			}	
		}else{//role3:管理员
			if(Ssn.equals("zl")&&Pwd.equals("zl")){
				isValidated=true;
			}
		}
		
		if(isValidated){
			System.out.println("登录成功");
			HttpSession session = request.getSession();
			session.setAttribute("role", role);
			if(role==1){
				session.setAttribute("Student", student);
				session.setAttribute("isLogined", "yes");
				response.sendRedirect("SRS.html");
			}else if(role==2){
				session.setAttribute("Professor", professor);
				session.setAttribute("isLogined", "yes");
				response.sendRedirect("SRS.html");
			}else{
				session.setAttribute("Admin", "zl");
				session.setAttribute("isLogined", "yes");
				response.sendRedirect("CourseIndex.html");
			}
			
		}else{
			System.out.println("登录失败");
			HttpSession session = request.getSession();
			session = request.getSession();
			session.setAttribute("isLogined", "no");
			response.sendRedirect("html/login.html");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
