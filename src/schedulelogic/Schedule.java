package schedulelogic;
import java.util.ArrayList;
import java.util.Iterator;

import chipotlelogic.Week;
import employee.Employee;
import schedulelogic.Day;

/**
 * Week is an ArrayList of Days.  Each day will need to be scheduled for each deployment
 *
 */
public class Schedule implements Iterable<Day> {
  public static ArrayList<Day> schedule = new ArrayList<Day>();
  //You need a static array to keep track of hours worked throughout week - Day object has one for each day, you can just tally up from there
  //This will just be an arraylist with 7 days

  public Schedule() {
    schedule.clear();
    for (int i = 0; i < 7; i++) {
      schedule.add(new Day(i));
    }
  }

  /**
   *
   */
  public static int getHoursScheduled(Employee e) {
    int hours = 0;

    for (Day day : schedule) {
      hours += day.getHoursScheduled(e);
    }

    return hours;
  }

  @Override
  public Iterator<Day> iterator() {
    return schedule.iterator();
  }

  @Override
  public String toString() {
    String s = "";

    for (Day d : schedule) {
      s += d.toString() + "\n";
    }

    return s;
  }

}
