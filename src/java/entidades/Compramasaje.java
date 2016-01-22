/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import Utils.UtilsFechaHora;
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
@Table(name = "compramasaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compramasaje.findAll", query = "SELECT c FROM Compramasaje c"),
    @NamedQuery(name = "Compramasaje.findByComMasId", query = "SELECT c FROM Compramasaje c WHERE c.comMasId = :comMasId"),
    @NamedQuery(name = "Compramasaje.findByComMasCliIdAndEstado", query = "SELECT c FROM Compramasaje c WHERE c.comMasCliId = :comMasCliId and c.comMasEst = :comMasEst"),
    @NamedQuery(name = "Compramasaje.findByProfesionalAndEstado", query = "SELECT c FROM Compramasaje c WHERE c.comMasProId = :comMasProId and c.comMasEst = :comMasEst"),
    @NamedQuery(name = "Compramasaje.findByEstado", query = "SELECT c FROM Compramasaje c WHERE c.comMasEst = :comMasEst"),
    @NamedQuery(name = "Compramasaje.findByComMasCliId", query = "SELECT c FROM Compramasaje c WHERE c.comMasCliId = :comMasCliId"),
     @NamedQuery(name = "Compramasaje.findByComMasProId", query = "SELECT c FROM Compramasaje c WHERE c.comMasProId = :comMasProId"),
    @NamedQuery(name = "Compramasaje.findByComMasCos", query = "SELECT c FROM Compramasaje c WHERE c.comMasCos = :comMasCos"),
    @NamedQuery(name = "Compramasaje.findByComMasHor", query = "SELECT c FROM Compramasaje c WHERE c.comMasHor = :comMasHor")})
public class Compramasaje implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "com_mas_id")
    private Integer comMasId;
   
    @Basic(optional = false)
    @NotNull
    @Column(name = "com_mas_cos")
    private int comMasCos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "com_mas_hor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date comMasHor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "com_mas_est")
    private String comMasEst;
    @JoinColumn(name = "com_mas_tip_mas_nom", referencedColumnName = "tip_mas_id")
    @ManyToOne(optional = false)
    private Tipomasaje comMasTipMasNom;
    @JoinColumn(name = "com_mas_pro_id", referencedColumnName = "usu_id")
    @ManyToOne(optional = false)
    private Usuarios comMasProId;
    @JoinColumn(name = "com_mas_cli_id", referencedColumnName = "usu_id")
    @ManyToOne(optional = false)
    private Usuarios comMasCliId;
    
   

    public Compramasaje() {
    }

    public Compramasaje(Integer comMasId) {
        this.comMasId = comMasId;
    }

    public Compramasaje(Integer comMasId, int comMasCos, Date comMasHor) {
        this.comMasId = comMasId;
        this.comMasCos = comMasCos;
        this.comMasHor = comMasHor;
    }

    public Integer getComMasId() {
        return comMasId;
    }

    public void setComMasId(Integer comMasId) {
        this.comMasId = comMasId;
    }

    public Usuarios getComMasCliId() {
        return comMasCliId;
    }

    public void setComMasCliId(Usuarios comMasCliId) {
        this.comMasCliId = comMasCliId;
    }

    public int getComMasCos() {
        return comMasCos;
    }

    public void setComMasCos(int comMasCos) {
        this.comMasCos = comMasCos;
    }

    public Date getComMasHor() {
        return comMasHor;
    }

    public void setComMasHor(Date comMasHor) {
        this.comMasHor = comMasHor;
    }

    public Tipomasaje getComMasTipMasNom() {
        return comMasTipMasNom;
    }

    public void setComMasTipMasNom(Tipomasaje comMasTipMasNom) {
        this.comMasTipMasNom = comMasTipMasNom;
    }

    public Usuarios getComMasProId() {
        return comMasProId;
    }

    public void setComMasProId(Usuarios comMasProId) {
        this.comMasProId = comMasProId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comMasId != null ? comMasId.hashCode() : 0);
        return hash;
    }
    
      public String getComMasEst() {
        return comMasEst;
    }

    public void setComMasEst(String comMasEst) {
        this.comMasEst = comMasEst;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compramasaje)) {
            return false;
        }
        Compramasaje other = (Compramasaje) object;
        if ((this.comMasId == null && other.comMasId != null) || (this.comMasId != null && !this.comMasId.equals(other.comMasId))) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "entidades.Compramasaje[ comMasId=" + comMasId + " ]";
    }

   

    
}
