/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import constantes.Constant;
import javax.annotation.security.RunAs;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RateEvent;



/**
 *
 * @author manu
 */
@ManagedBean(name = "rating")
@RequestScoped
public class UtilsRating {
    
    public void onrate(RateEvent rateEvent) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.RATING_CALIFICAR, Constant.RATING_SELECCIONADO + ((Integer) rateEvent.getRating()).intValue());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
     
    public void oncancel() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.RATING_CANCELAR, Constant.RATING_CANCELADO);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
