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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manu
 */
@Entity
@Table(name = "comunasprofesional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comunasprofesional.findAll", query = "SELECT c FROM Comunasprofesional c"),
    @NamedQuery(name = "Comunasprofesional.findByIdComupro", query = "SELECT c FROM Comunasprofesional c WHERE c.idComupro = :idComupro")})
public class Comunasprofesional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comupro")
    private Integer idComupro;
    @JoinColumn(name = "comupro_comu", referencedColumnName = "com_id")
    @ManyToOne(optional = false)
    private Comuna comuproComu;
    @JoinColumn(name = "comupro_proid", referencedColumnName = "usu_id")
    @ManyToOne(optional = false)
    private Usuarios comuproProid;

    public Comunasprofesional() {
    }

    public Comunasprofesional(Integer idComupro) {
        this.idComupro = idComupro;
    }

    public Integer getIdComupro() {
        return idComupro;
    }

    public void setIdComupro(Integer idComupro) {
        this.idComupro = idComupro;
    }

    public Comuna getComuproComu() {
        return comuproComu;
    }

    public void setComuproComu(Comuna comuproComu) {
        this.comuproComu = comuproComu;
    }

    public Usuarios getComuproProid() {
        return comuproProid;
    }

    public void setComuproProid(Usuarios comuproProid) {
        this.comuproProid = comuproProid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComupro != null ? idComupro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comunasprofesional)) {
            return false;
        }
        Comunasprofesional other = (Comunasprofesional) object;
        if ((this.idComupro == null && other.idComupro != null) || (this.idComupro != null && !this.idComupro.equals(other.idComupro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Comunasprofesional[ idComupro=" + idComupro + " ]";
    }
    
}
