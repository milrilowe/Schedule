package schedulelogic;
import java.util.ArrayList;
import java.util.Iterator;

import chipotlelogic.DaysOfWeek;

/**
 * Week is an ArrayList of Days.  Each day will need to be scheduled for each deployment
 *
 */
public class Week {
    public static ArrayList<DaysOfWeek> week;

    public Week() {
		//You need a static array to keep track of hours worked throughout week - Day object has one for each day, you can just tally up from there
		//This will just be an arraylist with 7 days
    }

}
