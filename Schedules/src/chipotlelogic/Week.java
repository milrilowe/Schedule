package chipotlelogic;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Week is an ArrayList of Days.  Each day will need to be scheduled for each deployment
 *
 */
public class Week implements Iterable<Day> {
    private ArrayList<Day> week;

    public Week() {
        week = new ArrayList<Day>();
        for (Day day : Day.values()) {
            week.add(day);
        }
    }



    /**
	 * Returns week
	 * 
	 * @return week
	 */
	public ArrayList<Day> getList() {
		return week;
	}

    /**
	 * Implements Iteratable.iterator(), delegates to ArrayList<> iterator
	 * 
	 * @return: ArrayList<> iterator
	 */
	public Iterator<Day> iterator() {
		Iterator<Day> itr = week.iterator();
		return itr;
	}
}
