package mainviewcontroller;

import java.io.IOException;

import javax.swing.SwingUtilities;

import employee.EmployeeList;

/**
 * Contains the main method for the Schedule Generator. Creates a ScheduleView
 * and ScheduleController so that we can have a GUI that allows us to create and
 * edit Employees to be scheduled.
 *
 */
public class ChipotleScheduleGenerator {

	public static void main(String[] args) throws IOException {
		EmployeeList employeeList = new EmployeeList("data.dat");
		SwingUtilities.invokeLater(() -> {
			ScheduleView view = new ScheduleView();
			ScheduleController theController = new ScheduleController(view, employeeList);
			view.setVisible(true);
		});
	}

}
