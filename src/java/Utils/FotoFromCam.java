package Utils;

import Controller.LoginController;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author manu
 */
@ManagedBean
@SessionScoped
public class FotoFromCam {
    public void handleFileUpload(FileUploadEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String txtField = ec.getRequestParameterMap().get("myform:op");
        String des = ec.getRequestParameterMap().get("myform:descripcion");
        String usu = LoginController.usuario.getUsuNom() + "_" + LoginController.usuario.getUsuApe() + "img";
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        try {
            File resul = new File(externalContext.getRealPath("/tmp/") + File.separator + txtField + event.getFile().getFileName());
            FileOutputStream out = new FileOutputStream(resul);

            byte[] buffer = new byte[2048];
            int bulk;
            InputStream inputStream = event.getFile().getInputstream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }
                out.write(buffer, 0, bulk);
                out.flush();
            }
            

        } catch (Exception ex) {

        }

        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


}
