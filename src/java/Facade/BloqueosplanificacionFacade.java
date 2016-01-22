/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Controller.AbstractFacade;
import entidades.Bloqueosplanificacion;
import entidades.Usuarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author manu
 */
@Stateless
public class BloqueosplanificacionFacade extends AbstractFacade<Bloqueosplanificacion> {
    @PersistenceContext(unitName = "MasajesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BloqueosplanificacionFacade() {
        super(Bloqueosplanificacion.class);
    }
    
     public List<Bloqueosplanificacion> getMisBloqueos(Usuarios proId){
        Query query = em.createNamedQuery("Bloqueosplanificacion.findByPro", Bloqueosplanificacion.class);
        query.setParameter("bloProId", proId);
       List<Bloqueosplanificacion>list=query.getResultList();
        return list;
    }

     
     public boolean guardar(Bloqueosplanificacion bloqueo) {
        boolean res = false;
        try {
            em.persist(bloqueo);
            res = true;
        } catch (Exception e) {

        }
        return res;
    }
    
}
