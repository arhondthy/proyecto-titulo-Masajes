/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author manu
 */
@Entity
@Table(name = "tipomasaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipomasaje.findAll", query = "SELECT t FROM Tipomasaje t"),
    @NamedQuery(name = "Tipomasaje.findByTipMasId", query = "SELECT t FROM Tipomasaje t WHERE t.tipMasId = :tipMasId"),
    @NamedQuery(name = "Tipomasaje.findByTipMasNom", query = "SELECT t FROM Tipomasaje t WHERE t.tipMasNom = :tipMasNom"),
    @NamedQuery(name = "Tipomasaje.findByTipMasCos", query = "SELECT t FROM Tipomasaje t WHERE t.tipMasCos = :tipMasCos"),
    @NamedQuery(name = "Tipomasaje.findByTipMasDes", query = "SELECT t FROM Tipomasaje t WHERE t.tipMasDes = :tipMasDes"),
    @NamedQuery(name = "Tipomasaje.findByTipMasEst", query = "SELECT t FROM Tipomasaje t WHERE t.tipMasEst = :tipMasEst"),
    @NamedQuery(name = "Tipomasaje.findByTipMasImg", query = "SELECT t FROM Tipomasaje t WHERE t.tipMasImg = :tipMasImg")})
public class Tipomasaje implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tip_mas_id")
    private Integer tipMasId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tip_mas_nom")
    private String tipMasNom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tip_mas_cos")
    private int tipMasCos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "tip_mas_des")
    private String tipMasDes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tip_mas_est")
    private int tipMasEst;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "tip_mas_img")
    private String tipMasImg;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comMasTipMasNom")
    private List<Compramasaje> compramasajeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masProIdMas")
    private List<Masajesprofesional> masajesprofesionalList;

    public Tipomasaje() {
    }

    public Tipomasaje(Integer tipMasId) {
        this.tipMasId = tipMasId;
    }

    public Tipomasaje(Integer tipMasId, String tipMasNom, int tipMasCos, String tipMasDes, int tipMasEst, String tipMasImg) {
        this.tipMasId = tipMasId;
        this.tipMasNom = tipMasNom;
        this.tipMasCos = tipMasCos;
        this.tipMasDes = tipMasDes;
        this.tipMasEst = tipMasEst;
        this.tipMasImg = tipMasImg;
    }

    public Integer getTipMasId() {
        return tipMasId;
    }

    public void setTipMasId(Integer tipMasId) {
        this.tipMasId = tipMasId;
    }

    public String getTipMasNom() {
        return tipMasNom;
    }

    public void setTipMasNom(String tipMasNom) {
        this.tipMasNom = tipMasNom;
    }

    public int getTipMasCos() {
        return tipMasCos;
    }

    public void setTipMasCos(int tipMasCos) {
        this.tipMasCos = tipMasCos;
    }

    public String getTipMasDes() {
        return tipMasDes;
    }

    public void setTipMasDes(String tipMasDes) {
        this.tipMasDes = tipMasDes;
    }

    public int getTipMasEst() {
        return tipMasEst;
    }

    public void setTipMasEst(int tipMasEst) {
        this.tipMasEst = tipMasEst;
    }

    public String getTipMasImg() {
        return tipMasImg;
    }

    public void setTipMasImg(String tipMasImg) {
        this.tipMasImg = tipMasImg;
    }

    @XmlTransient
    public List<Compramasaje> getCompramasajeList() {
        return compramasajeList;
    }

    public void setCompramasajeList(List<Compramasaje> compramasajeList) {
        this.compramasajeList = compramasajeList;
    }

    @XmlTransient
    public List<Masajesprofesional> getMasajesprofesionalList() {
        return masajesprofesionalList;
    }

    public void setMasajesprofesionalList(List<Masajesprofesional> masajesprofesionalList) {
        this.masajesprofesionalList = masajesprofesionalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipMasId != null ? tipMasId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipomasaje)) {
            return false;
        }
        Tipomasaje other = (Tipomasaje) object;
        if ((this.tipMasId == null && other.tipMasId != null) || (this.tipMasId != null && !this.tipMasId.equals(other.tipMasId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipMasNom;
    }
    
}
