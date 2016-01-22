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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manu
 */
@Entity
@Table(name = "agendacancelada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agendacancelada.findAll", query = "SELECT a FROM Agendacancelada a"),
    @NamedQuery(name = "Agendacancelada.findByAgeCanId", query = "SELECT a FROM Agendacancelada a WHERE a.ageCanId = :ageCanId"),
    @NamedQuery(name = "Agendacancelada.findByAgeCanComId", query = "SELECT a FROM Agendacancelada a WHERE a.ageCanComId = :ageCanComId"),
    @NamedQuery(name = "Agendacancelada.findByAgeCanComCliId", query = "SELECT a FROM Agendacancelada a WHERE a.ageCanComCliId = :ageCanComCliId"),
    @NamedQuery(name = "Agendacancelada.findByAgeCanComCos", query = "SELECT a FROM Agendacancelada a WHERE a.ageCanComCos = :ageCanComCos"),
    @NamedQuery(name = "Agendacancelada.findByAgeCanComHor", query = "SELECT a FROM Agendacancelada a WHERE a.ageCanComHor = :ageCanComHor"),
    @NamedQuery(name = "Agendacancelada.findByAgeCanComEst", query = "SELECT a FROM Agendacancelada a WHERE a.ageCanComEst = :ageCanComEst")})
public class Agendacancelada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "age_can_id")
    private Integer ageCanId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "age_can_com_id")
    private int ageCanComId;
  
    @Basic(optional = false)
    @NotNull
    @Column(name = "age_can_com_cos")
    private int ageCanComCos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "age_can_com_hor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ageCanComHor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "age_can_com_est")
    private String ageCanComEst;
    @JoinColumn(name = "age_can_com_tip_mas", referencedColumnName = "tip_mas_id")
    @ManyToOne(optional = false)
    private Tipomasaje ageCanComTipMas;
    @JoinColumn(name = "age_can_com_pro", referencedColumnName = "usu_id")
    @ManyToOne(optional = false)
    private Usuarios ageCanComPro;
    @JoinColumn(name = "age_can_com_cli_id", referencedColumnName = "usu_id")
    @ManyToOne(optional = false)
    private Usuarios ageCanComCliId;
     @JoinColumn(name = "age_can_usu_can_id", referencedColumnName = "usu_id")
    @ManyToOne(optional = false)
    private Usuarios ageCanComUsuCanId;

    public Agendacancelada() {
    }

    public Agendacancelada(Integer ageCanId) {
        this.ageCanId = ageCanId;
    }

    public Agendacancelada(Integer ageCanId, int ageCanComId, Usuarios ageCanComCliId, int ageCanComCos, Date ageCanComHor, String ageCanComEst) {
        this.ageCanId = ageCanId;
        this.ageCanComId = ageCanComId;
        this.ageCanComCliId = ageCanComCliId;
        this.ageCanComCos = ageCanComCos;
        this.ageCanComHor = ageCanComHor;
        this.ageCanComEst = ageCanComEst;
    }

    public Integer getAgeCanId() {
        return ageCanId;
    }

    public void setAgeCanId(Integer ageCanId) {
        this.ageCanId = ageCanId;
    }

    public int getAgeCanComId() {
        return ageCanComId;
    }

    public void setAgeCanComId(int ageCanComId) {
        this.ageCanComId = ageCanComId;
    }

    public Usuarios getAgeCanComCliId() {
        return ageCanComCliId;
    }

    public void setAgeCanComCliId(Usuarios ageCanComCliId) {
        this.ageCanComCliId = ageCanComCliId;
    }

    public int getAgeCanComCos() {
        return ageCanComCos;
    }

    public void setAgeCanComCos(int ageCanComCos) {
        this.ageCanComCos = ageCanComCos;
    }

    public Date getAgeCanComHor() {
        return ageCanComHor;
    }

    public void setAgeCanComHor(Date ageCanComHor) {
        this.ageCanComHor = ageCanComHor;
    }

    public String getAgeCanComEst() {
        return ageCanComEst;
    }

    public void setAgeCanComEst(String ageCanComEst) {
        this.ageCanComEst = ageCanComEst;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ageCanId != null ? ageCanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agendacancelada)) {
            return false;
        }
        Agendacancelada other = (Agendacancelada) object;
        if ((this.ageCanId == null && other.ageCanId != null) || (this.ageCanId != null && !this.ageCanId.equals(other.ageCanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Agendacancelada[ ageCanId=" + ageCanId + " ]";
    }

    public Tipomasaje getAgeCanComTipMas() {
        return ageCanComTipMas;
    }

    public void setAgeCanComTipMas(Tipomasaje ageCanComTipMas) {
        this.ageCanComTipMas = ageCanComTipMas;
    }

    public Usuarios getAgeCanComPro() {
        return ageCanComPro;
    }

    public void setAgeCanComPro(Usuarios ageCanComPro) {
        this.ageCanComPro = ageCanComPro;
    }

    public Usuarios getAgeCanComUsuCanId() {
        return ageCanComUsuCanId;
    }

    public void setAgeCanComUsuCanId(Usuarios ageCanComUsuCanId) {
        this.ageCanComUsuCanId = ageCanComUsuCanId;
    }

}
