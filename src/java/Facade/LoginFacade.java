/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import entidades.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author manu
 */
public class LoginFacade {
    
    EntityManagerFactory emf;
    EntityManager em;
    
    public LoginFacade(){
     emf= Persistence.createEntityManagerFactory("MasajesPU");
     em=emf.createEntityManager();
     //em.getTransaction().begin();
    }
    
    public Usuarios loginControl(String userName, String pass){
        try {
           Usuarios usuarios;
            Query q=em.createNamedQuery("Usuarios.findByNomUsuAndPass",Usuarios.class);
            q.setParameter("usuNomUsu",userName);
            q.setParameter("usuPas",pass);
            usuarios=(Usuarios) q.getSingleResult();
           
            if(usuarios!=null){
                return usuarios;
            }else{
                return null;
            }
        } catch (Exception e) {
        return null;
        }
    }
    
}
