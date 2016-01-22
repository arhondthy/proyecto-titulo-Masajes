package Controller;

import entidades.Agendacancelada;
import Facade.util.JsfUtil;
import Facade.util.JsfUtil.PersistAction;
import Facade.AgendacanceladaFacade;
import TO.AgendaCanceladaTO;
import Utils.UtilsFechaHora;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "agendacanceladaController")
@SessionScoped
public class AgendacanceladaController implements Serializable {

    @EJB
    private Facade.AgendacanceladaFacade ejbFacade;
    private List<Agendacancelada> items = null;
    private Agendacancelada selected;
    private List<AgendaCanceladaTO> canceladas = null;

    public AgendacanceladaController() {
    }

    public Agendacancelada getSelected() {
        return selected;
    }

    public void setSelected(Agendacancelada selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AgendacanceladaFacade getFacade() {
        return ejbFacade;
    }

    public Agendacancelada prepareCreate() {
        selected = new Agendacancelada();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AgendacanceladaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AgendacanceladaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AgendacanceladaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Agendacancelada> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Agendacancelada> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Agendacancelada> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<AgendaCanceladaTO> getCanceladas() {
         items = getFacade().findAll();
         canceladas=new ArrayList<>();
         for(Agendacancelada a:items){
             AgendaCanceladaTO acto=new AgendaCanceladaTO();
             acto.setCancelada(a);
             acto.setFechaAgendada(UtilsFechaHora.HORA_DIA_MES_ANIO.format(a.getAgeCanComHor()));
             canceladas.add(acto);
         }
        return canceladas;
    }

    public void setCanceladas(List<AgendaCanceladaTO> canceladas) {
        this.canceladas = canceladas;
    }

    @FacesConverter(forClass = Agendacancelada.class)
    public static class AgendacanceladaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AgendacanceladaController controller = (AgendacanceladaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "agendacanceladaController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Agendacancelada) {
                Agendacancelada o = (Agendacancelada) object;
                return getStringKey(o.getAgeCanId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Agendacancelada.class.getName()});
                return null;
            }
        }

    }

}
