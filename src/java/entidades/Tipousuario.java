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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tipousuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipousuario.findAll", query = "SELECT t FROM Tipousuario t"),
    @NamedQuery(name = "Tipousuario.findByTipUsuId", query = "SELECT t FROM Tipousuario t WHERE t.tipUsuId = :tipUsuId"),
    @NamedQuery(name = "Tipousuario.findByTipUsuNom", query = "SELECT t FROM Tipousuario t WHERE t.tipUsuNom = :tipUsuNom")})
public class Tipousuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tip_usu_id")
    private Integer tipUsuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tip_usu_nom")
    private String tipUsuNom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mentipoUserid")
    private List<Menu> menuList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuTipUsuId")
    private List<Usuarios> usuariosList;
   @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tip_usu_est")
    private String tipUsuEst;

    public Tipousuario() {
    }

    public Tipousuario(Integer tipUsuId) {
        this.tipUsuId = tipUsuId;
    }

    public Tipousuario(Integer tipUsuId, String tipUsuNom) {
        this.tipUsuId = tipUsuId;
        this.tipUsuNom = tipUsuNom;
    }

    public Integer getTipUsuId() {
        return tipUsuId;
    }

    public void setTipUsuId(Integer tipUsuId) {
        this.tipUsuId = tipUsuId;
    }

    public String getTipUsuNom() {
        return tipUsuNom;
    }

    public void setTipUsuNom(String tipUsuNom) {
        this.tipUsuNom = tipUsuNom;
    }

    @XmlTransient
    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    public String getTipUsuEst() {
        return tipUsuEst;
    }

    public void setTipUsuEst(String tipUsuEst) {
        this.tipUsuEst = tipUsuEst;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipUsuId != null ? tipUsuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipousuario)) {
            return false;
        }
        Tipousuario other = (Tipousuario) object;
        if ((this.tipUsuId == null && other.tipUsuId != null) || (this.tipUsuId != null && !this.tipUsuId.equals(other.tipUsuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipUsuNom;
    }
    
}
