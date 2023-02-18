package chipotlelogic;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Week implements Iterable<DayOfWeek> {

	private List<DayOfWeek> days;

	public Week() {
		this.days = Arrays.asList(DayOfWeek.values());
	}

	@Override
	public Iterator<DayOfWeek> iterator() {
		return days.iterator();
	}

}