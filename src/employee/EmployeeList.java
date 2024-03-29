package employee;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import chipotlelogic.Deployment;
import chipotlelogic.Rank;

public class EmployeeList implements Iterable<Employee> {
	private static ArrayList<Employee> employeeList;
	private FileInputStream in;
	private FileOutputStream out;

	/**
	 * Non-arg constructor initializes employeeList as a new ArrayList<Employee>
	 */
	public EmployeeList() {
		employeeList = new ArrayList<Employee>();
	}

	/**
	 * Constructor takes a String as a parameter. The String will be the name of a
	 * .dat file that it will read from to get an EmployeeList from
	 *
	 * @param file
	 *            The .dat file that contains data for the employeeList
	 */
	public EmployeeList(String file) {
		employeeList = readFromFile(file).getList(); // readFromFile() returns an EmployeeList, but getList() returns an
														// ArrayList<Employee>
	}

	/**
	 * Implements Iteratable.iterator(), delegates to ArrayList<> iterator
	 *
	 * @return: ArrayList<> iterator
	 */
	public Iterator<Employee> iterator() {
		Iterator<Employee> itr = employeeList.iterator();
		return itr;
	}

	/**
	 * Adds an employee to the employeeList
	 *
	 * @param emp
	 *            the employee to be added
	 */
	public void addEmployee(Employee emp) {
		employeeList.add(emp);
	}

	/**
	 * Removes an employee from employeeList
	 */
	public void removeEmployee(Employee emp) {
		employeeList.remove(emp);
	}

	/**
	 * Sorts the EmployeeList alphabetically, and then by rank
	 */
	public void sort() {
		// Sorts employees alphabetically by last name
		ArrayList<Employee> sort = new ArrayList<Employee>();
		Collections.sort(employeeList);

		// Sorts by rank
		for (Rank r : Rank.values()) {
			for (Employee e : employeeList) {
				if (e.getRank().equals(r)) {
					sort.add(e);
				}
			}
		}

		employeeList = sort;
	}

	/**
	 * Returns an ArrayList<Employee> with elements sorted by availability
	 * @return an ArrayList<Employee> with elements sorted by availability
	 */
	public static ArrayList<Employee> sortByAvailability() {
		ArrayList<Employee> sort = new ArrayList<Employee>();

		for(Employee e : employeeList) {
			sort.add(e);
		}

		return quickSort(sort, 0, sort.size() - 1);


		}

	/**
	 * Quick sort helper method
	 * @param list:	List to sort
	 * @param a:	i
	 * @param b:	j
	 * @return		ArrayList<Employee> sorted by availability
	 */
	private static ArrayList<Employee> quickSort(ArrayList<Employee> list, int a, int b) {
    if (a < b) {
        Employee pivot = list.get(b);
        int left = a;
        int right = b - 1;

        while (left <= right) {
            while (left <= right && list.get(left).compareAvailability(pivot) < 0) {
                left++;
            }
            while (right >= left && list.get(right).compareAvailability(pivot) >= 0) {
                right--;
            }
            if (left < right) {
                Collections.swap(list, left, right);
            }
        }

        Collections.swap(list, left, b);

        quickSort(list, a, left - 1);
        quickSort(list, left + 1, b);
    }

    return list;
}

	/**
	 * Returns employeeList
	 *
	 * @return employeeList
	 */
	public static ArrayList<Employee> getList() {
		return employeeList;
	}

	/**
	 * Returns list of employees that known particular deployment
	 *
	 * @param d
	 *          the deployment that employee must know to be part of returned list
	 * @return list of employees that known particular deployment
	 */
	public static ArrayList<Employee> getEmployeesByDeployment(Deployment d) {
		ArrayList<Employee> employeesByDeployment = new ArrayList<Employee>();

		for (Employee e : employeeList) {
			if (e.getKnownDeployments().contains(d)) {
				employeesByDeployment.add(e);
			}
		}

		return employeesByDeployment;
	}

	/**
	 * Writes the EmployeeList to a file to save the data
	 *
	 * @param file
	 *            the file name as a string to be written to
	 */
	public void writeToFile(String file) {
		// Cannot find a way to NOT initialize as null without compile-time errors.
		// Either "May not have been
		// initialized" or "cannot be resolved" in finally block
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			for (Employee e : employeeList) {
				oos.writeObject(e);
			}

		} catch (IOException ioException) {
			System.err.println("Error writing to file.");
		} catch (NoSuchElementException noSuchElementException) {
			System.err.println("Invalid input.");
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (IOException ioException) {
				System.err.println("Error closing file.");
			}
		}
	}

	/**
	 * Reads Employee objects from a file and adds them to an EmployeeList that we will return
	 * @param file The file to read from
	 * @throws IOException
	 */
	public EmployeeList readFromFile(String file) {

		// If the file already exists, this code does not really do anything.
		File fileToWriteTo = new File(file);
		try {
			fileToWriteTo.createNewFile();
		} catch (IOException i) {
			i.printStackTrace();
		}

		// Create an EmployeeList to transfer data from file into
		EmployeeList emp = new EmployeeList();

		// Cannot find a way to NOT initialize as null without compile-time errors.
		// Either "May not have been
		// initialized" or "cannot be resolved" in finally block
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			// Will just continuously run through loop. When it runs out of objects to read,
			// it will throw (and catch) an EOFException, which is handled by just returning
			// the EmployeeList we've added Employee objects we read into
			while (true) {
				Employee e = (Employee) ois.readObject();
				emp.addEmployee(e);
			}
		} catch (EOFException eofException) {
			// If this exception is caught, there are no more Employee objects to read, so
			// we can go ahead and return the EmployeeList we've added the Employees to.
			return emp;
		} catch (ClassNotFoundException classNotFoundException) {
			System.err.println("Object creation failed.");
		} catch (IOException ioException) {
			System.err.println("Error reading file.");
		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (IOException ioException) {
				System.err.println("Error closing file.");
			}
		}
		return emp;
	}

	/**
	 * Returns length of employeeList
	 * @return length of employeeList
	 */
	public int getLength() {
		return employeeList.size();
	}

	@Override
	public String toString() {
		String string = "";

		// Just prints the name of the employees on new lines
		for (Employee e : this.employeeList) {
			if (string != "") {
				string = string + "\n" + e;
			} else {
				string += e;
			}
		}
		return string;
	}

}
