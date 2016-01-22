/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TO;

import Utils.UtilsFechaHora;
import entidades.Compramasaje;
import java.util.List;

/**
 *
 * @author manu
 */
public class CompraMasajeTO {
    
    private Compramasaje listaMasajes;
    private String fechaMasaje;
    
    public Compramasaje getListaMasajes() {
        return listaMasajes;
    }
    
    public void setListaMasajes(Compramasaje listaMasajes) {
        this.listaMasajes = listaMasajes;
    }
    
    public String getFechaMasaje() {
        return fechaMasaje;
    }
    
    public void setFechaMasaje(String fechaMasaje) {
        this.fechaMasaje = fechaMasaje;
    }
    
    public String TOString() {
        String CompraMasajeString = "nombre de paciente: " + listaMasajes.getComMasCliId().getUsuNom() + " " + listaMasajes.getComMasCliId().getUsuApe() + "\n"
                + "Nombre de profesional: " + listaMasajes.getComMasProId().getUsuNom() + " " + listaMasajes.getComMasProId().getUsuApe() + "\n"
                + "Tipo de masaje: " + listaMasajes.getComMasTipMasNom().getTipMasNom() + "\n"
                + "Fecha de masaje" + UtilsFechaHora.HORA_DIA_MES_ANIO.format(listaMasajes.getComMasHor());
        return CompraMasajeString;
    }
    
}
