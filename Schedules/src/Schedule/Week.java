package schedule;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Week is an ArrayList of Days.  Each day will need to be scheduled for each deployment
 *
 */
public class Week implements Iterable<DaysOfWeek> {
    private ArrayList<DaysOfWeek> week;

    public Week() {
        week = new ArrayList<DaysOfWeek>();
        for (DaysOfWeek day : DaysOfWeek.values()) {
            week.add(day);
        }
    }



    /**
	 * Returns week
	 * 
	 * @return week
	 */
	public ArrayList<DaysOfWeek> getList() {
		return week;
	}

    /**
	 * Implements Iteratable.iterator(), delegates to ArrayList<> iterator
	 * 
	 * @return: ArrayList<> iterator
	 */
	public Iterator<DaysOfWeek> iterator() {
		Iterator<DaysOfWeek> itr = week.iterator();
		return itr;
	}
}
