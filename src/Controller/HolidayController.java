package Controller;

import View.HolidayView;
import Model.Employe;
import Model.Holiday;
import Model.HolidayModel;
import Model.HolidayType;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class HolidayController {
    private HolidayModel model;
    private HolidayView view;

    public HolidayController(HolidayModel model, HolidayView view) {
        this.model = model;
        this.view = view;
        displayHolidays();
        this.view.getAddButton().addActionListener(e -> addHoliday());
        this.view.getDeleteButton().addActionListener(e -> deleteHoliday());
        this.view.getUpdateButton().addActionListener(e -> updateHoliday());
        this.view.getListButton().addActionListener(e -> displayHolidays());
        loadEmployeeNames();
    }

    private void addHoliday() {
        try {
            String employeeName = (String) view.getEmployeeNameComboBox().getSelectedItem();
            int employeeId = model.getEmployeeIdByName(employeeName);

            String dateDebut = view.getStartField().getText();
            String dateFin = view.getEndField().getText();
            HolidayType type = (HolidayType) view.getTypeBox().getSelectedItem();

            boolean success = model.add(employeeId, dateDebut, dateFin, type);
            if (success) {
                JOptionPane.showMessageDialog(view, "Congé ajouté avec succès!");
                clearInputFields();
                displayHolidays();
                displayHolidays();
            } else {
                JOptionPane.showMessageDialog(view, "Erreur : Solde insuffisant ou dates invalides.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Veuillez vérifier les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteHoliday() {
        int selectedRow = view.getHolidayTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un congé à supprimer!");
            return;
        }
        int id = (int) view.getHolidayTable().getValueAt(selectedRow, 0);
        model.delete(id);

       
        JOptionPane.showMessageDialog(view, "Congé supprimé avec succès!");
        displayHolidays();
    }

    private void updateHoliday() {
        int selectedRow = view.getHolidayTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = (int) view.getHolidayTable().getValueAt(selectedRow, 0);
                String start = view.getStartField().getText();
                String end = view.getEndField().getText();
                HolidayType type = (HolidayType) view.getTypeBox().getSelectedItem();

                int employeeId = model.getEmployeeIdByName((String) view.getEmployeeNameComboBox().getSelectedItem());

                boolean success = model.update(id, employeeId, type, start, end);
                if (success) {
                	 displayHolidays();
                    JOptionPane.showMessageDialog(view, "La mise à jour a réussi.");
                } else {
                    JOptionPane.showMessageDialog(view, "Erreur lors de la mise à jour.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Veuillez vérifier les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearInputFields() {
        view.getStartField().setText("");
        view.getEndField().setText("");
        view.getTypeBox().setSelectedIndex(0);
    }

    private void displayHolidays() {
        List<Holiday> holidays = model.getAllHolidays();
        DefaultTableModel tableModel = (DefaultTableModel) view.getHolidayTable().getModel();
        tableModel.setRowCount(0); 

        for (Holiday holiday : holidays) {
            String employeeName =getEmployeeNameById(holiday.getIdEmploye());
            tableModel.addRow(new Object[]{
                holiday.getId(),
                employeeName,
                holiday.getDateDebut(),
                holiday.getDateFin(),
                holiday.getType().toString()
                
            });
        }
        loadEmployeeNames();
    }

    private String getEmployeeNameById(int employeeId) {
        List<Employe> employees = getEmployees();
        for (Employe employee : employees) {
            if (employee.getId() == employeeId) {
                return employee.getNom();
            }
           
        }
        return "";
    }

    private List<Employe> getEmployees() {
        return model.getEmployees();
    }

    private void loadEmployeeNames() {
        List<String> employeeNames = model.getEmployeeNames();
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(employeeNames.toArray(new String[0]));
        view.getEmployeeNameComboBox().setModel(comboBoxModel);
    }
}
