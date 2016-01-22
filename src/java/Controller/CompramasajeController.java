package Controller;

import entidades.Compramasaje;
import Controller.util.JsfUtil;
import Controller.util.JsfUtil.PersistAction;
import Facade.CompramasajeFacade;
import TO.CompraMasajeTO;
import Utils.UtilsFechaHora;
import constantes.Constant;

import java.io.Serializable;
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

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import entidades.Agendacancelada;
import entidades.Rating;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpServletResponse;
import static org.apache.jasper.Constants.DEFAULT_BUFFER_SIZE;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "compramasajeController")
@SessionScoped
public class CompramasajeController implements Serializable {

    @EJB
    private Facade.CompramasajeFacade ejbFacade;
    @EJB
    private Facade.RatingFacade ejbRating;
    @EJB
    private Facade.AgendacanceladaFacade ejbCancelada;
    private List<Compramasaje> items = null;
    private Compramasaje selected;
    private Compramasaje paraEditar;
    private Compramasaje terminada;
    private List<Compramasaje> misMasajes = null;
    private List<CompraMasajeTO> misMasa = null;
    private String estado;
    private int rating = 1;
    private File guardado;

    public CompramasajeController() {
    }

    public Compramasaje getSelected() {
        return selected;
    }

    public void eliminar(Compramasaje eliminar) {
        selected = eliminar;
        Agendacancelada cancelar = new Agendacancelada();
        cancelar.setAgeCanComCliId(eliminar.getComMasCliId());
        cancelar.setAgeCanComPro(eliminar.getComMasProId());
        cancelar.setAgeCanComCos(eliminar.getComMasCos());
        cancelar.setAgeCanComTipMas(eliminar.getComMasTipMasNom());
        cancelar.setAgeCanComHor(eliminar.getComMasHor());
        cancelar.setAgeCanComEst(eliminar.getComMasEst());
        cancelar.setAgeCanComId(eliminar.getComMasId());
        cancelar.setAgeCanComUsuCanId(LoginController.usuario);
        boolean resp = ejbCancelada.guardar(cancelar);
        if (resp) {
            System.out.println("hora cancelada registrada en agendas canceladas");
        }
        destroy();
    }

    public void cargarPDF(Compramasaje item) throws DocumentException, FileNotFoundException, IOException, InterruptedException {
        FacesContext context = FacesContext.getCurrentInstance();
          HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

        String nombreDocumento = "Consulta_" + item.getComMasTipMasNom().getTipMasNom() + "_" + item.getComMasId();
        String dia = UtilsFechaHora.DIA_MES_ANIO.format(item.getComMasHor()) + "-" + UtilsFechaHora.HORA_MINUTO.format(item.getComMasHor());

        File pdfFile = new File(Constant.DIRECTORIO + "\\" + nombreDocumento + ".pdf");
        if (!pdfFile.exists()) {
            File directorio = new File(Constant.DIRECTORIO);
            directorio.mkdir();
            OutputStream archivoDoc = new FileOutputStream(new File(directorio + "\\" + nombreDocumento + ".pdf"));
            Document documento = new Document();
            PdfWriter.getInstance(documento, archivoDoc);
            documento.open();
            documento.add(new Paragraph("Boucher de Atencion"));
            Paragraph titulo = new Paragraph("Informacion");
            titulo.setAlignment(Constant.CENTRADO);
            documento.add(titulo);
            documento.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(Constant.NUMERO_COLUMNAS);
            tabla.addCell("Nombre Cliente:");
            tabla.addCell(item.getComMasCliId().getUsuNom() + " " + item.getComMasCliId().getUsuApe());
            tabla.addCell("RUT: ");
            tabla.addCell(item.getComMasCliId().getUsuRut());
            tabla.addCell("Tipo de masaje: ");
            tabla.addCell(item.getComMasTipMasNom().getTipMasNom());
            tabla.addCell("Fecha de atencion:");
            tabla.addCell(UtilsFechaHora.DIA_MES_ANIO.format(item.getComMasHor()));
            tabla.addCell("Hora de atencion:");
            tabla.addCell(UtilsFechaHora.HORA_MINUTO.format(item.getComMasHor()));
            tabla.addCell("Profesional que atiende:");
            tabla.addCell(item.getComMasProId().getUsuNom() + " " + item.getComMasProId().getUsuApe());
            tabla.addCell("Costo de la atencion:");
            tabla.addCell(Integer.toString(item.getComMasCos()));
            documento.add(tabla);
            documento.add(new Paragraph(" "));
            documento.close();
            archivoDoc.close();
            File pdfFileNew = new File(Constant.DIRECTORIO + "\\" + nombreDocumento + ".pdf");
            if (pdfFileNew.exists()) {
                response.reset();
                response.setBufferSize(DEFAULT_BUFFER_SIZE);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Length", String.valueOf(pdfFileNew.length()));
                response.setHeader("Content-Disposition", "attachment;filename=\""
                        + pdfFileNew.getName() + "\"");
                BufferedInputStream input = null;
                BufferedOutputStream output = null;

                try {
                    input = new BufferedInputStream(new FileInputStream(pdfFileNew),
                            DEFAULT_BUFFER_SIZE);
                    output = new BufferedOutputStream(response.getOutputStream(),
                            DEFAULT_BUFFER_SIZE);
                    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                    int length;
                    while ((length = input.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }
                } finally {
                    input.close();
                    output.close();
                }
                context.responseComplete();
            }
        } else {
            response.reset();
                response.setBufferSize(DEFAULT_BUFFER_SIZE);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Length", String.valueOf(pdfFile.length()));
                response.setHeader("Content-Disposition", "attachment;filename=\""
                        + pdfFile.getName() + "\"");
                BufferedInputStream input = null;
                BufferedOutputStream output = null;

                try {
                    input = new BufferedInputStream(new FileInputStream(pdfFile),
                            DEFAULT_BUFFER_SIZE);
                    output = new BufferedOutputStream(response.getOutputStream(),
                            DEFAULT_BUFFER_SIZE);
                    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                    int length;
                    while ((length = input.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }
                } finally {
                    input.close();
                    output.close();
                }
                context.responseComplete();
        }

    }

    public Boolean guardarRating() {
        Rating ratingnuevo = new Rating();
        ratingnuevo.setRatRat(rating);
        ratingnuevo.setRatProId(paraEditar.getComMasProId());
        boolean resRating = ejbRating.guardar(ratingnuevo);
        return resRating;
    }

    public void editar() {
        if (estado != null) {
            paraEditar.setComMasEst(estado);
            boolean resp = ejbFacade.editar(paraEditar);
            if (LoginController.usuario.getUsuTipUsuId().getTipUsuId().equals(Constant.TIPO_USUARIO_CLIENTE)) {
                guardarRating();
                if (resp) {
                    RequestContext.getCurrentInstance().update("growl");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "EL termino de la atencion Id: " + paraEditar.getComMasId() + " con fecha " + UtilsFechaHora.HORA_DIA_MES_ANIO.format(paraEditar.getComMasHor()) + " se realizo exitosamente"));
                } else {
                    RequestContext.getCurrentInstance().update("growl");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "EL termino de la atencion de " + paraEditar.getComMasTipMasNom().getTipMasNom() + "con fecha " + UtilsFechaHora.HORA_DIA_MES_ANIO.format(paraEditar.getComMasHor()) + " no se pudo realizar"));

                }
            }
            if (LoginController.usuario.getUsuTipUsuId().getTipUsuId().equals(Constant.TIPO_USUARIO_ADMIN)) {
                if (resp) {
                    RequestContext.getCurrentInstance().update("growl");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "La atencion Id: " + paraEditar.getComMasId() + " con fecha " + UtilsFechaHora.HORA_DIA_MES_ANIO.format(paraEditar.getComMasHor()) + " Se a Guardado como En Curso, pasar a atenderse"));
                } else {
                    RequestContext.getCurrentInstance().update("growl");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La atencion Id: " + paraEditar.getComMasTipMasNom().getTipMasNom() + "con fecha " + UtilsFechaHora.HORA_DIA_MES_ANIO.format(paraEditar.getComMasHor()) + " No se pudo Guardar como En curso"));

                }
            }
            if (LoginController.usuario.getUsuTipUsuId().getTipUsuId().equals(Constant.TIPO_USUARIO_PROFESIONAL)) {
                if (resp) {
                    RequestContext.getCurrentInstance().update("growl");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "La atencion Id: " + paraEditar.getComMasId() + " con fecha " + UtilsFechaHora.HORA_DIA_MES_ANIO.format(paraEditar.getComMasHor()) + " Se a Guardado como Atendida"));
                } else {
                    RequestContext.getCurrentInstance().update("growl");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La atencion Id: " + paraEditar.getComMasTipMasNom().getTipMasNom() + "con fecha " + UtilsFechaHora.HORA_DIA_MES_ANIO.format(paraEditar.getComMasHor()) + " No se pudo Guardar como Atendida"));

                }
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
        rating = 0;
        estado = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('CompramasajeEditEstadoDialog').show();");
    }

    public List<CompraMasajeTO> getMisMasajesAgendados() {
        if (LoginController.usuario != null) {
            misMasajes = ejbFacade.getMasajeByIdAndAgendada(LoginController.usuario);
            misMasa = new ArrayList<>();

            for (Compramasaje compramasaje : misMasajes) {
                CompraMasajeTO cmto = new CompraMasajeTO();
                cmto.setListaMasajes(compramasaje);
                cmto.setFechaMasaje(UtilsFechaHora.HORA_DIA_MES_ANIO.format(compramasaje.getComMasHor()));
                misMasa.add(cmto);
            }
        }
        return misMasa;
    }

    public List<CompraMasajeTO> getAgendaProfesionalAgendada() {
        if (LoginController.usuario != null) {
            misMasajes = ejbFacade.getMasajeByProfesionalAndEstado(LoginController.usuario, Constant.ESTADO_AGENDADA);
            misMasa = new ArrayList<>();

            for (Compramasaje compramasaje : misMasajes) {
                CompraMasajeTO cmto = new CompraMasajeTO();
                cmto.setListaMasajes(compramasaje);
                cmto.setFechaMasaje(UtilsFechaHora.HORA_DIA_MES_ANIO.format(compramasaje.getComMasHor()));
                misMasa.add(cmto);
            }
        }
        return misMasa;
    }

    public List<CompraMasajeTO> getAgendaProfesionalCurso() {
        if (LoginController.usuario != null) {
            misMasajes = ejbFacade.getMasajeByProfesionalAndEstado(LoginController.usuario, Constant.ESTADO_EN_CURSO);
            misMasa = new ArrayList<>();

            for (Compramasaje compramasaje : misMasajes) {
                CompraMasajeTO cmto = new CompraMasajeTO();
                cmto.setListaMasajes(compramasaje);
                cmto.setFechaMasaje(UtilsFechaHora.HORA_DIA_MES_ANIO.format(compramasaje.getComMasHor()));
                misMasa.add(cmto);
            }
        }
        return misMasa;
    }

    public List<CompraMasajeTO> getAgendaProfesionalAtendida() {
        if (LoginController.usuario != null) {
            misMasajes = ejbFacade.getMasajeByProfesionalAndEstado(LoginController.usuario, Constant.ESTADO_ATENDIDA);
            misMasa = new ArrayList<>();

            for (Compramasaje compramasaje : misMasajes) {
                CompraMasajeTO cmto = new CompraMasajeTO();
                cmto.setListaMasajes(compramasaje);
                cmto.setFechaMasaje(UtilsFechaHora.HORA_DIA_MES_ANIO.format(compramasaje.getComMasHor()));
                misMasa.add(cmto);
            }
        }
        return misMasa;
    }

    public List<CompraMasajeTO> getAgendaProfesionalTerminada() {
        if (LoginController.usuario != null) {
            misMasajes = ejbFacade.getMasajeByProfesionalAndEstado(LoginController.usuario, Constant.ESTADO_TERMINADA);

            misMasa = new ArrayList<>();

            for (Compramasaje compramasaje : misMasajes) {
                CompraMasajeTO cmto = new CompraMasajeTO();
                cmto.setListaMasajes(compramasaje);
                cmto.setFechaMasaje(UtilsFechaHora.HORA_DIA_MES_ANIO.format(compramasaje.getComMasHor()));
                misMasa.add(cmto);
            }
        }
        return misMasa;
    }

    public List<CompraMasajeTO> getMisMasajesAtendidos() {
        if (LoginController.usuario != null) {
            misMasajes = ejbFacade.getMasajeByIdAndAtendida(LoginController.usuario);
            misMasa = new ArrayList<>();
            for (Compramasaje compramasaje : misMasajes) {
                CompraMasajeTO cmto = new CompraMasajeTO();
                cmto.setListaMasajes(compramasaje);
                cmto.setFechaMasaje(UtilsFechaHora.HORA_DIA_MES_ANIO.format(compramasaje.getComMasHor()));
                misMasa.add(cmto);
            }
        }
        return misMasa;
    }

    public List<CompraMasajeTO> getAllMasajesAtendidos() {
        misMasajes = ejbFacade.getMasajeByAtendida();
        misMasa = new ArrayList<>();
        for (Compramasaje compramasaje : misMasajes) {
            CompraMasajeTO cmto = new CompraMasajeTO();
            cmto.setListaMasajes(compramasaje);
            cmto.setFechaMasaje(UtilsFechaHora.HORA_DIA_MES_ANIO.format(compramasaje.getComMasHor()));
            misMasa.add(cmto);
        }
        return misMasa;
    }

    public List<CompraMasajeTO> getAllMasajesAgendado() {
        misMasajes = ejbFacade.getMasajeAgendado();
        misMasa = new ArrayList<>();
        for (Compramasaje compramasaje : misMasajes) {
            CompraMasajeTO cmto = new CompraMasajeTO();
            cmto.setListaMasajes(compramasaje);
            cmto.setFechaMasaje(UtilsFechaHora.HORA_DIA_MES_ANIO.format(compramasaje.getComMasHor()));
            misMasa.add(cmto);
        }
        return misMasa;
    }

    public List<CompraMasajeTO> getAllMasajesEnCurso() {
        misMasajes = ejbFacade.getMasajeByCurso();
        misMasa = new ArrayList<>();
        for (Compramasaje compramasaje : misMasajes) {
            CompraMasajeTO cmto = new CompraMasajeTO();
            cmto.setListaMasajes(compramasaje);
            cmto.setFechaMasaje(UtilsFechaHora.HORA_DIA_MES_ANIO.format(compramasaje.getComMasHor()));
            misMasa.add(cmto);
        }
        return misMasa;
    }

    public List<CompraMasajeTO> getAllMasajesTerminado() {
        misMasajes = ejbFacade.getMasajeByTerminada();
        misMasa = new ArrayList<>();
        for (Compramasaje compramasaje : misMasajes) {
            CompraMasajeTO cmto = new CompraMasajeTO();
            cmto.setListaMasajes(compramasaje);
            cmto.setFechaMasaje(UtilsFechaHora.HORA_DIA_MES_ANIO.format(compramasaje.getComMasHor()));
            misMasa.add(cmto);
        }
        return misMasa;
    }

    public List<CompraMasajeTO> getMisMasajesTerminados() {
        if (LoginController.usuario != null) {
            misMasajes = ejbFacade.getMasajeByIdAndTerminada(LoginController.usuario);

            misMasa = new ArrayList<>();

            for (Compramasaje compramasaje : misMasajes) {
                CompraMasajeTO cmto = new CompraMasajeTO();
                cmto.setListaMasajes(compramasaje);
                cmto.setFechaMasaje(UtilsFechaHora.HORA_DIA_MES_ANIO.format(compramasaje.getComMasHor()));
                misMasa.add(cmto);
            }
        }
        return misMasa;
    }

    public void setSelected(Compramasaje selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CompramasajeFacade getFacade() {
        return ejbFacade;
    }

    public Compramasaje prepareCreate() {
        selected = new Compramasaje();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CompramasajeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CompramasajeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CompramasajeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Compramasaje> getItems() {
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

    public List<Compramasaje> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Compramasaje> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public Compramasaje getTerminada() {
        return terminada;
    }

    public void setTerminada(Compramasaje terminada) {
        this.terminada = terminada;
    }

    public File getGuardado() {
        return guardado;
    }

    public void setGuardado(File guardado) {
        this.guardado = guardado;
    }

    @FacesConverter(forClass = Compramasaje.class)
    public static class CompramasajeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompramasajeController controller = (CompramasajeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "compramasajeController");
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
            if (object instanceof Compramasaje) {
                Compramasaje o = (Compramasaje) object;
                return getStringKey(o.getComMasId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Compramasaje.class.getName()});
                return null;
            }
        }

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
