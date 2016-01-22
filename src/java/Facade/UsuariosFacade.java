/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

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
public class UsuariosFacade extends AbstractFacade<Usuarios> {
    @PersistenceContext(unitName = "MasajesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }
  public boolean guardar(Usuarios usuario) {
        boolean res = false;
        try {
            em.persist(usuario);
            res = true;
        } catch (Exception e) {

        }
        return res;
    }    
  
  public boolean editar(Usuarios usuario) {
        boolean res = false;
        try {
            if (em.find(Usuarios.class, usuario.getUsuId()) == null) {
                throw new IllegalArgumentException("Unknown Employee id");
            }

            em.merge(usuario);
            res = true;
        } catch (Exception e) {
        }
        return res;
    }
  
  
  public List<Usuarios> getMiUsuario(Usuarios clienteId) {
        Query query = em.createNamedQuery("Usuarios.findByUsuId", Usuarios.class);
        query.setParameter("usuId", clienteId.getUsuId());
        List<Usuarios> list = query.getResultList();
        return list;
    }
}
