/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TO;

import entidades.Agendacancelada;

/**
 *
 * @author manu
 */
public class AgendaCanceladaTO {

  
    private String fechaAgendada;
    private Agendacancelada cancelada;

    public String getFechaAgendada() {
        return fechaAgendada;
    }

    public void setFechaAgendada(String fechaAgendada) {
        this.fechaAgendada = fechaAgendada;
    }

    public Agendacancelada getCancelada() {
        return cancelada;
    }

    public void setCancelada(Agendacancelada cancelada) {
        this.cancelada = cancelada;
    }
}
