/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TO;

import entidades.Bloqueosplanificacion;
import entidades.Usuarios;
import java.util.List;

/**
 *
 * @author manu
 */
public class BloqueoTO {

    private Integer lunes;
    private Integer martes;
    private Integer miercoles;
    private Integer jueves;
    private Integer viernes;
    private Integer sabado;
  
    private Usuarios proId;
    private Bloqueosplanificacion bloqueo;

    public Usuarios getProId() {
        return proId;
    }

    public void setProId(Usuarios proId) {
        this.proId = proId;
    }

    public Integer getLunes() {
        return lunes;
    }

    public void setLunes(Integer lunes) {
        this.lunes = lunes;
    }

    public Integer getMartes() {
        return martes;
    }

    public void setMartes(Integer martes) {
        this.martes = martes;
    }

    public Integer getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(Integer miercoles) {
        this.miercoles = miercoles;
    }

    public Integer getJueves() {
        return jueves;
    }

    public void setJueves(Integer jueves) {
        this.jueves = jueves;
    }

    public Integer getViernes() {
        return viernes;
    }

    public void setViernes(Integer viernes) {
        this.viernes = viernes;
    }

    public Integer getSabado() {
        return sabado;
    }

    public void setSabado(Integer sabado) {
        this.sabado = sabado;
    }

    public Bloqueosplanificacion getBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(Bloqueosplanificacion bloqueo) {
        this.bloqueo = bloqueo;
    }

}
