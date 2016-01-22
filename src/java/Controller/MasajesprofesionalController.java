package Controller;

import entidades.Masajesprofesional;
import Controller.util.JsfUtil;
import Controller.util.JsfUtil.PersistAction;
import Facade.MasajesprofesionalFacade;
import entidades.Tipomasaje;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "masajesprofesionalController")
@SessionScoped
public class MasajesprofesionalController implements Serializable {

    @EJB
    private Facade.MasajesprofesionalFacade ejbFacade;
    @EJB
    private Facade.TipomasajeFacade tipoMasajeFacade;
    private List<Masajesprofesional> items = null;
    private List<Masajesprofesional> misMasajes = null;
    private List<Tipomasaje> masajesDisponibles = null;
    private Masajesprofesional selected;
    private Tipomasaje tipomasaje;

    public MasajesprofesionalController() {
    }

    public void agregarMasaje() {
        if (tipomasaje != null) {
            selected = new Masajesprofesional();
            selected.setMasProIdPro(LoginController.usuario);
            selected.setMasProIdMas(tipomasaje);
            Boolean respuesta = ejbFacade.guardar(selected);
            if (respuesta) {
                System.out.println("ingreso exitoso");
            }
        } else {
            RequestContext.getCurrentInstance().update(":growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña invalida"));
        }
    }

    public Masajesprofesional getSelected() {
        return selected;
    }

    public void setSelected(Masajesprofesional selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MasajesprofesionalFacade getFacade() {
        return ejbFacade;
    }

    public Masajesprofesional prepareCreate() {
        selected = new Masajesprofesional();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MasajesprofesionalCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MasajesprofesionalUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MasajesprofesionalDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Masajesprofesional> getItems() {
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

    public List<Masajesprofesional> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Masajesprofesional> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<Masajesprofesional> getMisMasajes() {
        misMasajes = ejbFacade.getMasajeByUsuId(LoginController.usuario);
        return misMasajes;
    }

    public void setMisMasajes(List<Masajesprofesional> misMasajes) {
        this.misMasajes = misMasajes;
    }

    public Tipomasaje getTipomasaje() {
        return tipomasaje;
    }

    public void setTipomasaje(Tipomasaje tipomasaje) {
        this.tipomasaje = tipomasaje;
    }

    public List<Tipomasaje> getMasajesDisponibles() {
        List<Tipomasaje> mas = tipoMasajeFacade.findAll();
        Tipomasaje ab;
        for (Masajesprofesional a : misMasajes) {
            ab = a.getMasProIdMas();
            Iterator<Tipomasaje> iter = mas.iterator();
            while (iter.hasNext()) {
                if (iter.next().equals(ab)) {
                    iter.remove();
                }
            }

        }

        return masajesDisponibles = mas;
    }

    public void setMasajesDisponibles(List<Tipomasaje> masajesDisponibles) {
        this.masajesDisponibles = masajesDisponibles;
    }

    @FacesConverter(forClass = Masajesprofesional.class)
    public static class MasajesprofesionalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MasajesprofesionalController controller = (MasajesprofesionalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "masajesprofesionalController");
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
            if (object instanceof Masajesprofesional) {
                Masajesprofesional o = (Masajesprofesional) object;
                return getStringKey(o.getMasProId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Masajesprofesional.class.getName()});
                return null;
            }
        }

    }

}
