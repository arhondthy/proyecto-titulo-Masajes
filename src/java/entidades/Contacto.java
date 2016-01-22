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
@Table(name = "contacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contacto.findAll", query = "SELECT c FROM Contacto c"),
    @NamedQuery(name = "Contacto.findByConId", query = "SELECT c FROM Contacto c WHERE c.conId = :conId"),
    @NamedQuery(name = "Contacto.findByConNom", query = "SELECT c FROM Contacto c WHERE c.conNom = :conNom"),
    @NamedQuery(name = "Contacto.findByConMail", query = "SELECT c FROM Contacto c WHERE c.conMail = :conMail"),
    @NamedQuery(name = "Contacto.findByConMsj", query = "SELECT c FROM Contacto c WHERE c.conMsj = :conMsj")})
public class Contacto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "con_id")
    private Integer conId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "con_nom")
    private String conNom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "con_mail")
    private String conMail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "con_msj")
    private String conMsj;

    public Contacto() {
    }

    public Contacto(Integer conId) {
        this.conId = conId;
    }

    public Contacto(Integer conId, String conNom, String conMail, String conMsj) {
        this.conId = conId;
        this.conNom = conNom;
        this.conMail = conMail;
        this.conMsj = conMsj;
    }

    public Integer getConId() {
        return conId;
    }

    public void setConId(Integer conId) {
        this.conId = conId;
    }

    public String getConNom() {
        return conNom;
    }

    public void setConNom(String conNom) {
        this.conNom = conNom;
    }

    public String getConMail() {
        return conMail;
    }

    public void setConMail(String conMail) {
        this.conMail = conMail;
    }

    public String getConMsj() {
        return conMsj;
    }

    public void setConMsj(String conMsj) {
        this.conMsj = conMsj;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conId != null ? conId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contacto)) {
            return false;
        }
        Contacto other = (Contacto) object;
        if ((this.conId == null && other.conId != null) || (this.conId != null && !this.conId.equals(other.conId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Contacto[ conId=" + conId + " ]";
    }
    
}
