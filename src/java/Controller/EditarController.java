/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utils.UtilsFechaHora;
import entidades.Compramasaje;
import entidades.Rating;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author manu
 */
@ManagedBean(name = "editarController")
@RequestScoped
public class EditarController implements Serializable {

    private String estado;
    private int rating=1;
    private Compramasaje paraEditar;
    @EJB
    private Facade.CompramasajeFacade ejbCompramasaje;
    @EJB
    private Facade.RatingFacade ejbRating;
    
    public void editar() {
        if (estado != null) {
            paraEditar.setComMasEst(estado);
            boolean resp = ejbCompramasaje.editar(paraEditar);
            Rating ratingnuevo=new Rating();
            ratingnuevo.setRatRat(rating);
            ratingnuevo.setRatProId(paraEditar.getComMasProId());
            boolean resRating=ejbRating.guardar(null);
            if (resp) {

                RequestContext.getCurrentInstance().update("growl");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "EL termino de la atencion Id: " + paraEditar.getComMasId() + " con fecha " + UtilsFechaHora.HORA_DIA_MES_ANIO.format(paraEditar.getComMasHor()) + " se realizo exitosamente"));
            } else {
                RequestContext.getCurrentInstance().update("growl");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "EL termino de la atencion de " + paraEditar.getComMasTipMasNom().getTipMasNom() + "con fecha " + UtilsFechaHora.HORA_DIA_MES_ANIO.format(paraEditar.getComMasHor()) + " no se pudo realizar"));

            }
        } else {
            RequestContext.getCurrentInstance().update("growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Terminar no seleccionado"));

        }
        estado = null;
        paraEditar = null;
    }

    public void cargarEditar(Compramasaje compramasaje) {
        paraEditar = new Compramasaje();
        paraEditar = compramasaje;
        System.out.println(rating);
         System.out.println(estado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('CompramasajeEditEstadoDialog').show();");
    }

    public void cerrarEditar() {
        paraEditar = new Compramasaje();
        System.out.println(rating);
         System.out.println(estado);
         rating=0;
         estado=null;
         System.out.println(rating);
         System.out.println(estado);
         
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('CompramasajeEditEstadoDialog').hide();");
    }

    public EditarController() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Compramasaje getParaEditar() {
        return paraEditar;
    }

    public void setParaEditar(Compramasaje paraEditar) {
        this.paraEditar = paraEditar;
    }

}
