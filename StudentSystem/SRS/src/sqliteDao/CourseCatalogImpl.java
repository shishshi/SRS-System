package sqliteDao;

import java.util.List;

import dao.CourseCatalogDao;
import dao.CourseDao;
import dao.dataAccess;
import model.Course;
import model.CourseCatalog;

public class CourseCatalogImpl implements CourseCatalogDao {

	@Override
	public CourseCatalog getCourseCatalog() {
		CourseCatalog courseCatalog = new CourseCatalog();
		CourseDao cd = dataAccess.createCourseDao();
		List<Course> courses = cd.getAllCourses();
		for (Course c : courses) {
			courseCatalog.addCourse(c);
		}
		return courseCatalog;
	}

}
