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
@Table(name = "bloqueosplanificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bloqueosplanificacion.findAll", query = "SELECT b FROM Bloqueosplanificacion b"),
    @NamedQuery(name = "Bloqueosplanificacion.findByBloId", query = "SELECT b FROM Bloqueosplanificacion b WHERE b.bloId = :bloId"),
    @NamedQuery(name = "Bloqueosplanificacion.findByPro", query = "SELECT b FROM Bloqueosplanificacion b WHERE b.bloProId = :bloProId"),
    @NamedQuery(name = "Bloqueosplanificacion.findByBloDia", query = "SELECT b FROM Bloqueosplanificacion b WHERE b.bloDia = :bloDia")})
public class Bloqueosplanificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "blo_id")
    private Integer bloId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "blo_dia")
    private String bloDia;
    @JoinColumn(name = "blo_pro_id", referencedColumnName = "usu_id")
    @ManyToOne(optional = false)
    private Usuarios bloProId;

    public Bloqueosplanificacion() {
    }

    public Bloqueosplanificacion(Integer bloId) {
        this.bloId = bloId;
    }

    public Bloqueosplanificacion(Integer bloId, String bloDia) {
        this.bloId = bloId;
        this.bloDia = bloDia;
    }

    public Integer getBloId() {
        return bloId;
    }

    public void setBloId(Integer bloId) {
        this.bloId = bloId;
    }

    public String getBloDia() {
        return bloDia;
    }

    public void setBloDia(String bloDia) {
        this.bloDia = bloDia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bloId != null ? bloId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bloqueosplanificacion)) {
            return false;
        }
        Bloqueosplanificacion other = (Bloqueosplanificacion) object;
        if ((this.bloId == null && other.bloId != null) || (this.bloId != null && !this.bloId.equals(other.bloId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Bloqueosplanificacion[ bloId=" + bloId + " ]";
    }

    public Usuarios getBloProId() {
        return bloProId;
    }

    public void setBloProId(Usuarios bloProId) {
        this.bloProId = bloProId;
    }

}
