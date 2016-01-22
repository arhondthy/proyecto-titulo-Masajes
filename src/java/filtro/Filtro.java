/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import Controller.LoginController;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author manu
 */
@WebFilter("*.xhtml")
public class Filtro implements Filter {

    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
        String requestUrl = req.getRequestURL().toString();
        if (LoginController.usuario != null) {
            String tipo = LoginController.usuario.getUsuTipUsuId().getTipUsuNom();
            switch (tipo) {
                case "admin":
                    if (requestUrl.contains("/Cliente/") || requestUrl.contains("/profesional/")|| requestUrl.contains("/Ingreso")) {
                        res.sendRedirect(req.getContextPath() + "/faces/menuComun/Home.xhtml");
                    } else {
                        chain.doFilter(request, response);
                    }
                    break;
                case "cliente":
                    if (requestUrl.contains("/Cliente/") || requestUrl.contains("/menuComun/")|| requestUrl.contains("/Ingreso")) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect(req.getContextPath() + "/faces/menuComun/Home.xhtml");
                    }
                    break;
                case "profesional":
                    if (requestUrl.contains("profesional/")|| requestUrl.contains("/menuComun/")|| requestUrl.contains("/Ingreso")) {
                        chain.doFilter(request, response);
                    } else {
                         res.sendRedirect(req.getContextPath() + "/faces/menuComun/Home.xhtml");
                    }
                    break;
            }
        } else {
            if (!requestUrl.contains("/menuComun/")) {
                res.sendRedirect(req.getContextPath() + "/faces/menuComun/Home.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

}
