package main;

import View.*;
import DAO.*;
import Controller.*;
import Model.*;

public class Main {

    public static void main(String[] args) {
        // Start the application
        launchApplication();
    }

    /**
     * Launch the application starting with the login view.
     */
    private static void launchApplication() {
        LoginView loginView = new LoginView();

        // Add a listener for successful login
        loginView.setLoginSuccessListener(() -> {
            // Close the login view
            loginView.dispose();

            // Launch the HolidayView after successful login
            launchHolidayView();
        });

        // Show the login view
        loginView.setVisible(true);
    }

    /**
     * Set up and launch the main HolidayView interface.
     * This method is called after successful login.
     */
    private static void launchHolidayView() {
        // Initialize DAO implementations
        HolidayDAOImplement holidayDAO = new HolidayDAOImplement();
        EmployeDAOImplement employeeDAO = new EmployeDAOImplement();

        // Initialize Models
        HolidayModel holidayModel = new HolidayModel(holidayDAO, employeeDAO);
        EmployeModel employeeModel = new EmployeModel(employeeDAO);

        // Initialize Views
        HolidayView holidayView = new HolidayView();

        // Set up Controllers
        new HolidayController(holidayModel, holidayView);
        new EmployeController(employeeModel, holidayView.getEmployeView());

        // Show the HolidayView window
        holidayView.setVisible(true);
    }
}
