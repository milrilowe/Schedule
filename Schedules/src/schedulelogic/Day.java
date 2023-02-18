package schedulelogic;

import employee.Employee;
import employee.EmployeeList;
import java.util.ArrayList;
import java.util.Collections;

import chipotlelogic.Deployment;

/**
 *
 */
public class Day {
    int day; // Corresponds to day of week (e.g. 0 => Monday, 6 => Sunday)
    final int HOURS = 17; //Store has 17 working hours (7:00AM - 11:00PM)

    Employee[] grill1 = new Employee[HOURS];
    Employee[] grill2 = new Employee[HOURS];
    Employee[] cash = new Employee[HOURS];
    Employee[] prep = new Employee[HOURS];
    Employee[] dish = new Employee[HOURS];
    Employee[] lobby = new Employee[HOURS];
    Employee[] tortilla = new Employee[HOURS];
    Employee[] salsa = new Employee[HOURS];

    //ArrayList used to keep tracking of who is working at what time, so they aren't scheduled to work multiple deployments at one time.
    ArrayList<Employee>[] working;
    ArrayList<Employee> scheduled;

    ArrayList<Employee> hoursWorked;

    //ArrayList sorted by amount of availability employee gives, so that we prioritize scheduling those who offer few hours, otherwise they may never get scheduled.
    ArrayList<Employee> sortedEmployeeList;

    public Day(int day) {
        this.day = day;

        sortedEmployeeList = EmployeeList.sortByAvailability();
        working = (ArrayList<Employee>[]) new ArrayList[HOURS];
        scheduled = new ArrayList<Employee>();

        // Initialize working array with empty ArrayLists
        for (int i = 0; i < working.length; i++) {
            working[i] = new ArrayList<>();
        }

        hoursWorked = new ArrayList<Employee>();

        deployGrill();
        deployGrill2();
        deployCash();
        deployPrep();
        deployDish();
        deployLobby();
        deployTortilla();
        deploySalsa();

    }

    /**
     * Populates grill array with employees. Each index represents an hour of work from open to close
     */
    private void deployGrill() {
        ArrayList<Employee> employeesScheduled = new ArrayList<Employee>();

        for (int i = 0; i < grill1.length; i++) {
            // Check if there are employees available for this deployment
            if (!EmployeeList.getEmployeesByDeployment(Deployment.grill).isEmpty()) {
                for (Employee e : sortedEmployeeList) {
                    int[] availability = e.getAvailability();


                    int conversion = ((day) * 17) + i + day + 1;
                    boolean isAvailable = false;

                    for (int j = 0; j < availability.length && !isAvailable; j++) {
                        if (availability[j] == conversion) {
                            isAvailable = true;
                        }
                    }


                    if(isAvailable && Collections.frequency(hoursWorked, e) < 9 && Schedule.getHoursScheduled(e) + this.getHoursScheduled(e) < 36) { // Don't want to schedule more than 9 hours a day

                        if (!working[i].contains(e)) {
                            if (e.getKnownDeployments().contains(Deployment.grill)) {
                                grill1[i] = e;
                                working[i].add(e);
                                hoursWorked.add(e);
                                if (!employeesScheduled.contains(e)) {
                                    employeesScheduled.add(e);
                                }
                                break; // Break out of inner loop after employee has been scheduled
                            }
                        }
                    }
                }
            }
            if (i == 7 || i == grill1.length - 1) {
                for (Employee e : employeesScheduled) {
                    sortedEmployeeList.remove(e);
                }
            }
        }
    }

    /**
     * Populates grill2 array with employees.  Each index represents an hour of work from open to close
     */
    private void deployGrill2() {
        ArrayList<Employee> employeesScheduled = new ArrayList<Employee>();

        for (int i = 0; i < grill2.length; i++) {
            // Check if there are employees available for this deployment
            if (!EmployeeList.getEmployeesByDeployment(Deployment.grill2).isEmpty()) {
                                for (Employee e : sortedEmployeeList) {
                    int[] availability = e.getAvailability();


                    int conversion = ((day) * 17) + i + day + 1;
                    boolean isAvailable = false;

                    for (int j = 0; j < availability.length && !isAvailable; j++) {
                        if (availability[j] == conversion) {
                            isAvailable = true;
                        }
                    }


                    if(isAvailable && Collections.frequency(hoursWorked, e) < 9 && Schedule.getHoursScheduled(e) + this.getHoursScheduled(e) < 36) { // Don't want to schedule more than 9 hours a day
                        if (!working[i].contains(e)) {
                            if (e.getKnownDeployments().contains(Deployment.grill2)) {
                                grill2[i] = e;
                                working[i].add(e);
                                hoursWorked.add(e);
                                if (!employeesScheduled.contains(e)) {
                                    employeesScheduled.add(e);
                                }
                                break; // Break out of inner loop after employee has been scheduled
                            }
                        }
                    }
                }
            }
            if (i == 7 || i == grill2.length - 1) {
                for (Employee e : employeesScheduled) {
                    sortedEmployeeList.remove(e);
                }
            }
        }
    }

    /**
     * Populates prep array with employees.  Each index represents an hour of work from open to close
     */
    private void deployPrep() {
        ArrayList<Employee> employeesScheduled = new ArrayList<Employee>();

        for (int i = 1; i < prep.length; i++) {
            // Check if there are employees available for this deployment
            if (!EmployeeList.getEmployeesByDeployment(Deployment.prep).isEmpty()) {
                for (Employee e : sortedEmployeeList) {
                    int[] availability = e.getAvailability();


                    int conversion = ((day) * 17) + i + day + 1;
                    boolean isAvailable = false;

                    for (int j = 0; j < availability.length && !isAvailable; j++) {
                        if (availability[j] == conversion) {
                            isAvailable = true;
                        }
                    }


                    if(isAvailable && Collections.frequency(hoursWorked, e) < 9 && Schedule.getHoursScheduled(e) + this.getHoursScheduled(e) < 36) { // Don't want to schedule more than 9 hours a day
                        if (!working[i].contains(e)) {
                            if (e.getKnownDeployments().contains(Deployment.prep)) {
                                prep[i] = e;
                                working[i].add(e);
                                hoursWorked.add(e);
                                if (!employeesScheduled.contains(e)) {
                                    employeesScheduled.add(e);
                                }
                                break; // Break out of inner loop after employee has been scheduled
                            }
                        }
                    }
                }
            }
            if (i == 7 || i == prep.length - 1) {
                for (Employee e : employeesScheduled) {
                    sortedEmployeeList.remove(e);
                }
            }
        }
    }

    /**
     * Populates dish array with employees.  Each index represents an hour of work from open to close
     */
    private void deployDish() {
        ArrayList<Employee> employeesScheduled = new ArrayList<Employee>();

        for (int i = 4; i < dish.length; i++) {
            // Check if there are employees available for this deployment
            if (!EmployeeList.getEmployeesByDeployment(Deployment.dish).isEmpty()) {
                for (Employee e : sortedEmployeeList) {
                    int[] availability = e.getAvailability();


                    int conversion = ((day) * 17) + i + day + 1;
                    boolean isAvailable = false;

                    for (int j = 0; j < availability.length && !isAvailable; j++) {
                        if (availability[j] == conversion) {
                            isAvailable = true;
                        }
                    }


                    if(isAvailable && Collections.frequency(hoursWorked, e) < 9 && Schedule.getHoursScheduled(e) + this.getHoursScheduled(e) < 36) { // Don't want to schedule more than 9 hours a day
                        if (!working[i].contains(e)) {
                            if (e.getKnownDeployments().contains(Deployment.dish)) {
                                dish[i] = e;
                                working[i].add(e);
                                hoursWorked.add(e);
                                if (!employeesScheduled.contains(e)) {
                                    employeesScheduled.add(e);
                                }
                                break; // Break out of inner loop after employee has been scheduled
                            }
                        }
                    }
                }
            }
            if (i == 7 || i == dish.length - 1) {
                for (Employee e : employeesScheduled) {
                    sortedEmployeeList.remove(e);
                }
            }
        }
    }

    /**
     * Populates salsa array with employees.  Each index represents an hour of work from open to close.
     */
    private void deploySalsa() {
        ArrayList<Employee> employeesScheduled = new ArrayList<Employee>();

        for (int i = 0; i < salsa.length; i++) {
            // Check if there are employees available for this deployment
            if (!EmployeeList.getEmployeesByDeployment(Deployment.salsa).isEmpty()) {
                                for (Employee e : sortedEmployeeList) {
                    int[] availability = e.getAvailability();


                    int conversion = ((day) * 17) + i + day + 1;
                    boolean isAvailable = false;

                    for (int j = 0; j < availability.length && !isAvailable; j++) {
                        if (availability[j] == conversion) {
                            isAvailable = true;
                        }
                    }


                    if(isAvailable && Collections.frequency(hoursWorked, e) < 9 && Schedule.getHoursScheduled(e) + this.getHoursScheduled(e) < 36) { // Don't want to schedule more than 9 hours a day
                        if (!working[i].contains(e)) {
                            if (e.getKnownDeployments().contains(Deployment.salsa)) {
                                salsa[i] = e;
                                working[i].add(e);
                                hoursWorked.add(e);
                                if (!employeesScheduled.contains(e)) {
                                    employeesScheduled.add(e);
                                }
                                break; // Break out of inner loop after employee has been scheduled
                            }
                        }
                    }
                }
            }
            if (i == 7 || i == salsa.length - 1) {
                for (Employee e : employeesScheduled) {
                    sortedEmployeeList.remove(e);
                }
            }
        }
    }

    /**
     * Populates lobby array with employees.  Each index represents an hour of work from open to close.  No one is deployed on lobby until 11:00AM
     * so i starts at 4, leaving indices 0-4 empty.  No one is deployed on lobby after 10, so we iterate until i < lobby.length - 1
     */
    private void deployLobby() {
        ArrayList<Employee> employeesScheduled = new ArrayList<Employee>();

        for (int i = 4; i < lobby.length - 2; i++) {
            // Check if there are employees available for this deployment
            if (!EmployeeList.getEmployeesByDeployment(Deployment.lobby).isEmpty()) {
                                for (Employee e : sortedEmployeeList) {
                    int[] availability = e.getAvailability();


                    int conversion = ((day) * 17) + i + day + 1;
                    boolean isAvailable = false;

                    for (int j = 0; j < availability.length && !isAvailable; j++) {
                        if (availability[j] == conversion) {
                            isAvailable = true;
                        }
                    }


                    if(isAvailable && Collections.frequency(hoursWorked, e) < 9 && Schedule.getHoursScheduled(e) + this.getHoursScheduled(e) < 36) { // Don't want to schedule more than 9 hours a day
                        if (!working[i].contains(e)) {
                            if (e.getKnownDeployments().contains(Deployment.lobby)) {
                                lobby[i] = e;
                                working[i].add(e);
                                hoursWorked.add(e);
                                if (!employeesScheduled.contains(e)) {
                                    employeesScheduled.add(e);
                                }
                                break; // Break out of inner loop after employee has been scheduled
                            }
                        }
                    }
                }
            }
            if (i == 7 || i == lobby.length - 1) {
                for (Employee e : employeesScheduled) {
                    sortedEmployeeList.remove(e);
                }
            }
        }
    }

    /**
     * Populates cash array with employees.  Each index represents an hour of work from open to close.  No one is deployed on cash until 11:00AM
     * so i starts at 4, leaving indices 0-4 empty.  No one is deployed on cash after 10, so we iterate until i < lobby.length - 1
     */
    private void deployCash() {
        ArrayList<Employee> employeesScheduled = new ArrayList<Employee>();

        for (int i = 4; i < cash.length - 1; i++) {
            // Check if there are employees available for this deployment
            if (!EmployeeList.getEmployeesByDeployment(Deployment.cash).isEmpty()) {
                                for (Employee e : sortedEmployeeList) {
                    int[] availability = e.getAvailability();


                    int conversion = ((day) * 17) + i + day + 1;
                    boolean isAvailable = false;

                    for (int j = 0; j < availability.length && !isAvailable; j++) {
                        if (availability[j] == conversion) {
                            isAvailable = true;
                        }
                    }


                    if(isAvailable && Collections.frequency(hoursWorked, e) < 9 && Schedule.getHoursScheduled(e) + this.getHoursScheduled(e) < 36) { // Don't want to schedule more than 9 hours a day
                        if (!working[i].contains(e)) {
                            if (e.getKnownDeployments().contains(Deployment.cash)) {
                                cash[i] = e;
                                working[i].add(e);
                                hoursWorked.add(e);
                                if (!employeesScheduled.contains(e)) {
                                    employeesScheduled.add(e);
                                }
                                break; // Break out of inner loop after employee has been scheduled
                            }
                        }
                    }
                }
            }
            if (i == 7 || i == cash.length - 1) {
                for (Employee e : employeesScheduled) {
                    sortedEmployeeList.remove(e);
                }
            }
        }
    }

    /**
     * Populates tortilla array with employees.  Each index represents an hour of work from open to close.  No one is deployed on tortilla until 11:00AM
     * so i starts at 4, leaving indices 0-4 empty.  No one is deployed on tortilla after 10, so we iterate until i < lobby.length - 1
     */
    private void deployTortilla() {
        ArrayList<Employee> employeesScheduled = new ArrayList<Employee>();

        for (int i = 4; i < tortilla.length - 2; i++) {
            // Check if there are employees available for this deployment
            if (!EmployeeList.getEmployeesByDeployment(Deployment.tortilla).isEmpty()) {
                for (Employee e : sortedEmployeeList) {
                    int[] availability = e.getAvailability();


                    int conversion = ((day) * 17) + i + day + 1;
                    boolean isAvailable = false;

                    for (int j = 0; j < availability.length && !isAvailable; j++) {
                        if (availability[j] == conversion) {
                            isAvailable = true;
                        }
                    }


                    if(isAvailable && Collections.frequency(hoursWorked, e) < 9 && Schedule.getHoursScheduled(e) + this.getHoursScheduled(e) < 36) { // Don't want to schedule more than 9 hours a day
                        if (!working[i].contains(e)) {
                            if (e.getKnownDeployments().contains(Deployment.tortilla)) {
                                tortilla[i] = e;
                                    working[i].add(e);
                                    hoursWorked.add(e);
                                    if (!employeesScheduled.contains(e)) {
                                        employeesScheduled.add(e);
                                    }
                                    break; // Break out of inner loop after employee has been scheduled
                                }
                            }
                        }
                }
            }
            if (i == 7 || i == tortilla.length - 1) {
                for (Employee e : employeesScheduled) {
                    sortedEmployeeList.remove(e);
                }
            }
        }
    }

    /**
     *
     */
    public String getEmployeeShift(Employee e) {
        String shift = "";
        int start = -1;
        int end = -1;

        for (int i = 0; i < grill1.length; i++) {
            if (grill1[i] != null && grill1[i].equals(e)) {
                if (start == -1 || i < start) {
                    start = i;
                } else {
                    end = i;
                }
            }
        }

        for (int i = 0; i < grill2.length; i++) {
            if (grill2[i] != null && grill2[i].equals(e)) {
                if (start == -1 || i < start) {
                    start = i;
                } else {
                    end = i;
                }
            }
        }

        for (int i = 0; i < cash.length; i++) {
            if (cash[i] != null && cash[i].equals(e)) {
                if (start == -1 || i < start) {
                    start = i;
                } else {
                    end = i;
                }
            }
        }

        for (int i = 0; i < prep.length; i++) {
            if (prep[i] != null && prep[i].equals(e)) {
                if (start == -1 || i < start) {
                    start = i;
                } else {
                    end = i;
                }
            }
        }

        for (int i = 0; i < dish.length; i++) {
            if (dish[i] != null && dish[i].equals(e)) {
                if (start == -1 || i < start) {
                    start = i;
                } else {
                    end = i;
                }
            }
        }

        for (int i = 0; i < lobby.length; i++) {
            if (lobby[i] != null && lobby[i].equals(e)) {
                if (start == -1 || i < start) {
                    start = i;
                } else {
                    end = i;
                }
            }
        }

        for (int i = 0; i < tortilla.length; i++) {
            if (tortilla[i] != null && tortilla[i].equals(e)) {
                if (start == -1 || i < start) {
                    start = i;
                } else {
                    end = i;
                }
            }
        }

        for (int i = 0; i < salsa.length; i++) {
            if (salsa[i] != null && salsa[i].equals(e)) {
                if (start == -1 || i < start) {
                    start = i;
                } else {
                    end = i;
                }
            }
        }

        if (start != -1 && end != -1) {
            start += 7;
            end += 8;

            if (start < 12) {
                shift += start + ":00 AM";
            } else if (start > 12) {
                shift += start - 12 + ":00 PM";
            } else {
                shift += start + ":00 PM";

            }

            shift += " - ";

            if (end < 12) {
                shift += end + ":00 AM";
            } else if (end > 12) {
                if (end - 12 == 12) {
                    shift += end - 12 + ":00 AM";
                } else {
                    shift += end - 12 + ":00 PM";
                }
            } else {
                shift += end + ":00 PM";
            }
        }

        return shift;
    }

    /**
     *
     */
    public int getHoursScheduled(Employee e) {
        int hours = 0;

        for (int i = 0; i < working.length; i++) {
            if (working[i].contains(e)) {
                hours++;
            }
        }

        return hours;
    }

    @Override
    public String toString() {
        String s = "Grill 1\n";
        s += "------------\n";
        for (Employee e : grill1) {
            if (e != null) {
                s += e.toString() + "\n";
            }
        }

        s += "\n------------\n";
        s += "Grill 2\n";
        s += "------------\n";
        for (Employee e : grill2) {
            if (e != null) {
                s += e.toString() + "\n";
            }
        }

        s += "\n------------\n";
        s += "Cash \n";
        s += "------------\n";
        for (Employee e : cash) {
            if (e != null) {
                s += e.toString() + "\n";
            }
        }

        s += "\n------------\n";
        s += "Prep \n";
        s += "------------\n";
        for (Employee e : prep) {
            if (e != null) {
                s += e.toString() + "\n";
            }
        }

        s += "\n------------\n";
        s += "Lobby \n";
        s += "------------\n";
        for (Employee e : lobby) {
            if (e != null) {
                s += e.toString() + "\n";
            }
        }

        s += "\n------------\n";
        s += "Dish \n";
        s += "------------\n";
        for (Employee e : dish) {
            if (e != null) {
                s += e.toString() + "\n";
            }
        }

        s += "\n------------\n";
        s += "Tortilla \n";
        s += "------------\n";
        for (Employee e : tortilla) {
            if (e != null) {
                s += e.toString() + "\n";
            }
        }

        s += "\n------------\n";
        s += "Salsa \n";
        s += "------------\n";
        for (Employee e : salsa) {
            if (e != null) {
                s += e.toString() + "\n";
            }
        }

        return s;
    }
}
