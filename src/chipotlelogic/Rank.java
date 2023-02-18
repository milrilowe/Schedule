package chipotlelogic;

/**
 * The rank of the employee (General Manager, Apprentice, Service Manager,
 * Kitchen Manager, or Crew)
 * 
 * @author Miles
 *
 */
public enum Rank {
	gm("General Manager"), apprentice("Apprentice"), sm("Service Manager"), km("Kitchen Manager"), crew("Crew");

	private String toString;

	Rank(String toString) {
		this.toString = toString;
	}

	public String toString() {
		return toString;
	}
};
