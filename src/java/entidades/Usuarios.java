/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author manu
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByNomUsuAndPass", query = "SELECT u FROM Usuarios u WHERE u.usuNomUsu = :usuNomUsu and u.usuPas = :usuPas"),
    @NamedQuery(name = "Usuarios.findByUsuId", query = "SELECT u FROM Usuarios u WHERE u.usuId = :usuId"),
    @NamedQuery(name = "Usuarios.findByUsuNomUsu", query = "SELECT u FROM Usuarios u WHERE u.usuNomUsu = :usuNomUsu"),
    @NamedQuery(name = "Usuarios.findByUsuEst", query = "SELECT u FROM Usuarios u WHERE u.usuEst = :usuEst"),
    @NamedQuery(name = "Usuarios.findByUsuPas", query = "SELECT u FROM Usuarios u WHERE u.usuPas = :usuPas"),
    @NamedQuery(name = "Usuarios.findByUsuNom", query = "SELECT u FROM Usuarios u WHERE u.usuNom = :usuNom"),
    @NamedQuery(name = "Usuarios.findByUsuApe", query = "SELECT u FROM Usuarios u WHERE u.usuApe = :usuApe"),
    @NamedQuery(name = "Usuarios.findByUsuDir", query = "SELECT u FROM Usuarios u WHERE u.usuDir = :usuDir"),
    @NamedQuery(name = "Usuarios.findByUsuCom", query = "SELECT u FROM Usuarios u WHERE u.usuCom = :usuCom"),
    @NamedQuery(name = "Usuarios.findByUsuRut", query = "SELECT u FROM Usuarios u WHERE u.usuRut = :usuRut"),
    @NamedQuery(name = "Usuarios.findByUsuTel", query = "SELECT u FROM Usuarios u WHERE u.usuTel = :usuTel"),
    @NamedQuery(name = "Usuarios.findByUsuSex", query = "SELECT u FROM Usuarios u WHERE u.usuSex = :usuSex"),
    @NamedQuery(name = "Usuarios.findByUsuFec", query = "SELECT u FROM Usuarios u WHERE u.usuFec = :usuFec")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usu_id")
    private Integer usuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usu_nom_usu")
    private String usuNomUsu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usu_est")
    private int usuEst;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usu_pas")
    private String usuPas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usu_nom")
    private String usuNom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usu_ape")
    private String usuApe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usu_dir")
    private String usuDir;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usu_com")
    private String usuCom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usu_mail")
    private String usuMail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usu_rut")
    private String usuRut;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usu_tel")
    private String usuTel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usu_sex")
    private int usuSex;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usu_fec")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFec;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ratProId")
    private List<Rating> ratingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comuproProid")
    private List<Comunasprofesional> comunasprofesionalList;
    @JoinColumn(name = "usu_tip_usu_id", referencedColumnName = "tip_usu_id")
    @ManyToOne(optional = false)
    private Tipousuario usuTipUsuId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comMasProId")
    private List<Compramasaje> compramasajeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "masProIdPro")
    private List<Masajesprofesional> masajesprofesionalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plaUsuId")
    private List<Planificacionprofesional> planificacionprofesionalList;
    
    
  
    public Usuarios() {
    }

    public Usuarios(Integer usuId) {
        this.usuId = usuId;
    }

    public Usuarios(Integer usuId, String usuNomUsu, int usuEst, String usuPas, String usuNom, String usuApe, String usuDir, String usuCom, String usuRut, String usuTel, int usuSex, Date usuFec) {
        this.usuId = usuId;
        this.usuNomUsu = usuNomUsu;
        this.usuEst = usuEst;
        this.usuPas = usuPas;
        this.usuNom = usuNom;
        this.usuApe = usuApe;
        this.usuDir = usuDir;
        this.usuCom = usuCom;
        this.usuRut = usuRut;
        this.usuTel = usuTel;
        this.usuSex = usuSex;
        this.usuFec = usuFec;
    }

    public Integer getUsuId() {
        return usuId;
    }

    public void setUsuId(Integer usuId) {
        this.usuId = usuId;
    }

    public String getUsuNomUsu() {
        return usuNomUsu;
    }

    public void setUsuNomUsu(String usuNomUsu) {
        this.usuNomUsu = usuNomUsu;
    }

    public int getUsuEst() {
        return usuEst;
    }

    public void setUsuEst(int usuEst) {
        this.usuEst = usuEst;
    }

    public String getUsuPas() {
        return usuPas;
    }

    public void setUsuPas(String usuPas) {
        this.usuPas = usuPas;
    }

    public String getUsuNom() {
        return usuNom;
    }

    public void setUsuNom(String usuNom) {
        this.usuNom = usuNom;
    }

    public String getUsuApe() {
        return usuApe;
    }

    public void setUsuApe(String usuApe) {
        this.usuApe = usuApe;
    }

    public String getUsuDir() {
        return usuDir;
    }

    public void setUsuDir(String usuDir) {
        this.usuDir = usuDir;
    }

    public String getUsuCom() {
        return usuCom;
    }

    public void setUsuCom(String usuCom) {
        this.usuCom = usuCom;
    }

    public String getUsuRut() {
        return usuRut;
    }

    public void setUsuRut(String usuRut) {
        this.usuRut = usuRut;
    }

    public String getUsuTel() {
        return usuTel;
    }

    public void setUsuTel(String usuTel) {
        this.usuTel = usuTel;
    }

    public int getUsuSex() {
        return usuSex;
    }

    public void setUsuSex(int usuSex) {
        this.usuSex = usuSex;
    }

    public Date getUsuFec() {
        return usuFec;
    }

    public void setUsuFec(Date usuFec) {
        this.usuFec = usuFec;
    }

    @XmlTransient
    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    @XmlTransient
    public List<Comunasprofesional> getComunasprofesionalList() {
        return comunasprofesionalList;
    }

    public void setComunasprofesionalList(List<Comunasprofesional> comunasprofesionalList) {
        this.comunasprofesionalList = comunasprofesionalList;
    }

    public Tipousuario getUsuTipUsuId() {
        return usuTipUsuId;
    }

    public void setUsuTipUsuId(Tipousuario usuTipUsuId) {
        this.usuTipUsuId = usuTipUsuId;
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

    @XmlTransient
    public List<Planificacionprofesional> getPlanificacionprofesionalList() {
        return planificacionprofesionalList;
    }

    public void setPlanificacionprofesionalList(List<Planificacionprofesional> planificacionprofesionalList) {
        this.planificacionprofesionalList = planificacionprofesionalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuId != null ? usuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.usuId == null && other.usuId != null) || (this.usuId != null && !this.usuId.equals(other.usuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return usuNom +" "+usuApe;
    }


    public String nombApellido() {
         return  usuNom +" "+usuApe;
    }

    public String getUsuMail() {
        return usuMail;
    }

    public void setUsuMail(String usuMail) {
        this.usuMail = usuMail;
    }

}
