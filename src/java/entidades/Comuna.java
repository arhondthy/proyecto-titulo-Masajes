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
@Table(name = "comuna")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comuna.findAll", query = "SELECT c FROM Comuna c"),
    @NamedQuery(name = "Comuna.findByComId", query = "SELECT c FROM Comuna c WHERE c.comId = :comId"),
    @NamedQuery(name = "Comuna.findByComNom", query = "SELECT c FROM Comuna c WHERE c.comNom = :comNom")})
public class Comuna implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "com_id")
    private Integer comId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "com_nom")
    private String comNom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comuproComu")
    private List<Comunasprofesional> comunasprofesionalList;

    public Comuna() {
    }

    public Comuna(Integer comId) {
        this.comId = comId;
    }

    public Comuna(Integer comId, String comNom) {
        this.comId = comId;
        this.comNom = comNom;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public String getComNom() {
        return comNom;
    }

    public void setComNom(String comNom) {
        this.comNom = comNom;
    }

    @XmlTransient
    public List<Comunasprofesional> getComunasprofesionalList() {
        return comunasprofesionalList;
    }

    public void setComunasprofesionalList(List<Comunasprofesional> comunasprofesionalList) {
        this.comunasprofesionalList = comunasprofesionalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comId != null ? comId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comuna)) {
            return false;
        }
        Comuna other = (Comuna) object;
        if ((this.comId == null && other.comId != null) || (this.comId != null && !this.comId.equals(other.comId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return comNom;
    }
    
}
