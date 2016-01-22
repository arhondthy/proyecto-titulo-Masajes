/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Facade.LoginFacade;
import entidades.Menu;
import entidades.Usuarios;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author manu
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginController implements Serializable {

    private String userName;
    private String pass;
    public static Usuarios usuario;
    private final LoginFacade loginFacade = new LoginFacade();
    private MenuModel menuModel;
    private Usuarios modUser;
    @EJB
    private Facade.MenuFacade ejbFacade;
 
 

    public void loginController() {
        LoginController.usuario = loginFacade.loginControl(userName, pass);
        if (LoginController.usuario != null) {
            RequestContext.getCurrentInstance().update("MensajeError");
            RequestContext.getCurrentInstance().update("msj");
            RequestContext.getCurrentInstance().update("labelUserName");
            RequestContext.getCurrentInstance().update("btnClose");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Logeo exitoso"));
            crearMenuDinamico();
        } else {
            RequestContext.getCurrentInstance().update("MensajeError");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña invalida"));
        }
    }

    public String logout() {
        if (usuario != null) {
            this.userName = null;
            this.pass = null;
            LoginController.usuario = null;
            RequestContext.getCurrentInstance().update("growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "exito", "Sesion cerrada"));
            return "/menuComun/Ingreso.xhtml";
        } else {
            return "/menuComun/Ingreso.xhtml";
        }
    }

    public MenuModel crearMenuDinamico() {
        try {
            this.menuModel = new DefaultMenuModel();
            List<Menu> items = ejbFacade.menuByIdUsuario(LoginController.usuario.getUsuTipUsuId());
            DefaultSubMenu subMenu = new DefaultSubMenu("Crud administracion");
            boolean entro = false;
            for (Menu menu : items) {
                if (menu.getMenNom().contains("rud")) {
                    DefaultMenuItem item = new DefaultMenuItem(menu.getMenNom());
                    item.setOutcome(menu.getMenXhtml());
                    subMenu.addElement(item);
                    entro = true;
                } else {
                    DefaultMenuItem item = new DefaultMenuItem(menu.getMenNom());
                    item.setOutcome(menu.getMenXhtml());
                    menuModel.addElement(item);
                }
            }
            if (entro) {
                menuModel.addElement(subMenu);
            }
            return menuModel;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return menuModel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        LoginController.usuario = usuario;
    }

    public MenuModel getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

    public Usuarios getModUser() {

        return modUser;
    }

    public void setModUser(Usuarios modUser) {
        this.modUser = modUser;
    }

}
