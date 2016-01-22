/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import TO.BloqueoTO;
import constantes.Constant;
import entidades.Bloqueosplanificacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manu
 */
public class CargarBloqueoTO {

    private BloqueoTO bloq;

    public BloqueoTO cargarTO(List<Bloqueosplanificacion> bloqPro) {
        BloqueoTO bto = new BloqueoTO();
        if (!bloqPro.isEmpty()) {
            for (Bloqueosplanificacion x : bloqPro) {
                bto.setBloqueo(x);
                bto.setProId(x.getBloProId());
                String[] diasArray = x.getBloDia().split(";");
                for (String dias : diasArray) {
                    switch (dias) {
                        case "1":
                            bto.setLunes(2);
                            break;
                        case "2":
                            bto.setMartes(3);
                            break;
                        case "3":
                            bto.setMiercoles(4);
                            break;
                        case "4":
                            bto.setJueves(5);
                            break;
                        case "5":
                            bto.setViernes(6);
                            break;
                        case "6":
                            bto.setSabado(7);
                            break;
                    }
                }
                bloq = bto;
            }
        }
        return bloq;

    }

    public List<Integer> getListaDias(BloqueoTO bloqueoTO) {
        List<Integer> dias = new ArrayList<>();
        dias.add(Constant.DOMINGO);
        if (bloqueoTO.getLunes() != null) {
            dias.add(bloqueoTO.getLunes());
        }
        if (bloqueoTO.getMartes() != null) {
            dias.add(bloqueoTO.getMartes());
        }
        if (bloqueoTO.getMiercoles() != null) {
            dias.add(bloqueoTO.getMiercoles());
        }
        if (bloqueoTO.getJueves() != null) {
            dias.add(bloqueoTO.getJueves());
        }
        if (bloqueoTO.getViernes() != null) {
            dias.add(bloqueoTO.getViernes());
        }
        if (bloqueoTO.getSabado() != null) {
            dias.add(bloqueoTO.getSabado());
        }
        return dias;
    }

    public BloqueoTO getBloq() {
        return bloq;
    }

    public void setBloq(BloqueoTO bloq) {
        this.bloq = bloq;
    }
}
