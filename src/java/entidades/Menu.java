/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manu
 */
@Entity
@Table(name = "menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
    @NamedQuery(name = "Menu.findByMenId", query = "SELECT m FROM Menu m WHERE m.menId = :menId"),
    @NamedQuery(name = "Menu.findByMenNom", query = "SELECT m FROM Menu m WHERE m.menNom = :menNom"),
      @NamedQuery(name = "Menu.findBytipUsu", query = "SELECT m FROM Menu m WHERE m.mentipoUserid = :mentipoUserid"),
    @NamedQuery(name = "Menu.findByMenXhtml", query = "SELECT m FROM Menu m WHERE m.menXhtml = :menXhtml")})
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "men_id")
    private Integer menId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "men_nom")
    private String menNom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "men_xhtml")
    private String menXhtml;
    @JoinColumn(name = "men_tipoUser_id", referencedColumnName = "tip_usu_id")
    @ManyToOne(optional = false)
    private Tipousuario mentipoUserid;

    public Menu() {
    }

    public Menu(Integer menId) {
        this.menId = menId;
    }

    public Menu(Integer menId, String menNom, String menXhtml) {
        this.menId = menId;
        this.menNom = menNom;
        this.menXhtml = menXhtml;
    }

    public Integer getMenId() {
        return menId;
    }

    public void setMenId(Integer menId) {
        this.menId = menId;
    }

    public String getMenNom() {
        return menNom;
    }

    public void setMenNom(String menNom) {
        this.menNom = menNom;
    }

    public String getMenXhtml() {
        return menXhtml;
    }

    public void setMenXhtml(String menXhtml) {
        this.menXhtml = menXhtml;
    }

    public Tipousuario getMentipoUserid() {
        return mentipoUserid;
    }

    public void setMentipoUserid(Tipousuario mentipoUserid) {
        this.mentipoUserid = mentipoUserid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menId != null ? menId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.menId == null && other.menId != null) || (this.menId != null && !this.menId.equals(other.menId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return menNom;
    }
    
}
