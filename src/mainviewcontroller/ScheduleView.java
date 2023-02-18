package mainviewcontroller;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 *
 */
public class ScheduleView extends JFrame {
	// Times
	protected String[] time;

	// JList
	protected JList availabilityList;

	// Buttons
	protected JButton viewButton;
	protected JButton editListButton;
	protected JButton submitButton;
	protected JButton cancelRemoveButton;

	// Textfields
	protected JTextField nameField;

	// Radio Buttons
	protected JRadioButton crew;
	protected JRadioButton km;
	protected JRadioButton sm;
	protected JRadioButton app;
	protected JRadioButton gm;

	// Check Boxes
	protected JCheckBox grill;
	protected JCheckBox grill2;
	protected JCheckBox dish;
	protected JCheckBox lobby;
	protected JCheckBox tortilla;
	protected JCheckBox salsa;
	protected JCheckBox cash;
	protected JCheckBox prep;
	protected JCheckBox minor;

	// ButtonGroup for Radio Buttons
	ButtonGroup rank;

	// Panels
	protected JPanel thePanel;
	protected JPanel namePanel;
	protected JPanel rankPanel;
	protected JPanel deploymentPanel;
	protected JPanel availabilityPanel;
	protected JPanel minorPanel;
	protected JPanel editListPanel;
	protected JPanel submitPanel;
	protected JPanel weekPanelWrapper;
	protected JPanel weekPanel;
	protected JPanel employeePanelPanel;
	protected JPanel wrappingPanel;


	// JLabels
	protected JLabel nameLabel;

	// ScrollPane
	JScrollPane employeeScroll;
	JScrollPane availabilityScroll;

	/**
	 * Non-Arg constructor initializes components and calls init() method
	 */
	public ScheduleView() {
		viewButton = new JButton("View Employee List");
		editListButton = new JButton("Add New Employee");
		// These two buttons cause two different events so have different text assigned
		// depending where they are used.
		submitButton = new JButton();
		cancelRemoveButton = new JButton();

		nameField = new JTextField(20);

		crew = new JRadioButton("Crew");
		km = new JRadioButton("Kitchen Manager");
		sm = new JRadioButton("Service Manager");
		app = new JRadioButton("Apprentice");
		gm = new JRadioButton("General Manager");

		// There are 17 hours available to work in a day, and 7 days in a week, but
		// there is also 7 labels for "Monday", "Tuesday", etc., so 17+1 * 7 is how many
		// indexes we need
		time = new String[18 * 7];
		String amPm = "AM";
		String[] day = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		int j = 0;
		// int[] time is used by JList to represent the availability of the employee.
		for (int i = 0; i < time.length; i++) {
			// Every 18th index in the JList, will be a day of the week, instead of a time
			// Ex : Monday | 7:00AM | 8:00AM... | 11:00PM <- index = 17
			// Ex : Tuesday| 7:00AM | 8:00AM... | 11:00PM <- index = 35
			if (i % 18 == 0) {
				time[i] = day[j];
				j++;
			} else {
				// Just print time AM/PM depending on if we're before noon etc. and converts
				// from 1300 to 100 etc.
				int twelveHrClock = (i % 18) + 6; // adding 6 because time starts at 7:00AM, otherwise would start at
													// 1:00AM
				if (twelveHrClock >= 12) { // beyond noon
					if (twelveHrClock != 12) {
						twelveHrClock = twelveHrClock - 12;
					}
					amPm = "PM";
				} else { // before noon
					amPm = "AM";
				}

				time[i] = twelveHrClock + ":00" + amPm;
			}
		}

		// JList
		availabilityList = new JList(time);

		// Deployment checkboxes
		grill = new JCheckBox("Grill");
		grill2 = new JCheckBox("Grill 2");
		dish = new JCheckBox("Dish");
		lobby = new JCheckBox("Lobby");
		tortilla = new JCheckBox("Tortilla");
		salsa = new JCheckBox("Salsa");
		cash = new JCheckBox("Cash");
		prep = new JCheckBox("Prep");

		rank = new ButtonGroup();

		// Rank radio buttons
		rank.add(crew);
		rank.add(km);
		rank.add(sm);
		rank.add(app);
		rank.add(gm);

		minor = new JCheckBox("Minor");

		// Panels
		thePanel = new JPanel();
		namePanel = new JPanel();
		rankPanel = new JPanel();
		deploymentPanel = new JPanel();
		availabilityPanel = new JPanel();
		minorPanel = new JPanel();
		editListPanel = new JPanel();
		submitPanel = new JPanel();
		weekPanelWrapper = new JPanel();
		weekPanel = new JPanel();
		employeePanelPanel = new JPanel();
		wrappingPanel = new JPanel();


		employeeScroll = new JScrollPane();
		availabilityScroll = new JScrollPane();

		nameLabel = new JLabel("Name");
		init();
	}

	/**
	 * Initializes the frame
	 */
	private void init() {
		setSize(900, 600);

		thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));

		add(thePanel);
		setTitle("Schedule");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	// Set up action listeners for buttons

	/**
	 * Adds listener to addSchedule Button
	 */
	public void addViewButtonListener(ActionListener listener) {
		viewButton.addActionListener(listener);
	}

	/**
	 * Adds listener to addEdit Button
	 */
	public void addEditListButtonListener(ActionListener listener) {
		editListButton.addActionListener(listener);
	}

	/**
	 * Adds ActionListener to editEmployeeButton, but also removes any existing
	 * ActionListeners because only one reference for this button exists, but the
	 * button will be instantiated many times (every time we add or edit and
	 * employee).
	 *
	 */
	public void addSubmitButtonListener(ActionListener listener) {
		if (submitButton.getActionListeners().length != 0) {
			submitButton.removeActionListener(submitButton.getActionListeners()[0]);
		}
		submitButton.addActionListener(listener);
	}

	/**
	 * Adds ActionListener to editEmployeeButton, but also removes any existing
	 * ActionListeners because only one reference for this button exists, but the
	 * button will be instantiated many times (every time we add or edit and
	 * employee).
	 */
	public void addCancelRemoveButtonListener(ActionListener listener) {
		if (cancelRemoveButton.getActionListeners().length != 0) {
			cancelRemoveButton.removeActionListener(cancelRemoveButton.getActionListeners()[0]);
		}
		cancelRemoveButton.addActionListener(listener);
	}
}
