package Controller;

import entidades.Tipomasaje;
import Controller.util.JsfUtil;
import Controller.util.JsfUtil.PersistAction;
import Facade.TipomasajeFacade;
import TO.BloqueoTO;
import Utils.CargarBloqueoTO;
import Utils.UtilsFechaHora;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import static com.sun.xml.bind.util.CalendarConv.formatter;
import constantes.Constant;
import entidades.Bloqueosplanificacion;
import entidades.Compramasaje;
import entidades.Masajesprofesional;
import entidades.Planificacionprofesional;
import entidades.Rating;
import entidades.Usuarios;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
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

@ManagedBean(name = "tipomasajeController")
@SessionScoped
public class TipomasajeController implements Serializable {

    @EJB
    private Facade.TipomasajeFacade ejbFacade;

    @EJB
    private Facade.MasajesprofesionalFacade ejbFacadeMasajesProfesional;
    @EJB
    private Facade.CompramasajeFacade compramasajeFacade;
    @EJB
    private Facade.PlanificacionprofesionalFacade facade;
    @EJB
    private Facade.RatingFacade ejbRating;
    @EJB
    private Facade.BloqueosplanificacionFacade ejbBloqueo;
    private List<Tipomasaje> items = null;
    private List<Tipomasaje> masajesHabilitados = null;
    private List<Masajesprofesional> masajesByProfesional = null;
    private Tipomasaje selected;
    private Compramasaje compramasaje;
    private Usuarios profesionalId;
    private Date fechaAgenda;
    private Date actual = new Date();
    private List<String> listaFechas = null;
    private List<String> listaHoras = null;
    private String diaSeleccionado;
    private String hora;
    private Planificacionprofesional plani;
    private Integer ratingByPro;
    private String nombreDia;

    private Boolean proSeleccionado = false;

    public List<String> Planificacionprofesional() {
        List<Planificacionprofesional> p = facade.getMisPlanificaciones(profesionalId);
        CargarBloqueoTO cargarBloqueoTO = new CargarBloqueoTO();
        List<Bloqueosplanificacion> bloqueosplanificacion = ejbBloqueo.getMisBloqueos(profesionalId);
        BloqueoTO bloqueoTO = cargarBloqueoTO.cargarTO(bloqueosplanificacion);
        if (bloqueoTO == null) {
            for (Planificacionprofesional pla : p) {
                UtilsFechaHora ufh = new UtilsFechaHora();
                long diffDays = ufh.getDiferenciaDias(pla.getPlaDiaHorIni(), pla.getPlaDiaHorFin());
                listaFechas = new ArrayList<>();
                String sHoy = UtilsFechaHora.DIA_MES_ANIO.format(UtilsFechaHora.HOY);
                while (diffDays != Constant.CONTADOR_HORAS) {

                    if (UtilsFechaHora.calendar.get(Calendar.DAY_OF_WEEK) != Constant.DOMINGO) {
                        Date a = UtilsFechaHora.calendar.getTime();
                        String today = UtilsFechaHora.DIA_MES_ANIO.format(a);
                        if (sHoy.equals(today) || a.after(UtilsFechaHora.HOY)) {
                            listaFechas.add(today);
                        }
                    }
                    UtilsFechaHora.calendar.add(Calendar.DAY_OF_MONTH, Constant.DOMINGO);
                    diffDays--;
                }
                plani = pla;
                break;
            }
        } else {
            for (Planificacionprofesional pla : p) {
                UtilsFechaHora ufh = new UtilsFechaHora();
                long diffDays = ufh.getDiferenciaDias(pla.getPlaDiaHorIni(), pla.getPlaDiaHorFin());
                listaFechas = new ArrayList<>();
                String sHoy = UtilsFechaHora.DIA_MES_ANIO.format(UtilsFechaHora.HOY);
                List<Integer> diasBLoqueo = cargarBloqueoTO.getListaDias(bloqueoTO);
                while (diffDays != Constant.CONTADOR_HORAS) {
                    if (!diasBLoqueo.contains(UtilsFechaHora.calendar.get(Calendar.DAY_OF_WEEK))) {
                        Date a = UtilsFechaHora.calendar.getTime();
                        String today = UtilsFechaHora.DIA_MES_ANIO.format(a);
                        if (sHoy.equals(today) || a.after(UtilsFechaHora.HOY)) {
                            listaFechas.add(today);
                        }
                    }
                    UtilsFechaHora.calendar.add(Calendar.DAY_OF_MONTH, Constant.DOMINGO);
                    diffDays--;
                }
                plani = pla;
                break;
            }
        }
        return listaFechas;
    }

    public void cargarRating() {
        List<Rating> ratingList = ejbRating.getRatingByProId(profesionalId);
        if (!ratingList.isEmpty()) {
            int sumatoriaRating = Constant.CONTADOR_HORAS;
            int porcent = Constant.RATING * ratingList.size();
            for (Rating ra : ratingList) {
                sumatoriaRating = sumatoriaRating + ra.getRatRat();
            }
            ratingByPro = (sumatoriaRating * Constant.PERCENT) / porcent;
            if (ratingByPro <= Constant.VEINTE_PERCENT) {
                ratingByPro = Constant.RATING_ONE;
            }
            if (ratingByPro > Constant.VEINTE_PERCENT && ratingByPro <= Constant.CUARENTA_PERCENT) {
                ratingByPro = Constant.RATING_TWO;
            }
            if (ratingByPro > Constant.CUARENTA_PERCENT && ratingByPro <= Constant.SESENTA_PERCENT) {
                ratingByPro = Constant.RATING_THREE;
            }
            if (ratingByPro > Constant.SESENTA_PERCENT && ratingByPro <= Constant.OCHENTA_PERCENT) {
                ratingByPro = Constant.RATING_FOUR;
            }
            if (ratingByPro > Constant.OCHENTA_PERCENT) {
                ratingByPro = Constant.RATING_FIVE;
            }
        } else {
            ratingByPro = Constant.CONTADOR_HORAS;
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Profesional seleccionado aun no posee calificaciones"));
        }

    }

    public void cargarPlanificacionByPro() {
        listaFechas = new ArrayList<>();
        List<String> lista = Planificacionprofesional();
        cargarRating();
        listaHoras = new ArrayList<>();
        if (!lista.isEmpty()) {
            fechaAgenda = new Date();
            proSeleccionado = true;
            RequestContext.getCurrentInstance().update("MensajeErrorAgenda");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", Constant.PLANIFICACION + profesionalId));
        } else {
            RequestContext.getCurrentInstance().update("MensajeErrorAgenda");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", Constant.PLANIFICACION_VACIA + profesionalId));
        }

    }

    public void cargarHoras() throws ParseException {
        nombreDia = UtilsFechaHora.NOMBRE_DIA.format(UtilsFechaHora.DIA_MES_ANIO.parse(diaSeleccionado));
        listaHoras = new ArrayList<>();
        int limiteHoras = plani.getPlaHor();
        List<Compramasaje> lista = compramasajeFacade.getMasajeByProAndDia(profesionalId);
        String diaActual = UtilsFechaHora.DIA_MES_ANIO.format(UtilsFechaHora.HOY);
        List<String> horasLimite = new ArrayList<>();
        List<String> horasFiltradas = new ArrayList<>();
        int contador = Constant.CONTADOR_HORAS;
        while (contador < limiteHoras) {
            horasLimite.add(Constant.HORAS.get(contador));
            contador++;
        }
        if (!lista.isEmpty()) {
            List<String> horasAgendadas = new ArrayList<>();
            for (Compramasaje co : lista) {
                String diaAgendado = UtilsFechaHora.DIA_MES_ANIO.format(co.getComMasHor());
                if (diaSeleccionado.equals(diaAgendado)) {
                    horasAgendadas.add(UtilsFechaHora.HORA_MINUTO.format(co.getComMasHor()));
                }
            }
            if (horasAgendadas.isEmpty()) {
                horasFiltradas = horasLimite;
            } else {
                String horaConsult;
                for (String agendada : horasAgendadas) {
                    horaConsult = agendada;
                    Iterator<String> iterHoras = horasLimite.iterator();
                    while (iterHoras.hasNext()) {
                        if (iterHoras.next().equals(horaConsult)) {
                            iterHoras.remove();
                        }
                    }
                }
                horasFiltradas = horasLimite;
            }
            if (diaSeleccionado.equals(diaActual)) {
                List<String> compararConHoraActual = horasFiltradas;
                horasFiltradas = new ArrayList<>();
                String horaActualStr = UtilsFechaHora.HORA.format(UtilsFechaHora.HOY);
                Integer horaActualInt = Integer.parseInt(horaActualStr);
                for (String comparar : compararConHoraActual) {
                    String[] compArray = comparar.split(":");
                    Integer horaComparar = Integer.parseInt(compArray[0]);
                    if (horaActualInt < horaComparar) {
                        horasFiltradas.add(comparar);
                    }
                }
                listaHoras = horasFiltradas;
            } else {
                listaHoras = horasFiltradas;
            }
        } else {
            if (diaSeleccionado.equals(diaActual)) {
                String horaActual = UtilsFechaHora.HORA.format(UtilsFechaHora.HOY);
                Integer horaActual2 = Integer.parseInt(horaActual);
                for (String ho : horasLimite) {
                    if (!horaActual.equals(ho)) {
                        String[] hoArray = ho.split(":");
                        Integer ho2 = Integer.parseInt(hoArray[0]);
                        if (ho2 > horaActual2) {
                            horasFiltradas.add(ho);
                        }
                    }
                }
                listaHoras = horasFiltradas;
            } else {
                listaHoras = horasLimite;
            }
        }
        if (listaHoras.isEmpty()) {
            RequestContext.getCurrentInstance().update("MensajeErrorAgenda");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exito", Constant.PLANI_SIN_HORAS + diaSeleccionado));
        } else {
            RequestContext.getCurrentInstance().update("MensajeErrorAgenda");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", Constant.PLANI_HORAS + profesionalId));
        }
    }

    public TipomasajeController() {
        listaHoras = null;
        listaFechas = null;
        profesionalId = null;

    }

    public String getMasajeConProfesional(Tipomasaje masaje) {
        profesionalId = new Usuarios();
        nombreDia = "";
        ratingByPro = 0;
        selected = masaje;
        List<Masajesprofesional> byPro = ejbFacadeMasajesProfesional.getMasajeById(masaje);
        this.masajesByProfesional = new ArrayList<>();
        for (Masajesprofesional masPro : byPro) {
            int estado = masPro.getMasProIdPro().getUsuEst();
            if (estado == Constant.ESTADO_HABILITADO) {
                this.masajesByProfesional.add(masPro);
            }
        }
        listaHoras = null;
        listaFechas = null;
        return "detalleMasaje";
    }

    public void guardarAgendaMasaje() throws ParseException {

        if (LoginController.usuario != null) {
            if (LoginController.usuario.getUsuTipUsuId().getTipUsuId().equals(Constant.TIPO_USUARIO_CLIENTE)) {
                compramasaje = new Compramasaje();
                compramasaje.setComMasCos(selected.getTipMasCos());
                compramasaje.setComMasTipMasNom(selected);
                compramasaje.setComMasProId(profesionalId);
                compramasaje.setComMasCliId(LoginController.usuario);
                fechaAgenda = UtilsFechaHora.HORA_DIA_MES_ANIO.parse(diaSeleccionado + " " + hora);

                compramasaje.setComMasHor(fechaAgenda);
                compramasaje.setComMasEst(Constant.ESTADO_AGENDADA);
                Boolean resp = compramasajeFacade.guardar(compramasaje);
                if (resp) {
                    compramasaje = new Compramasaje();
                    selected = null;
                    profesionalId = null;
                    fechaAgenda = null;
                    diaSeleccionado = null;
                    hora = null;
                    listaFechas = new ArrayList<>();
                    listaHoras = new ArrayList<>();
                    RequestContext.getCurrentInstance().update("MensajeErrorAgenda");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", Constant.MASAJE_AGENDADO));
                } else {
                    compramasaje = null;
                    selected = null;
                    profesionalId = null;
                    fechaAgenda = null;
                    diaSeleccionado = null;
                    hora = null;
                    listaFechas = new ArrayList<>();
                    listaHoras = new ArrayList<>();
                    RequestContext.getCurrentInstance().update("MensajeErrorAgenda");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", Constant.MASAJE_ERROR));
                }
            } else {
                RequestContext.getCurrentInstance().update("MensajeErrorAgenda");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", Constant.LOGEARSE_CLIENTE));
            }
        } else {
            RequestContext.getCurrentInstance().update("MensajeErrorAgenda");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", Constant.LOGEARSE));
        }
    }

    public Tipomasaje getSelected() {
        return selected;
    }

    public void setSelected(Tipomasaje selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipomasajeFacade getFacade() {
        return ejbFacade;
    }

    public Tipomasaje prepareCreate() {
        selected = new Tipomasaje();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipomasajeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipomasajeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipomasajeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Tipomasaje> getItems() {
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

    public List<Tipomasaje> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Tipomasaje> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<Masajesprofesional> getMasajesByProfesional() {
        return masajesByProfesional;
    }

    public void setMasajesByProfesional(List<Masajesprofesional> masajesByProfesional) {
        this.masajesByProfesional = masajesByProfesional;
    }

    public Compramasaje getCompramasaje() {
        return compramasaje;
    }

    public void setCompramasaje(Compramasaje compramasaje) {
        this.compramasaje = compramasaje;
    }

    public Usuarios getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(Usuarios profesionalId) {
        this.profesionalId = profesionalId;
    }

    public Date getFechaAgenda() {
        return fechaAgenda;
    }

    public void setFechaAgenda(Date fechaAgenda) {
        this.fechaAgenda = fechaAgenda;
    }

    public Boolean getProSeleccionado() {
        return proSeleccionado;
    }

    public void setProSeleccionado(Boolean proSeleccionado) {
        this.proSeleccionado = proSeleccionado;
    }

    public Date getActual() {
        return actual;
    }

    public void setActual(Date actual) {
        this.actual = actual;
    }

    public List<String> getListaFechas() {
        return listaFechas;
    }

    public void setListaFechas(List<String> listaFechas) {
        this.listaFechas = listaFechas;
    }

    public List<String> getListaHoras() {
        return listaHoras;
    }

    public void setListaHoras(List<String> listaHoras) {
        this.listaHoras = listaHoras;
    }

    public String getDia() {
        return diaSeleccionado;
    }

    public void setDia(String dia) {
        this.diaSeleccionado = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Planificacionprofesional getPlani() {
        return plani;
    }

    public void setPlani(Planificacionprofesional plani) {
        this.plani = plani;

    }

    public Integer getRatingByPro() {
        return ratingByPro;
    }

    public void setRatingByPro(Integer ratingByPro) {
        this.ratingByPro = ratingByPro;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public List<Tipomasaje> getMasajesHabilitados() {
        masajesHabilitados = ejbFacade.getMasajeHabilitado();
        return masajesHabilitados;
    }

    public void setMasajesHabilitados(List<Tipomasaje> masajesHabilitados) {
        this.masajesHabilitados = masajesHabilitados;
    }

    @FacesConverter(forClass = Tipomasaje.class)
    public static class TipomasajeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipomasajeController controller = (TipomasajeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipomasajeController");
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
            if (object instanceof Tipomasaje) {
                Tipomasaje o = (Tipomasaje) object;
                return getStringKey(o.getTipMasId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Tipomasaje.class.getName()});
                return null;
            }
        }

    }

}
