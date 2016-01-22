/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import Controller.AbstractFacade;
import entidades.Agendacancelada;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author manu
 */
@Stateless
public class AgendacanceladaFacade extends AbstractFacade<Agendacancelada> {
    @PersistenceContext(unitName = "MasajesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgendacanceladaFacade() {
        super(Agendacancelada.class);
    }
    
    public boolean guardar(Agendacancelada eliminada) {
        boolean res = false;
        try {
            em.persist(eliminada);
            res = true;
        } catch (Exception e) {
        }
        return res;
    }
    
}
