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
@Table(name = "masajesprofesional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Masajesprofesional.findAll", query = "SELECT m FROM Masajesprofesional m"),
    @NamedQuery(name = "Masajesprofesional.findByMasProId", query = "SELECT m FROM Masajesprofesional m WHERE m.masProId = :masProId"),
     @NamedQuery(name = "Masajesprofesional.findByMasProIdMas", query = "SELECT m FROM Masajesprofesional m WHERE m.masProIdMas = :masProIdMas"),
      @NamedQuery(name = "Masajesprofesional.findByMasProIdUsu", query = "SELECT m FROM Masajesprofesional m WHERE m.masProIdPro = :masProIdPro")})
   
public class Masajesprofesional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mas_pro_id")
    private Integer masProId;
    @JoinColumn(name = "mas_pro_id_mas", referencedColumnName = "tip_mas_id")
    @ManyToOne(optional = false)
    private Tipomasaje masProIdMas;
    @JoinColumn(name = "mas_pro_id_pro", referencedColumnName = "usu_id")
    @ManyToOne(optional = false)
    private Usuarios masProIdPro;

    public Masajesprofesional() {
    }

    public Masajesprofesional(Integer masProId) {
        this.masProId = masProId;
    }

    public Integer getMasProId() {
        return masProId;
    }

    public void setMasProId(Integer masProId) {
        this.masProId = masProId;
    }

 

    public Tipomasaje getMasProIdMas() {
        return masProIdMas;
    }

    public void setMasProIdMas(Tipomasaje masProIdMas) {
        this.masProIdMas = masProIdMas;
    }

    public Usuarios getMasProIdPro() {
        return masProIdPro;
    }

    public void setMasProIdPro(Usuarios masProIdPro) {
        this.masProIdPro = masProIdPro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (masProId != null ? masProId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Masajesprofesional)) {
            return false;
        }
        Masajesprofesional other = (Masajesprofesional) object;
        if ((this.masProId == null && other.masProId != null) || (this.masProId != null && !this.masProId.equals(other.masProId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Masajesprofesional[ masProId=" + masProId + " ]";
    }
    
}
