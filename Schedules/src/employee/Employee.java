package employee;

import java.io.Serializable;
import java.util.ArrayList;

import chipotlelogic.Deployment;
import chipotlelogic.Rank;

/**
 * Class holds information on an employee. Mostly important for their
 * availability, but also has their name and rank
 * 
 * @author Miles
 *
 */
public class Employee implements Serializable, Comparable<Employee> {
	private int MAX_TIME = 36; // 36 hours is the most hours to be scheduled
	private String fullName;
	private String firstName;
	private String lastName;
	private Rank rank;
	private int[] availability;
	private boolean minor; // Being a minor affects how employee is scheduled to ensure child-labor laws
							// are followed
	private ArrayList<Deployment> knownDeployments;

	/**
	 * Constructor takes a name and a rank
	 * 
	 * @param name:
	 *            The employee's name
	 * @param rank:
	 *            The employee's rank
	 */
	public Employee(String fullName, Rank rank) {
		// Helps to ensure proper formatting and is flexible if a first and last name is
		// not provided. Will automatically convert first letter of first and last name
		// to upper-case. Also splits the String apart and stored into two different
		// Strings for first and last names.
		if (fullName.contains(" ")) {
			String[] name = fullName.split(" ");
			this.firstName = name[0].substring(0, 1).toUpperCase() + name[0].substring(1);
			this.lastName = name[1].substring(0, 1).toUpperCase() + name[1].substring(1);
			this.fullName = this.firstName + " " + this.lastName;
		} else {
			this.firstName = fullName.substring(0, 1).toUpperCase() + fullName.substring(1);
			;
			this.lastName = "";
			this.fullName = fullName;
		}

		this.rank = rank;
		// There are 17 hours possible to be available in a day, 7 days in a week, so 17
		// * 7 possible hours to be available
		availability = new int[17 * 7];
		minor = false;
		knownDeployments = new ArrayList<Deployment>();

	}

	/**
	 * Constructor takes only a name but passes name to name and rank constructor,
	 * using default rank of crew member
	 * 
	 * @param name
	 *            the employee's name
	 */
	public Employee(String name) {
		this(name, Rank.crew);
	}

	/**
	 * Sets the employee's name
	 * 
	 * @param name:
	 *            name to set employee to
	 */
	public void setName(String fullName) {
		// Helps to ensure proper formatting and is flexible if a first and last name is
		// not provided. Will automatically convert first letter of first and last name
		// to upper-case. Also splits the String apart and stored into two different
		// Strings for first and last names.
		if (fullName.contains(" ")) {
			String[] name = fullName.split(" ");
			this.firstName = name[0].substring(0, 1).toUpperCase() + name[0].substring(1);
			this.lastName = name[1].substring(0, 1).toUpperCase() + name[1].substring(1);
			this.fullName = this.firstName + " " + this.lastName;
		} else {
			this.firstName = fullName.substring(0, 1).toUpperCase() + fullName.substring(1);
			;
			this.lastName = "";
			this.fullName = fullName;
		}

	}

	/**
	 * Sets the employee's rank
	 * 
	 * @param rank:
	 *            the rank to be set to
	 */
	public void setRank(Rank rank) {
		this.rank = rank;
	}

	/**
	 * Sets the availability of this employee
	 */
	public void setAvailability(int[] availability) {
		this.availability = availability;
	}

	/**
	 * Sets whether or not the employee is a minor
	 * 
	 * @param minor
	 *            whether or not they are a minor
	 */
	public void setMinor(boolean minor) {
		this.minor = minor;
	}

	/**
	 * Adds a Deployment to the ArrayList of known deployments
	 * 
	 * @param toAdd
	 *            the deployment that is being added to known deployments
	 */
	public void addKnownDeployments(ArrayList<Deployment> toAdd) {
		for (Deployment d : toAdd) {
			knownDeployments.add(d);
		}
	}

	/**
	 * Removes a Deployment from the ArrayList of known deployments
	 * 
	 * @param toRemove
	 *            the deployment that is being removed from known deployments
	 */
	public void removeKnownDeployments(ArrayList<Deployment> toRemove) {
		ArrayList<Deployment> copy = new ArrayList<Deployment>();

		for (Deployment d : this.knownDeployments) {
			copy.add(d);
		}

		for (Deployment d : toRemove) {
			copy.remove(d);
		}

		this.knownDeployments = copy;
	}

	/**
	 * Returns deployments this employee knows
	 * 
	 * @return deployments this employee knows
	 */
	public ArrayList<Deployment> getKnownDeployments() {
		return knownDeployments;
	}

	/**
	 * Returns whether or not this employee knows deployment passed
	 * 
	 * @param deployment
	 *            the deployment we are finding if employee knows
	 * @return boolean value of if they know deployment or not
	 *//**
		 * public boolean knows(Deployment deployment) { for (Deployment d :
		 * knownDeployments) { if (d.equals(deployment)) { return true; } } return
		 * false; }
		 */

	/**
	 * Returns the employee's full name
	 * 
	 * @return the employee's full name
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Returns the employee's first name
	 * 
	 * @return the employee's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the employee's last name
	 * 
	 * @return the employee's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the employee's rank
	 * 
	 * @return the employee's rank
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Returns the availability of the employee
	 * 
	 * @return the availability of the employee
	 */
	public int[] getAvailability() {
		return availability;
	}

	/**
	 * Returns true or false for if the employee is a minor
	 * 
	 * @return true or false for if the employee is a minor
	 */
	public boolean isMinor() {
		return minor;
	}

	@Override
	public int compareTo(Employee compareTo) {
		int com = this.lastName.compareToIgnoreCase(compareTo.getLastName());
		if(com == 0) { //If they have the same last name, compare by first name
			return this.firstName.compareToIgnoreCase(compareTo.getFirstName());
		}
		//Otherwise compare by last name
		return com;

	}

	@Override
	public String toString() {
		return fullName;
	}


	public int compareAvailability(Employee e) {
		if(this.getAvailability().length > e.getAvailability().length) {
			return 1;
		} else if (this.getAvailability().length < e.getAvailability().length) {
			return -1;
		} else {
			return 0;
		}
	}
}
