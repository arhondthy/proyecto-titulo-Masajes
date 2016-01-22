/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import entidades.Masajesprofesional;
import entidades.Planificacionprofesional;
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
public class PlanificacionprofesionalFacade extends AbstractFacade<Planificacionprofesional> {
    @PersistenceContext(unitName = "MasajesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanificacionprofesionalFacade() {
        super(Planificacionprofesional.class);
    }
    
    public List<Planificacionprofesional> getMisPlanificaciones(Usuarios usuarioId){
        Query query = em.createNamedQuery("Planificacionprofesional.findByPlaUsuId", Planificacionprofesional.class);
        query.setParameter("plaUsuId", usuarioId);
       List<Planificacionprofesional>list=query.getResultList();
        return list;
    }
    
      public boolean guardar(Planificacionprofesional planificacionprofesional) {
        boolean res = false;
        try {
            em.persist(planificacionprofesional);
            res = true;
        } catch (Exception e) {

        }
        return res;
    }
    
}
