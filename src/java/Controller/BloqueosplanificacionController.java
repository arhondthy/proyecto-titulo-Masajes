package Controller;

import entidades.Bloqueosplanificacion;
import Facade.util.JsfUtil;
import Facade.util.JsfUtil.PersistAction;
import Controller.LoginController;
import Facade.BloqueosplanificacionFacade;
import TO.BloqueoTO;
import Utils.CargarBloqueoTO;

import java.io.Serializable;
import java.util.ArrayList;
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

@ManagedBean(name = "bloqueosplanificacionController")
@SessionScoped
public class BloqueosplanificacionController implements Serializable {

    @EJB
    private Facade.BloqueosplanificacionFacade ejbFacade;
    private List<Bloqueosplanificacion> items = null;
    private List<Bloqueosplanificacion> bloqPro = null;
    private List<BloqueoTO> bloqProTO = null;
    private BloqueoTO bloqueoSelected = null;
    private Bloqueosplanificacion selected;
    private List<String> diasBloqueados = null;

    public BloqueosplanificacionController() {
    }

    public void eliminar() {
        selected = bloqueoSelected.getBloqueo();
        destroy();
    }

    public void guardarBloqueo() {
        if (LoginController.usuario != null) {
            List<Bloqueosplanificacion> exist = ejbFacade.getMisBloqueos(LoginController.usuario);
            if (exist.isEmpty()) {
                if (!diasBloqueados.isEmpty()) {
                    selected = new Bloqueosplanificacion();
                    String dias = "";
                    for (String dB : diasBloqueados) {
                        if (dias.isEmpty()) {
                            dias = dB;
                        } else {
                            dias = dias + ";" + dB;
                        }
                    }
                    selected.setBloDia(dias);
                    selected.setBloProId(LoginController.usuario);
                    Boolean resp = ejbFacade.guardar(selected);
                    if (resp) {
                        RequestContext.getCurrentInstance().update(":growl");
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Bloqueo creada exitosamente"));

                    } else {
                        RequestContext.getCurrentInstance().update(":growl");
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar un dia de bloqueo"));
                    }
                }
            } else {
                RequestContext.getCurrentInstance().update(":growl");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ya posee un bloqueo de dias"));
            }
        }
    }

    public Bloqueosplanificacion getSelected() {
        return selected;
    }

    public void setSelected(Bloqueosplanificacion selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BloqueosplanificacionFacade getFacade() {
        return ejbFacade;
    }

    public void cargarCrear() {
        diasBloqueados = null;
    }

    public Bloqueosplanificacion prepareCreate() {
        selected = new Bloqueosplanificacion();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BloqueosplanificacionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BloqueosplanificacionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BloqueosplanificacionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Bloqueosplanificacion> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<Bloqueosplanificacion> getBloqueoByPro() {
        bloqPro = ejbFacade.getMisBloqueos(LoginController.usuario);
        return bloqPro;
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

    public List<Bloqueosplanificacion> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Bloqueosplanificacion> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public void setBloqPro(List<Bloqueosplanificacion> bloqPro) {
        this.bloqPro = bloqPro;
    }

    public List<String> getDiasBloqueados() {
        diasBloqueados = null;
        return diasBloqueados;
    }

    public void setDiasBloqueados(List<String> diasBloqueados) {
        this.diasBloqueados = diasBloqueados;

    }

    public List<BloqueoTO> getBloqProTO() {
        bloqPro = ejbFacade.getMisBloqueos(LoginController.usuario);
        bloqProTO = new ArrayList<>();
        CargarBloqueoTO bl = new CargarBloqueoTO();
        BloqueoTO bto = bl.cargarTO(bloqPro);
        if (bto != null) {
            bloqProTO.add(bto);
        }
        return bloqProTO;

    }

    public void setBloqProTO(List<BloqueoTO> bloqProTO) {
        this.bloqProTO = bloqProTO;
    }

    public BloqueoTO getBloqueoSelected() {
        return bloqueoSelected;
    }

    public void setBloqueoSelected(BloqueoTO bloqueoSelected) {
        this.bloqueoSelected = bloqueoSelected;
    }

    @FacesConverter(forClass = Bloqueosplanificacion.class)
    public static class BloqueosplanificacionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BloqueosplanificacionController controller = (BloqueosplanificacionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bloqueosplanificacionController");
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
            if (object instanceof Bloqueosplanificacion) {
                Bloqueosplanificacion o = (Bloqueosplanificacion) object;
                return getStringKey(o.getBloId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Bloqueosplanificacion.class.getName()});
                return null;
            }
        }

    }

}
