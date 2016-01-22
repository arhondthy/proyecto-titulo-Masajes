/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import constantes.Constant;
import entidades.Tipomasaje;
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
public class TipomasajeFacade extends AbstractFacade<Tipomasaje> {
    @PersistenceContext(unitName = "MasajesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipomasajeFacade() {
        super(Tipomasaje.class);
    }
    
     public List<Tipomasaje> getMasajeHabilitado() {
        Query query = em.createNamedQuery("Tipomasaje.findByTipMasEst", Tipomasaje.class);
        query.setParameter("tipMasEst", Constant.ESTADO_HABILITADO);
        List<Tipomasaje> list = query.getResultList();
        return list;
    }
    
}
