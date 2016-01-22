/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;


import entidades.Rating;
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
public class RatingFacade extends AbstractFacade<Rating> {
    @PersistenceContext(unitName = "MasajesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RatingFacade() {
        super(Rating.class);
    }
    public boolean guardar(Rating rating) {
        boolean res = false;
        try {
            em.persist(rating);
            res = true;
        } catch (Exception e) {
        }
        return res;
    }
    
    public List<Rating> getRatingByProId(Usuarios proId) {
        Query query = em.createNamedQuery("Rating.findByProId", Rating.class);
        query.setParameter("ratProId", proId);
        List<Rating> list = query.getResultList();
        return list;
    }
    
}
