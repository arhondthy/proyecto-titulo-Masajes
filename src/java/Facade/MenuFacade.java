/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import entidades.Menu;
import entidades.Tipousuario;
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
public class MenuFacade extends AbstractFacade<Menu> {
    @PersistenceContext(unitName = "MasajesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuFacade() {
        super(Menu.class);
    }
    public List<Menu> menuByIdUsuario(Tipousuario tipousuario){
         Query query = em.createNamedQuery("Menu.findBytipUsu", Menu.class);
        query.setParameter("mentipoUserid", tipousuario);
       List<Menu>list=query.getResultList();
        return list;
        
    }
    
}
