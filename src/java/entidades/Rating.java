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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manu
 */
@Entity
@Table(name = "rating")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rating.findAll", query = "SELECT r FROM Rating r"),
    @NamedQuery(name = "Rating.findByRatId", query = "SELECT r FROM Rating r WHERE r.ratId = :ratId"),
    @NamedQuery(name = "Rating.findByProId", query = "SELECT r FROM Rating r WHERE r.ratProId = :ratProId"),
    @NamedQuery(name = "Rating.findByRatRat", query = "SELECT r FROM Rating r WHERE r.ratRat = :ratRat")})
public class Rating implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rat_id")
    private Integer ratId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rat_rat")
    private int ratRat;
    @JoinColumn(name = "rat_pro_id", referencedColumnName = "usu_id")
    @ManyToOne(optional = false)
    private Usuarios ratProId;

    public Rating() {
    }

    public Rating(Integer ratId) {
        this.ratId = ratId;
    }

    public Rating(Integer ratId, int ratRat) {
        this.ratId = ratId;
        this.ratRat = ratRat;
    }

    public Integer getRatId() {
        return ratId;
    }

    public void setRatId(Integer ratId) {
        this.ratId = ratId;
    }

    public int getRatRat() {
        return ratRat;
    }

    public void setRatRat(int ratRat) {
        this.ratRat = ratRat;
    }

    public Usuarios getRatProId() {
        return ratProId;
    }

    public void setRatProId(Usuarios ratProId) {
        this.ratProId = ratProId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ratId != null ? ratId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rating)) {
            return false;
        }
        Rating other = (Rating) object;
        if ((this.ratId == null && other.ratId != null) || (this.ratId != null && !this.ratId.equals(other.ratId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Rating[ ratId=" + ratId + " ]";
    }
    
}
