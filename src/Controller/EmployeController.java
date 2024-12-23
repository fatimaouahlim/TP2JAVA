package Controller;
import View.*;

import Model.*;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmployeController {
    private EmployeModel model; 
    private EmployeView view; // Vue pour afficher les informations et interagir avec l'utilisateur
    
    // Constructeur qui initialise la vue et le modèle, et ajoute des listeners pour les actions des boutons
    public EmployeController(EmployeModel model, EmployeView view) {
        this.view = view;
        this.model = model;
        displayEmployes() ;
       // Ajout des ActionListener pour les boutons de la vue
        this.view.addButton.addActionListener(e -> addEmploye());
        this.view.deleteButton.addActionListener(e -> deleteEmploye());
        this.view.displayButton.addActionListener(e -> displayEmployes());
        this.view.updateButton.addActionListener(e -> updateEmploye());
    }
    
    // Méthode pour ajouter un employé
    private void addEmploye() {
        // Récupération des informations de la vue
        String name = view.nomField.getText();
        String prenom = view.prenomField.getText();
        float salaire = Float.parseFloat(view.salaireField.getText());
        String telephone = view.telephoneField.getText();
        Role role = (Role) view.roleDropdown.getSelectedItem();
        Poste poste = (Poste) view.posteDropdown.getSelectedItem();
        String email;

        try {
            // Tentative de récupération de l'email
            email = view.emailField.getText();
        } catch (Exception e) {
            // Affichage d'un message d'erreur si l'email est invalide
            JOptionPane.showMessageDialog(view, "Email invalide !");
            return;
        }

        // Appel à la méthode du modèle pour ajouter l'employé
        boolean ajoutReussi = model.add(name, prenom, email, telephone, salaire, role, poste);
        if (ajoutReussi) {
            // Affichage d'un message de succès si l'ajout a réussi
            JOptionPane.showMessageDialog(view, "L'employé est ajouté avec succès !");
            displayEmployes() ;
        } else {
            // Affichage d'un message d'erreur si l'ajout a échoué
            JOptionPane.showMessageDialog(view, "L'ajout a échoué !");
        }
    }
    
    // Méthode pour supprimer un employé
    private void deleteEmploye() {
        // Récupération de la ligne sélectionnée dans le tableau
        int selectedRow = view.employeTable.getSelectedRow();
        if (selectedRow == -1) {
            // Affichage d'un message d'erreur si aucune ligne n'est sélectionnée
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un employé à supprimer !");
            return;
        }

     
        int id = (int) view.tableModel.getValueAt(selectedRow, 0);
         if( model.delete(id))   displayEmployes() ;
         else {
        	 JOptionPane.showMessageDialog(view, " L'employe a des conges !");
         } 
        	 

          
        
    }
    
   
    private void updateEmploye() {
        // Récupération de la ligne sélectionnée dans le tableau
        int selectedRow = view.employeTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Récupération des informations de l'employé à partir de la vue
                String nom = view.nomField.getText();
                String prenom = view.prenomField.getText();
                String telephone = view.telephoneField.getText();
                String email = view.emailField.getText();  
                double salaire = Double.parseDouble(view.salaireField.getText());
                Role role = (Role) view.roleDropdown.getSelectedItem();
                Poste poste = (Poste) view.posteDropdown.getSelectedItem();

                // Récupération de l'ID de l'employé à mettre à jour à partir de la ligne sélectionnée
                int id = (int) view.tableModel.getValueAt(selectedRow, 0);
                // Appel à la méthode du modèle pour mettre à jour l'employé
                boolean success = model.update(id, nom, prenom, email, telephone, salaire, role, poste);
                if (success) {
                    // Mise à jour des valeurs dans le tableau de la vue
                    view.employeTable.setValueAt(nom, selectedRow, 1);
                    view.employeTable.setValueAt(prenom, selectedRow, 2);
                    view.employeTable.setValueAt(telephone, selectedRow, 3);
                    view.employeTable.setValueAt(email, selectedRow, 4);
                    view.employeTable.setValueAt(salaire, selectedRow, 5);
                    view.employeTable.setValueAt(role, selectedRow, 6);
                    view.employeTable.setValueAt(poste, selectedRow, 7);
                    // Affichage d'un message de succès si la mise à jour est réussie
                    JOptionPane.showMessageDialog(view, "La mise à jour a réussi.");
                    displayEmployes() ;
                } else {
                    // Affichage d'un message d'erreur si la mise à jour échoue
                    JOptionPane.showMessageDialog(view, "La mise à jour a échoué.");
                }
            } catch (NumberFormatException ex) {
                // Affichage d'un message d'erreur si les entrées sont invalides
                JOptionPane.showMessageDialog(view, "Entrée invalide pour l'ID ou le salaire.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
           
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner une ligne à modifier.");
        }
    }
    
    

    

    private void displayEmployes() {
       
        List<Employe> employes = model.getAllEmployes();
        DefaultTableModel tableModel = (DefaultTableModel) view.employeTable.getModel();
        tableModel.setRowCount(0); 

       
        for (Employe employe : employes) {
            tableModel.addRow(new Object[]{
            		   employe.getId(),
                       employe.getNom(),
                       employe.getPrenom(),
                       employe.getTelephone(),
                       employe.getEmail(),
                       employe.getSalaire(),
                       employe.getRole(),
                       employe.getPoste(),
                       employe.getBalance() // Display balance in table
             
            });
        }
    }
}
