/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import entidades.Masajesprofesional;
import entidades.Tipomasaje;
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
public class MasajesprofesionalFacade extends AbstractFacade<Masajesprofesional> {

    @PersistenceContext(unitName = "MasajesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MasajesprofesionalFacade() {
        super(Masajesprofesional.class);
    }

    public List<Masajesprofesional> getMasajeById(Tipomasaje masajesId) {
        Query query = em.createNamedQuery("Masajesprofesional.findByMasProIdMas", Masajesprofesional.class);
        query.setParameter("masProIdMas", masajesId);
        List<Masajesprofesional> list = query.getResultList();
        return list;
    }

    public List<Masajesprofesional> getMasajeByUsuId(Usuarios usu) {
        Query query = em.createNamedQuery("Masajesprofesional.findByMasProIdUsu", Masajesprofesional.class);
        query.setParameter("masProIdPro", usu);
        List<Masajesprofesional> list = query.getResultList();
        return list;
    }
    public boolean guardar(Masajesprofesional obj) {
        boolean res = false;
        try {
            em.persist(obj);
            res = true;
        } catch (Exception e) {

        }
        return res;
    }
    

}
