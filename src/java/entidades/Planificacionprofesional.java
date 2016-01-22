/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manu
 */
@Entity
@Table(name = "planificacionprofesional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planificacionprofesional.findAll", query = "SELECT p FROM Planificacionprofesional p"),
    @NamedQuery(name = "Planificacionprofesional.findByIdplaPro", query = "SELECT p FROM Planificacionprofesional p WHERE p.idplaPro = :idplaPro"),
     @NamedQuery(name = "Planificacionprofesional.findByPlaUsuId", query = "SELECT p FROM Planificacionprofesional p WHERE p.plaUsuId = :plaUsuId"),
    @NamedQuery(name = "Planificacionprofesional.findByPlaDiaHorIni", query = "SELECT p FROM Planificacionprofesional p WHERE p.plaDiaHorIni = :plaDiaHorIni"),
    @NamedQuery(name = "Planificacionprofesional.findByPlaDiaHorFin", query = "SELECT p FROM Planificacionprofesional p WHERE p.plaDiaHorFin = :plaDiaHorFin"),
    @NamedQuery(name = "Planificacionprofesional.findByPlaHor", query = "SELECT p FROM Planificacionprofesional p WHERE p.plaHor = :plaHor")})
public class Planificacionprofesional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_plaPro")
    private Integer idplaPro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pla_dia_hor_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date plaDiaHorIni;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pla_dia_hor_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date plaDiaHorFin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pla_hor")
    private int plaHor;
    @JoinColumn(name = "pla_usu_id", referencedColumnName = "usu_id")
    @ManyToOne(optional = false)
    private Usuarios plaUsuId;

    public Planificacionprofesional() {
    }

    public Planificacionprofesional(Integer idplaPro) {
        this.idplaPro = idplaPro;
    }

    public Planificacionprofesional(Integer idplaPro, Date plaDiaHorIni, Date plaDiaHorFin, int plaHor) {
        this.idplaPro = idplaPro;
        this.plaDiaHorIni = plaDiaHorIni;
        this.plaDiaHorFin = plaDiaHorFin;
        this.plaHor = plaHor;
    }

    public Integer getIdplaPro() {
        return idplaPro;
    }

    public void setIdplaPro(Integer idplaPro) {
        this.idplaPro = idplaPro;
    }

    public Date getPlaDiaHorIni() {
        return plaDiaHorIni;
    }

    public void setPlaDiaHorIni(Date plaDiaHorIni) {
        this.plaDiaHorIni = plaDiaHorIni;
    }

    public Date getPlaDiaHorFin() {
        return plaDiaHorFin;
    }

    public void setPlaDiaHorFin(Date plaDiaHorFin) {
        this.plaDiaHorFin = plaDiaHorFin;
    }

    public int getPlaHor() {
        return plaHor;
    }

    public void setPlaHor(int plaHor) {
        this.plaHor = plaHor;
    }

    public Usuarios getPlaUsuId() {
        return plaUsuId;
    }

    public void setPlaUsuId(Usuarios plaUsuId) {
        this.plaUsuId = plaUsuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idplaPro != null ? idplaPro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planificacionprofesional)) {
            return false;
        }
        Planificacionprofesional other = (Planificacionprofesional) object;
        if ((this.idplaPro == null && other.idplaPro != null) || (this.idplaPro != null && !this.idplaPro.equals(other.idplaPro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Planificacionprofesional[ idplaPro=" + idplaPro + " ]";
    }
    
}
