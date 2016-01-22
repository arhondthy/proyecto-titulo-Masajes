/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 *
 * @author manu
 */
@ManagedBean
public class VistaTabs {
    public void CambioTab(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Cambio", "Se carga: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
         
    public void CierreTab(TabCloseEvent event) {
        FacesMessage msg = new FacesMessage("Cierre", "Se cierra: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
