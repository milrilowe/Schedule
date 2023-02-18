package chipotlelogic;
/**
 * Days of the week
 *
 */
public enum DayOfWeek {
	monday("Monday"),
    tuesday("Tuesday"),
    wednesday("Wednesday"),
    thursday("Thursday"),
    friday("Friday"),
    saturday("Saturday"),
    sunday("Sunday");

	private String toString;

	DayOfWeek(String toString) {
		this.toString = toString;
	}

	public String toString() {
		return toString;
	}
};
