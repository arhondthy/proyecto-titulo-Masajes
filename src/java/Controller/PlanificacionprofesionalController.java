package Controller;

import entidades.Planificacionprofesional;
import Controller.util.JsfUtil;
import Controller.util.JsfUtil.PersistAction;
import Facade.PlanificacionprofesionalFacade;

import java.io.Serializable;
import java.util.Date;
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

@ManagedBean(name = "planificacionprofesionalController")
@SessionScoped
public class PlanificacionprofesionalController implements Serializable {

    @EJB
    private Facade.PlanificacionprofesionalFacade ejbFacade;
    private List<Planificacionprofesional> items = null;
    private Planificacionprofesional selected;

    private List<Planificacionprofesional> planificacionnes = null;
    private Date actual = new Date();

    public PlanificacionprofesionalController() {
    }

    public Planificacionprofesional getSelected() {
        return selected;
    }

    public void setSelected(Planificacionprofesional selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PlanificacionprofesionalFacade getFacade() {
        return ejbFacade;
    }

    public Planificacionprofesional prepareCreate() {
        selected = new Planificacionprofesional();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PlanificacionprofesionalCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void nuevaPlanificacion() {

        if (LoginController.usuario != null) {
            List<Planificacionprofesional> exist = ejbFacade.getMisPlanificaciones(LoginController.usuario);
            if (exist.isEmpty()) {
                if (selected.getPlaDiaHorIni().before(selected.getPlaDiaHorFin()) || selected.getPlaDiaHorIni().equals(selected.getPlaDiaHorFin())) {
                    selected.setPlaUsuId(LoginController.usuario);
                    Boolean respuesta = ejbFacade.guardar(selected);
                    if (respuesta) {
                        RequestContext.getCurrentInstance().update(":growl");
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Planificacion creada exitosamente"));
                    }
                } else {
                    RequestContext.getCurrentInstance().update(":growl");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña invalida"));
                }
            } else {
                RequestContext.getCurrentInstance().update(":growl");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ya posee una planificacion"));
            }
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PlanificacionprofesionalUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PlanificacionprofesionalDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Planificacionprofesional> getItems() {
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

    public List<Planificacionprofesional> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Planificacionprofesional> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<Planificacionprofesional> getPlanificacionnes() {
        if (LoginController.usuario != null) {
            planificacionnes = ejbFacade.getMisPlanificaciones(LoginController.usuario);
        }
        return planificacionnes;
    }

    public void setPlanificacionnes(List<Planificacionprofesional> planificacionnes) {
        this.planificacionnes = planificacionnes;
    }

    public Date getActual() {
        return actual;
    }

    public void setActual(Date actual) {
        this.actual = actual;
    }

    @FacesConverter(forClass = Planificacionprofesional.class)
    public static class PlanificacionprofesionalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PlanificacionprofesionalController controller = (PlanificacionprofesionalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "planificacionprofesionalController");
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
            if (object instanceof Planificacionprofesional) {
                Planificacionprofesional o = (Planificacionprofesional) object;
                return getStringKey(o.getIdplaPro());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Planificacionprofesional.class.getName()});
                return null;
            }
        }

    }

}
