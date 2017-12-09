package mainviewcontroller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import chipotlelogic.Deployment;
import chipotlelogic.Rank;
import employee.Employee;
import employee.EmployeeList;

/**
 * Controller for MVC design pattern
 *
 */
public class ScheduleController {
	private ScheduleView scheduleView;
	private EmployeeList employeeList;
	private JPanel scheduleViewPanel;

	/**
	 * Constructor takes scheduleView (view) and an EmployeeList (model) to link a
	 * model, view, and controller for MVC design pattern. Initializes the view and
	 * model, and adds some action listeners to buttons that only have one action
	 * listener
	 * 
	 * @param scheduleView
	 *            the view (holds all of the GUI components)
	 * @param scheduleModel
	 *            the model (A list of employees)
	 */
	public ScheduleController(ScheduleView scheduleView, EmployeeList employeeList) {
		this.scheduleView = scheduleView;
		this.employeeList = employeeList;
		scheduleViewPanel = scheduleView.thePanel;

		this.scheduleView.addEditListButtonListener(new EditListButtonListener());
		this.scheduleView.addEmployeeButtonListener(new EmployeeButtonListener());
		this.scheduleView.addScheduleButtonListener(new ScheduleButtonListener());
	}

	/**
	 * Clears window
	 */
	private void init() {

		scheduleViewPanel.removeAll();
		scheduleViewPanel.repaint();

	}

	/**
	 * Updates the frame with the necessary JComponents to display and edit the list
	 * of employees
	 */
	private void employeeWindow() {

		init();
		// Resets the panel holding the employee panels
		scheduleView.employeePanelPanel.removeAll();
		scheduleView.employeePanelPanel.repaint();

		/**
		 * The employee window will consist of panels that hold information about each
		 * employee.
		 */
		class EmployeePanel extends JPanel {
			//
			Employee employee;

			/**
			 * Constructor takes has a parameter Employee. The employee passed as an
			 * argument will stay with the panel, allowing us to edit the employee
			 * 
			 * @param e
			 *            The panel's employee
			 */
			public EmployeePanel(Employee e) {
				employee = e;

				// Panel has an edit button that takes you to a new window to edit the
				// information on the employee, e
				JButton edit = new JButton("Edit");
				EditButtonListener editListener = new EditButtonListener();
				edit.addActionListener(editListener);

				// Panels just used to get components to behave intended way
				setLayout(new BorderLayout());
				JPanel nameWrapper = new JPanel();
				JPanel editWrapper = new JPanel();

				// nameWrapper panel displays employee's name
				nameWrapper.setLayout(new BorderLayout());
				if (employee.getLastName().equals("")) {
					nameWrapper.add(new JLabel(employee.getFullName()), BorderLayout.CENTER);
				} else {
					nameWrapper.add(new JLabel(employee.getLastName() + ", " + employee.getFirstName()),
							BorderLayout.CENTER);
				}
				nameWrapper.setBorder(new EmptyBorder(0, 10, 0, 0));
				editWrapper.add(edit);

				// One panel holding name label and another panel holding edit button are added
				// to the EmployeePanel
				add(nameWrapper, BorderLayout.WEST);
				add(editWrapper, BorderLayout.EAST);

			}

			/**
			 * When you click the "Edit" button, we have to change the submit Button's
			 * listener. Then we change the window to the editEmployeeWindow()
			 */
			class EditButtonListener implements ActionListener {
				@Override
				public void actionPerformed(ActionEvent e) {
					// adds button listeners to submit and cancel button then takes us to a window
					// that allows us to edit the employee
					scheduleView.submitButton.setText("Okay");
					scheduleView.addSubmitButtonListener(new EditEmployeeSubmitButtonListener(employee));
					scheduleView.addCancelRemoveButtonListener(new EditEmployeeCancelRemoveButtonListener(employee));
					editEmployeeWindow(employee);
				}
			}
		}

		// sorts the employee list as GM > Apprentice > Service Manager > Kitchen
		// Manager > Crew and in alphabetical order
		employeeList.sort();

		// Iterates through employeeList and adds a panel for each employee in the list
		Employee prev = null;
		for (Employee e : employeeList.getList()) {

			// Puts a divider between employees of different ranks.
			// Ex: GENERAL MANAGER
			// ....Employee a
			// ....APPRENTICE
			// ....Employee b
			// ....Employee c
			// ....SERVICE MANAGER
			// etc.
			if (prev == null) { // Checks to see if previous employee has same rank, if not, it will add a
								// divider panel, but first employee does not have a previous employee to
								// compare rank to
				scheduleView.employeePanelPanel.add(new JPanel().add(new JLabel(e.getRank().toString())));
			} else if (prev.getRank() != e.getRank()) {
				scheduleView.employeePanelPanel.add(new JPanel().add(new JLabel(e.getRank().toString())));
			}
			scheduleView.employeePanelPanel.add(new EmployeePanel(e));
			prev = e;
		}

		scheduleView.employeePanelPanel.repaint();

		// Add all the components
		scheduleView.editListPanel.add(scheduleView.editListButton);

		// Reset wrappingpanel
		scheduleView.wrappingPanel.removeAll();
		scheduleView.wrappingPanel.repaint();

		scheduleView.wrappingPanel.setLayout(new BorderLayout());
		scheduleView.wrappingPanel.add(scheduleView.editListPanel, BorderLayout.SOUTH);

		scheduleView.employeePanelPanel.setBackground(Color.lightGray);
		scheduleView.employeePanelPanel.setLayout(new GridLayout(0, 1));

		scheduleView.employeeScroll.setViewportView(scheduleView.employeePanelPanel);
		scheduleView.employeeScroll.setBorder(null);

		add(scheduleView.employeeScroll);

		add(scheduleView.wrappingPanel);

		validate();

	}

	/**
	 * Updates the frame with the necessary JComponents to create a new Employee
	 */
	private void newEmployeeWindow() {

		InitNewAndEditEmployeeWindow();
		initNewEmployeeWindow();

	}

	/**
	 * Updates the frame with the necessary JComponents to edit an employee, e
	 * 
	 * @param e
	 *            the employee we are editing
	 */
	private void editEmployeeWindow(Employee e) {
		InitNewAndEditEmployeeWindow();
		initEditEmployeeComponents(e);

	}

	/**
	 * Updates the frame with the necessary JComponents specific to creating a new
	 * employee
	 */
	private void initNewEmployeeWindow() {
		// Because it's a new Employee, we set the nameField text to be empty
		scheduleView.nameField.setText("");

		scheduleView.minor.setSelected(false);

		scheduleView.crew.setSelected(true);

		// We set everything in the availabilityList to be selected (except for the
		// indexes containing the day of the week (Monday, Tuesday, etc.) - which will
		// be every 18th index, so if index % 18 = 0, we will not set it as selected
		ListSelectionModel lsm = scheduleView.availabilityList.getSelectionModel();
		for (int i = 0; i < scheduleView.time.length; i++) {
			if (i % 18 != 0) {
				lsm.addSelectionInterval(i, i + 16);
				i = i + 16;
			}
		}

		// Set everything as unselected of the fields are set as unselected.
		scheduleView.grill.setSelected(false);
		scheduleView.grill2.setSelected(false);
		scheduleView.dish.setSelected(false);
		scheduleView.lobby.setSelected(false);
		scheduleView.tortilla.setSelected(false);
		scheduleView.salsa.setSelected(false);
		scheduleView.cash.setSelected(false);
		scheduleView.prep.setSelected(false);

		// Button Listeners are added here because the submit button and cancelRemove
		// button have different action events depending on the window they are used in
		scheduleView.addSubmitButtonListener(new NewEmployeeSubmitButtonListener());
		scheduleView.addCancelRemoveButtonListener(new NewEmployeeCancelRemoveButtonListener());
		scheduleView.cancelRemoveButton.setText("Cancel");

		validate();

	}

	/**
	 * Updates the frame with the necessary JComponents specific to editing an
	 * employee
	 * 
	 * @param e
	 *            the employee we are editing
	 */
	private void initEditEmployeeComponents(Employee e) {

		// Get the name of the employee we are editing and set the nameField text as
		// their name
		scheduleView.nameField.setText(e.getFullName());

		// Get whether or not employee is a minor and set minor checkbox to correlate
		if (e.isMinor()) {
			scheduleView.minor.setSelected(true);
		} else {
			scheduleView.minor.setSelected(false);
		}

		// Set corresponding rank JRadioButton as selected
		String rank = e.getRank().toString();

		if (rank == "Crew") {
			scheduleView.crew.setSelected(true);
		} else if (rank == "Kitchen Manager") {
			scheduleView.km.setSelected(true);
		} else if (rank == "Service Manager") {
			scheduleView.sm.setSelected(true);
		} else if (rank == "Apprentice") {
			scheduleView.app.setSelected(true);
		} else if (rank == "General Manager") {
			scheduleView.gm.setSelected(true);
		}

		// Selecting the appropriate indexes for availabilityList is delegated to a
		// method
		setUpAvailabilityList(e);

		// Get the deployments the employee knows and set corresponding checkboxes as
		// selected (or unselected, because they may be selected from a previous
		// employee)
		ArrayList<Deployment> knownDeployments = e.getKnownDeployments();

		if (knownDeployments.contains(Deployment.grill)) {
			scheduleView.grill.setSelected(true);
		} else {
			scheduleView.grill.setSelected(false);
		}

		if (knownDeployments.contains(Deployment.grill2)) {
			scheduleView.grill2.setSelected(true);
		} else {
			scheduleView.grill2.setSelected(false);
		}

		if (knownDeployments.contains(Deployment.dish)) {
			scheduleView.dish.setSelected(true);
		} else {
			scheduleView.dish.setSelected(false);
		}

		if (knownDeployments.contains(Deployment.lobby)) {
			scheduleView.lobby.setSelected(true);
		} else {
			scheduleView.lobby.setSelected(false);
		}

		if (knownDeployments.contains(Deployment.tortilla)) {
			scheduleView.tortilla.setSelected(true);
		} else {
			scheduleView.tortilla.setSelected(false);
		}

		if (knownDeployments.contains(Deployment.salsa)) {
			scheduleView.salsa.setSelected(true);
		} else {
			scheduleView.salsa.setSelected(false);
		}

		if (knownDeployments.contains(Deployment.cash)) {
			scheduleView.cash.setSelected(true);
		} else {
			scheduleView.cash.setSelected(false);
		}

		if (knownDeployments.contains(Deployment.prep)) {
			scheduleView.prep.setSelected(true);
		} else {
			scheduleView.prep.setSelected(false);
		}

		scheduleView.cancelRemoveButton.setText("Remove");

		validate();
	}

	/**
	 * Updates the frame with the necessary JComponents required when both editing
	 * and creating an employee
	 */
	private void InitNewAndEditEmployeeWindow() {
		init();

		// This whole method basically just puts everything into place, but does not
		// really set any of the selections
		scheduleViewPanel.setLayout(new BoxLayout(scheduleViewPanel, BoxLayout.Y_AXIS));
		scheduleView.rankPanel.setLayout(new GridLayout(3, 0));
		scheduleView.deploymentPanel.setLayout(new GridLayout(0, 4));

		scheduleView.namePanel.add(scheduleView.nameLabel);
		scheduleView.namePanel.add(scheduleView.nameField);
		scheduleView.namePanel.add(scheduleView.minor);

		scheduleView.rankPanel.setBorder(new TitledBorder("Rank"));

		scheduleView.rankPanel.add(scheduleView.crew);
		scheduleView.rankPanel.add(scheduleView.km);
		scheduleView.rankPanel.add(scheduleView.sm);
		scheduleView.rankPanel.add(scheduleView.app);
		scheduleView.rankPanel.add(scheduleView.gm);

		/**
		 * Modified ListSelectionListener so that whenever you click on a day of the
		 * week, it will select, or de-select every time for that day
		 */
		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				JList list = (JList) listSelectionEvent.getSource(); // Create a list so we have a reference for the
																		// source of the event

				int[] selections = list.getSelectedIndices(); // Create an array of all the selected indexes

				for (int i = 0; i < selections.length; i++) { // Go through every selected index, and
					if (selections[i] % 18 == 0) { // if an index corresponding to a day of the week is selected
						list.removeSelectionInterval(selections[i], selections[i]); // remove that index

						int[] wholeRow = new int[17]; // create an array that contains the next 17 indexes after the day
														// of the week index that was selected
						int[] selectedIndicesRow = new int[17]; // create an array that contains the next 17 selected
																// indexes (not necessarily the next 17 indexes)

						// We will then compare these to array, and if they equal, that means that the
						// whole row corresponding to that day of the week is selected, we will then
						// de-select them.

						// Here we are just adding values to the wholeRow array.
						for (int j = 0; j < wholeRow.length; j++) {
							wholeRow[j] = selections[i] + 1 + j;
						}

						for (int j = 0; j < selectedIndicesRow.length; j++) {
							try {
								selectedIndicesRow[j] = selections[i + 1 + j]; // Here we are creating the
																				// selectedIndicesRow

							} catch (ArrayIndexOutOfBoundsException e) {
								// if we catch an ArrayIndexOutOfBoundsException, then we know at-least one of
								// the indexes was not selected, so we just set every index in that row to
								// selected. This will not always happen, only if we are at the last row and at
								// selected.
								list.addSelectionInterval(wholeRow[0], wholeRow[wholeRow.length - 1]);
							}
						}

						// If contains() returns true, that means the whole row is selected, so we
						// de-select the row
						if (contains(wholeRow, selectedIndicesRow)) {
							list.removeSelectionInterval(wholeRow[0], wholeRow[wholeRow.length - 1]);
						} else { // If contains() returns false, that means that at least one index is not
									// selected in the row, so we select the whole row
							list.addSelectionInterval(wholeRow[0], wholeRow[wholeRow.length - 1]);
						}

						break;
					}
				}

			}

			/**
			 * Method helps to check if an entire row is selected or not.
			 * 
			 * @param a
			 *            The whole row
			 * @param b
			 *            Only selected indices
			 * @return Whether or not b contains a
			 */
			private boolean contains(int[] a, int[] b) {
				// If b contains every value of a, then we know that every index is selected. a
				// corresponds to every index in the row, while b corresponds to only selected
				// indices.
				for (int i = 0; i < a.length; i++) {
					if (a[i] != b[i]) {
						return false;
					}
				}
				return true;
			}
		};

		/**
		 * Allows you to drag and click to select/de-select indexes in the
		 * availabilityList One problem I have is, I'd like to have it so that, when you
		 * are dragging you mouse, it will only select or de-select indexes. For
		 * example, if the first index you click is selected, then it will be
		 * de-selected, and as you are holding the mouse down and dragging, you can now
		 * only de-select indexes. As it is now, if index i is selected, and index j is
		 * de-selected, dragging through them will just switch whether they are selected
		 * or not. I'll have to dive into the DefaultListSelectionModel class to figure
		 * it out, but for now, it works.
		 */
		scheduleView.availabilityList.setSelectionModel(new DefaultListSelectionModel() {
			private int i0 = -1;
			private int i1 = -1;

			// Changes the selection to be between index0 and index1 inclusive
			@Override
			public void setSelectionInterval(int index0, int index1) {

				if (i0 == index0 && i1 == index1) { // Means we have not changed index/dragged mouse over into new index
					if (getValueIsAdjusting()) {
						setValueIsAdjusting(false); // prevents the selection from changing as I drag my mouse
						setSelection(index0, index1);
					}
				} else { // If we drag mouse into new index, we update i0 and i1 to the index we dragged
							// mouse into
					i0 = index0;
					i1 = index1;
					setValueIsAdjusting(false); // prevents the selection from changing as I drag my mouse
					setSelection(index0, index1);
				}
			}

			// If the index clicked is selected, remove it, and vice-versa
			private void setSelection(int index0, int index1) {
				if (super.isSelectedIndex(index0)) {
					super.removeSelectionInterval(index0, index1);
				} else {
					super.addSelectionInterval(index0, index1);
				}
			}
		});

		// We defined listSelectionListener above
		scheduleView.availabilityList.addListSelectionListener(listSelectionListener);

		// Format availibilityList
		scheduleView.availabilityList.setVisibleRowCount(7);
		scheduleView.availabilityList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		scheduleView.availabilityPanel.add(scheduleView.availabilityList);

		// Create a scrollbar for availabilityList
		scheduleView.availabilityScroll.setBorder(BorderFactory.createTitledBorder("Availability"));
		scheduleView.availabilityScroll.setViewportView(scheduleView.availabilityPanel);
		scheduleView.availabilityScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scheduleView.availabilityScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// Set-up deploymentPanel
		scheduleView.deploymentPanel.setBorder(new TitledBorder("Known Deployments"));

		scheduleView.deploymentPanel.add(scheduleView.grill);
		scheduleView.deploymentPanel.add(scheduleView.tortilla);
		scheduleView.deploymentPanel.add(scheduleView.lobby);
		scheduleView.deploymentPanel.add(scheduleView.cash);
		scheduleView.deploymentPanel.add(scheduleView.grill2);
		scheduleView.deploymentPanel.add(scheduleView.salsa);
		scheduleView.deploymentPanel.add(scheduleView.dish);
		scheduleView.deploymentPanel.add(scheduleView.prep);

		// Set up submitPanel
		scheduleView.submitPanel.add(scheduleView.submitButton);
		scheduleView.submitPanel.add(scheduleView.cancelRemoveButton);

		scheduleView.wrappingPanel.removeAll();
		scheduleView.wrappingPanel.repaint();

		scheduleView.wrappingPanel.setLayout(new BorderLayout());
		scheduleView.wrappingPanel.add(scheduleView.submitPanel, BorderLayout.SOUTH);

		add(scheduleView.namePanel);
		add(scheduleView.rankPanel);
		add(scheduleView.availabilityScroll);
		add(scheduleView.deploymentPanel);
		add(scheduleView.wrappingPanel);

		scheduleView.nameField.requestFocus();
		scheduleView.getRootPane().setDefaultButton(scheduleView.submitButton);
	}

	/**
	 * Somewhat of an override so adding components to thePanel from scheduleView
	 * looks cleaner
	 */
	private void add(Component comp) {
		scheduleViewPanel.add(comp);
		scheduleViewPanel.repaint();
	}

	/**
	 * Somewhat of an override so validating looks cleaner
	 */
	private void validate() {
		scheduleViewPanel.validate();
	}

	/**
	 * Returns the rank selected by JRadio Buttons
	 * 
	 * @return Rank selected by JRadio Buttons
	 */
	private Rank findRankSelections() {
		if (scheduleView.crew.isSelected()) {
			return Rank.crew;
		} else if (scheduleView.km.isSelected()) {
			return Rank.km;
		} else if (scheduleView.sm.isSelected()) {
			return Rank.sm;
		} else if (scheduleView.app.isSelected()) {
			return Rank.apprentice;
		} else {
			return Rank.gm;
		}
	}

	/**
	 * Helps to find what deployments are selected so we can add them to
	 * knownDeployments of the Employee
	 * 
	 * @return an ArrayList<Deployment> of the deployments corresponding to the
	 *         selected checkboxes
	 */
	private ArrayList<Deployment> findDeploymentSelections() {
		ArrayList<Deployment> r = new ArrayList<Deployment>();

		if (scheduleView.grill.isSelected()) {
			r.add(Deployment.grill);
		}

		if (scheduleView.grill2.isSelected()) {
			r.add(Deployment.grill2);

		}

		if (scheduleView.tortilla.isSelected()) {
			r.add(Deployment.tortilla);

		}

		if (scheduleView.salsa.isSelected()) {
			r.add(Deployment.salsa);

		}

		if (scheduleView.dish.isSelected()) {
			r.add(Deployment.dish);

		}

		if (scheduleView.lobby.isSelected()) {
			r.add(Deployment.lobby);

		}

		if (scheduleView.cash.isSelected()) {
			r.add(Deployment.cash);

		}

		if (scheduleView.prep.isSelected()) {
			r.add(Deployment.prep);

		}

		if (scheduleView.grill.isSelected()) {
			r.add(Deployment.grill);

		}

		if (scheduleView.grill.isSelected()) {
			r.add(Deployment.grill);

		}

		return r;
	}

	/**
	 * Looks at all of the checkboxes from ScheduleView and sees if they are
	 * selected or not, then adds all the selected checkboxes corresponding
	 * deployment as a known deployment for the employee
	 * 
	 * @param e
	 *            the employee we are setting the known deployments for
	 */
	private void addDeployments(Employee e) {
		ArrayList<Deployment> toAdd = new ArrayList<Deployment>();

		if (scheduleView.grill.isSelected()) {
			toAdd.add(Deployment.grill);
		}

		if (scheduleView.grill2.isSelected()) {
			toAdd.add(Deployment.grill2);
		}

		if (scheduleView.dish.isSelected()) {
			toAdd.add(Deployment.dish);

		}

		if (scheduleView.lobby.isSelected()) {
			toAdd.add(Deployment.lobby);

		}

		if (scheduleView.tortilla.isSelected()) {
			toAdd.add(Deployment.tortilla);

		}

		if (scheduleView.salsa.isSelected()) {
			toAdd.add(Deployment.salsa);

		}

		if (scheduleView.cash.isSelected()) {
			toAdd.add(Deployment.cash);

		}

		if (scheduleView.prep.isSelected()) {
			toAdd.add(Deployment.prep);

		}

		e.addKnownDeployments(toAdd);
	}

	/**
	 * Sets up the availabilityList in ScheduleView by checking the availability of
	 * Employee e, then sets the corresponding indexes as selected so the
	 * availability displayed reflects the employee's actual availability
	 * 
	 * @param e
	 *            the employee we are editing
	 */
	private void setUpAvailabilityList(Employee e) {
		int[] employeeAvailability = e.getAvailability();
		for (int i = 0; i < employeeAvailability.length; i++) {
			scheduleView.availabilityList.getSelectionModel().addSelectionInterval(employeeAvailability[i],
					employeeAvailability[i]);
		}
	}

	/**
	 * ActionListener for employeeButton
	 */
	private class EmployeeButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			employeeWindow();
		}

	}

	/**
	 * ActionListener for scheduleButton (Not ready to be implemented)
	 */
	private class ScheduleButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// scheduleWindow();
		}
	}

	/**
	 * ActionListener for newEmployeeButton
	 */
	private class NewEmployeeSubmitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Rank r = findRankSelections();
			String n = scheduleView.nameField.getText().trim();

			try {
				// Checks to see if name contains alpha-numeric character
				if (n.replaceAll(" ", "").equals("")) {
					throw new NoNameException("Please enter a name.");
				}

				Employee newEmployee = new Employee(n, r);

				newEmployee.setMinor(scheduleView.minor.isSelected());
				newEmployee.setAvailability(scheduleView.availabilityList.getSelectedIndices());
				addDeployments(newEmployee);

				employeeList.addEmployee(newEmployee);
				employeeWindow();
				employeeList.writeToFile("data.dat");
			} catch (NoNameException ex) {
				JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
			}

		}
	}

	/**
	 * ActionListener for editEmployeeButton
	 */
	private class EditEmployeeSubmitButtonListener implements ActionListener {
		private Employee employeeToEdit;

		/**
		 * 
		 * @param e
		 */
		public EditEmployeeSubmitButtonListener(Employee e) {
			employeeToEdit = e;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String n = scheduleView.nameField.getText().trim();

			try {
				// Checks to see if name contains alpha-numeric character
				if (n.replaceAll(" ", "").equals("")) {
					throw new NoNameException("Please enter a name.");
				}

				// Update all the info for the employee corresponding to check boxes,
				// radio buttons etc.
				employeeToEdit.setRank(findRankSelections());
				employeeToEdit.setAvailability(scheduleView.availabilityList.getSelectedIndices());
				employeeToEdit.setName(n);
				employeeToEdit.setMinor(scheduleView.minor.isSelected());

				// If someone unselects a deployment, we add it to this ArrayList by comparing
				// knownDeployments to selected check boxes
				ArrayList<Deployment> toRemove = new ArrayList<Deployment>();

				for (Deployment d : employeeToEdit.getKnownDeployments()) {
					if (!findDeploymentSelections().contains(d)) {
						toRemove.add(d);
					}
				}

				employeeToEdit.removeKnownDeployments(toRemove);

				addDeployments(employeeToEdit);
				employeeWindow();
				
				//Update data.dat
				employeeList.writeToFile("data.dat");
			} catch (NoNameException ex) {
				JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
			}
		}
	}

	/**
	 * ActionListener for newEmployeeCancelRemoveButton
	 */
	private class NewEmployeeCancelRemoveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			employeeWindow();
		}
	}

	/**
	 * ActionListener for editEmployeeCancelRemoveButton
	 */
	private class EditEmployeeCancelRemoveButtonListener implements ActionListener {
		private Employee employeeToRemove;

		public EditEmployeeCancelRemoveButtonListener(Employee e) {
			employeeToRemove = e;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// Adds a dialog box to confirm you want to remove the employee. Only removes
			// the employee if "Yes" is selected.
			int remove = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to remove " + employeeToRemove + "?", "Remove Employee",
					JOptionPane.YES_NO_OPTION);

			if (remove == JOptionPane.YES_OPTION) {
				employeeList.removeEmployee(employeeToRemove);
				employeeWindow();
				// Update data.dat to have employee removed.
				employeeList.writeToFile("data.dat");
			}
		}
	}

	/**
	 * ActionListener for editListButton
	 */
	private class EditListButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			scheduleView.submitButton.setText("Submit");
			newEmployeeWindow();
		}
	}

	/**
	 * Exception thrown to prevent user from creating an employee with no name.
	 */
	private class NoNameException extends Exception {
		NoNameException(String msg) {
			super(msg);
		}
	}

}
