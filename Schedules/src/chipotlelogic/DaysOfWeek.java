package chipotlelogic;
/**
 * Days of the week
 *
 */
public enum Day {
	monday("Monday"),
    tuesday("Tuesday"),
    wednesday("Wednesday"),
    thursday("Thursday"),
    friday("Friday"),
    saturday("Saturday"),
    sunday("Sunday");

	private String toString;

	Day(String toString) {
		this.toString = toString;
	}

	public String toString() {
		return toString;
	}
};
