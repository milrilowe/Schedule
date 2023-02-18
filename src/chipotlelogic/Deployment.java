package chipotlelogic;

/**
 * Deployment are the positions at Chipotle.  When creating a schedule, for each deployment, at least one employee must be scheduled that knows the deployment.
 *
 */
public enum Deployment {
	grill("Grill"),
	grill2("Grill 2"),
	dish("Dish"),
	lobby("Lobby"),
	tortilla("Tortilla"),
	salsa("Salsa"),
	cash("Cash"),
	prep("Prep");

	private String toString;

	Deployment(String toString) {
		this.toString = toString;
	}

	public String toString() {
		return toString;
	}
}