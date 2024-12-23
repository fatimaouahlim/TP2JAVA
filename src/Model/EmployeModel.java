package Model;
import java.util.List;

import DAO.*;

public class EmployeModel {

   
    private EmployeDAOImplement dao;
    
  
    public EmployeModel(EmployeDAOImplement dao) {
        this.dao = dao;
    }
   
    public boolean add(String nom, String prenom, String email, String telephone, float salaire, Role role, Poste poste) {
     
        if (!email.contains("@") && !email.contains(".")) {
            System.out.println("L'email est invalide !");
            return false;
        }
        
        Employe nvEmploye = new Employe(nom, prenom, email, telephone, salaire, role, poste);
      
        dao.add(nvEmploye);
        return true;    
    }
    
    
    public boolean delete(int id) {
        try {
            dao.delete(id); 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;    
        }    
    }
   
    public boolean update(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste) {
        try {
           
            Employe nvEmploye = new Employe(id, nom, prenom, email, telephone, salaire, role, poste);
           
            dao.update(nvEmploye);
            return true;            
        } catch (Exception e) {
            e.printStackTrace();
            return false;    
        }    
    }
   
    public List<Employe> getAllEmployes() {
        return dao.getAll();
    }
}
