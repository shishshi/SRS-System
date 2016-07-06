package dao;

import java.util.List;

import model.ScheduleOfClasses;
import model.Section;

public interface ScheduleOfClassesDao {
	ScheduleOfClasses getScheduleOfClassess(String semester);
	List<Section> getAllSectionsOffered(ScheduleOfClasses schedule);
	void addScheduleOfClasses(ScheduleOfClasses schedule);
	void deleteScheduleOfClasses(ScheduleOfClasses schedule);
}
