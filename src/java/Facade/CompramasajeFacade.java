/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import constantes.Constant;
import entidades.Compramasaje;
import entidades.Usuarios;
import java.util.Date;
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
public class CompramasajeFacade extends AbstractFacade<Compramasaje> {

    @PersistenceContext(unitName = "MasajesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompramasajeFacade() {
        super(Compramasaje.class);
    }

    public boolean guardar(Compramasaje compramasaje) {
        boolean res = false;
        try {
            em.persist(compramasaje);
            res = true;
        } catch (Exception e) {
        }
        return res;
    }

    public boolean editar(Compramasaje compramasaje) {
        boolean res = false;
        try {
            if (em.find(Compramasaje.class, compramasaje.getComMasId()) == null) {
                throw new IllegalArgumentException("Unknown Employee id");
            }

            em.merge(compramasaje);
            res = true;
        } catch (Exception e) {
        }
        return res;
    }

    public List<Compramasaje> getMasajeById(Usuarios clienteId) {
        Query query = em.createNamedQuery("Compramasaje.findByComMasCliId", Compramasaje.class);
        query.setParameter("comMasCliId", clienteId);
        List<Compramasaje> list = query.getResultList();
        return list;
    }

    public List<Compramasaje> getMasajeByIdAndAgendada(Usuarios clienteId) {
        Query query = em.createNamedQuery("Compramasaje.findByComMasCliIdAndEstado", Compramasaje.class);
        query.setParameter("comMasCliId", clienteId);
        query.setParameter("comMasEst", Constant.ESTADO_AGENDADA);
        List<Compramasaje> list = query.getResultList();
        return list;
    }

    public List<Compramasaje> getMasajeByIdAndAtendida(Usuarios clienteId) {
        Query query = em.createNamedQuery("Compramasaje.findByComMasCliIdAndEstado", Compramasaje.class);
        query.setParameter("comMasCliId", clienteId);
        query.setParameter("comMasEst", Constant.ESTADO_ATENDIDA);
        List<Compramasaje> list = query.getResultList();
        return list;
    }

    public List<Compramasaje> getMasajeByIdAndCurso(Usuarios clienteId) {
        Query query = em.createNamedQuery("Compramasaje.findByComMasCliIdAndEstado", Compramasaje.class);
        query.setParameter("comMasCliId", clienteId);
        query.setParameter("comMasEst", Constant.ESTADO_ATENDIDA);
        List<Compramasaje> list = query.getResultList();
        return list;
    }

    public List<Compramasaje> getMasajeByIdAndTerminada(Usuarios clienteId) {
        Query query = em.createNamedQuery("Compramasaje.findByComMasCliIdAndEstado", Compramasaje.class);
        query.setParameter("comMasCliId", clienteId);
        query.setParameter("comMasEst", Constant.ESTADO_TERMINADA);
        List<Compramasaje> list = query.getResultList();
        return list;
    }

    public List<Compramasaje> getMasajeByProfesionalAndEstado(Usuarios profesional, String estado) {
        Query query = em.createNamedQuery("Compramasaje.findByProfesionalAndEstado", Compramasaje.class);
        query.setParameter("comMasProId", profesional);
        query.setParameter("comMasEst", estado);
        List<Compramasaje> list = query.getResultList();
        return list;
    }

    public List<Compramasaje> getMasajeByCurso() {
        Query query = em.createNamedQuery("Compramasaje.findByEstado", Compramasaje.class);
        query.setParameter("comMasEst", Constant.ESTADO_EN_CURSO);
        List<Compramasaje> list = query.getResultList();
        return list;
    }
     public List<Compramasaje> getMasajeAgendado() {
        Query query = em.createNamedQuery("Compramasaje.findByEstado", Compramasaje.class);
        query.setParameter("comMasEst", Constant.ESTADO_AGENDADA);
        List<Compramasaje> list = query.getResultList();
        return list;
    }

    public List<Compramasaje> getMasajeByAtendida() {
        Query query = em.createNamedQuery("Compramasaje.findByEstado", Compramasaje.class);
        query.setParameter("comMasEst", Constant.ESTADO_ATENDIDA);
        List<Compramasaje> list = query.getResultList();
        return list;
    }

    public List<Compramasaje> getMasajeByTerminada() {
        Query query = em.createNamedQuery("Compramasaje.findByEstado", Compramasaje.class);
        query.setParameter("comMasEst", Constant.ESTADO_TERMINADA);
        List<Compramasaje> list = query.getResultList();
        return list;
    }

    public List<Compramasaje> getMasajeByProAndDia(Usuarios pro) {
        Query query = em.createNamedQuery("Compramasaje.findByComMasProId", Compramasaje.class);
        query.setParameter("comMasProId", pro);
        List<Compramasaje> list = query.getResultList();

        return list;
    }

}
