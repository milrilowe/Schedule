package schedulelogic;

import employee.Employee;
import employee.EmployeeList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import chipotlelogic.Deployment;

/**
 * 
 */
public class Day {

    final int HOURS = 17; //Store has 17 working hours (7:00AM - 11:00PM)


    Employee[] grill1 = new Employee [HOURS];
    Employee[] grill2 = new Employee [HOURS];
    Employee[] cash = new Employee [HOURS];
    Employee[] prep = new Employee [HOURS];
    Employee[] dish = new Employee [HOURS];
    Employee[] lobby = new Employee [HOURS];
    Employee[] tortilla = new Employee [HOURS];
    Employee[] salsa = new Employee [HOURS];

    //ArrayList used to keep tracking of who is working at what time, so they aren't scheduled to work multiple deployments at one time.
    ArrayList<Employee>[] working;

    ArrayList<Employee> hoursWorked;

    //ArrayList sorted by amount of availability employee gives, so that we prioritize scheduling those who offer few hours, otherwise they may never get scheduled.
    ArrayList<Employee> sortedEmployeeList;
    public Day() {
         
        sortedEmployeeList = EmployeeList.sortByAvailability();
        working = (ArrayList<Employee>[]) new ArrayList[HOURS]; //I'm just trying to avoid using a map - hours make more sense as indexes than as keys.
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
     * Populates grill array with employees.  Each index represents an hour of work from open to close
     */
    private void deployGrill() {
        for(int i = 0; i < grill1.length; i++) {
            for(Employee e : sortedEmployeeList) {
                if(Collections.frequency(hoursWorked, e) < 9) {   //Don't want to schedule more than 9 hours a day
                    if(!working[i].contains(e)) {
                        if(e.getKnownDeployments().contains(Deployment.grill)) {
                            grill1[i] = e;

                            working[i].add(e); 
                        }
                    }
                }
            }
        }
    }

    /**
     * Populates grill2 array with employees.  Each index represents an hour of work from open to close
     */
    private void deployGrill2() {
        for(int i = 0; i < grill2.length; i++) {
            for(Employee e : sortedEmployeeList) {
                if(Collections.frequency(hoursWorked, e) < 9) {
                    if(!working[i].contains(e)) {
                        if(e.getKnownDeployments().contains(Deployment.grill2)) {
                            grill2[i] = e;

                            working[i].add(e); 
                        }
                    }
                }
            }
        }
    }

    /**
     * Populates prep array with employees.  Each index represents an hour of work from open to close
     */
    private void deployPrep() {
        for(int i = 0; i < prep.length; i++) {
            for(Employee e : sortedEmployeeList) {
                if(Collections.frequency(hoursWorked, e) < 9) {
                    if(!working[i].contains(e)) {
                        if(e.getKnownDeployments().contains(Deployment.prep)) {
                            prep[i] = e;

                            working[i].add(e); 
                        }
                    }
                }
            }
        }
    }

    /**
     * Populates dish array with employees.  Each index represents an hour of work from open to close
     */
    private void deployDish() {
        for(int i = 0; i < dish.length; i++) {
            for(Employee e : sortedEmployeeList) {
                if(Collections.frequency(hoursWorked, e) < 9) {
                    if(!working[i].contains(e)) {
                        if(e.getKnownDeployments().contains(Deployment.dish)) {
                            dish[i] = e;

                            working[i].add(e); 
                        }
                    }
                }
            }
        }
    }

    /**
     * Populates salsa array with employees.  Each index represents an hour of work from open to close. 
     */
    private void deploySalsa() {
        for(int i = 0; i < salsa.length; i++) {
            for(Employee e : sortedEmployeeList) {
                if(Collections.frequency(hoursWorked, e) < 9) {
                    if(!working[i].contains(e)) {
                        if(e.getKnownDeployments().contains(Deployment.salsa)) {
                            salsa[i] = e;

                            working[i].add(e); 
                        }
                    }
                }
            }
        }
    }

    /**
     * Populates lobby array with employees.  Each index represents an hour of work from open to close.  No one is deployed on lobby until 11:00AM
     * so i starts at 4, leaving indices 0-4 empty.  No one is deployed on lobby after 10, so we iterate until i < lobby.length - 1
     */
    private void deployLobby() {
        for(int i = 4; i < lobby.length - 1; i++) {
            for(Employee e : sortedEmployeeList) {
                if(Collections.frequency(hoursWorked, e) < 9) {
                    if(!working[i].contains(e)) {
                        if(e.getKnownDeployments().contains(Deployment.lobby)) {
                            lobby[i] = e;

                            working[i].add(e); 
                        }
                    }
                }
            }
        }
    }

    /**
     * Populates cash array with employees.  Each index represents an hour of work from open to close.  No one is deployed on cash until 11:00AM
     * so i starts at 4, leaving indices 0-4 empty.  No one is deployed on cash after 10, so we iterate until i < lobby.length - 1 
     */
    private void deployCash() {
        for(int i = 0; i < cash.length; i++) {
            for(Employee e : sortedEmployeeList) {
                if(Collections.frequency(hoursWorked, e) < 9) {
                    if(!working[i].contains(e)) {
                        if(e.getKnownDeployments().contains(Deployment.cash)) {
                            cash[i] = e;

                            working[i].add(e); 
                        }
                    }
                }
            }
        }
    }

    /**
     * Populates tortilla array with employees.  Each index represents an hour of work from open to close.  No one is deployed on tortilla until 11:00AM
     * so i starts at 4, leaving indices 0-4 empty.  No one is deployed on tortilla after 10, so we iterate until i < lobby.length - 1 
     */
    private void deployTortilla() {
        for(int i = 4; i < tortilla.length - 1; i++) {
            for(Employee e : sortedEmployeeList) {
                if(Collections.frequency(hoursWorked, e) < 9) {
                    if(!working[i].contains(e)) {
                        if(e.getKnownDeployments().contains(Deployment.tortilla)) {
                            tortilla[i] = e;

                            working[i].add(e); 
                        }
                    }
                }
            }
        }
    }
}
