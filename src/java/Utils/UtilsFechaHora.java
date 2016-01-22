/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author manu
 */
public class UtilsFechaHora {
    
    

    public static final Date HOY = new Date();
    public static final DateFormat DIA_MES_ANIO = new SimpleDateFormat("dd/MM/yyyy");
    public static final DateFormat HORA_DIA_MES_ANIO = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    public static final DateFormat HORA_DIA_MES_ANIO_COMPRA = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
    public static final DateFormat NOMBRE_DIA = new SimpleDateFormat("EEEE", new Locale("es"));
   
    public static final DateFormat DIA = new SimpleDateFormat("yyyy-M-d");
    

    public static final DateFormat HORA_MINUTO = new SimpleDateFormat("HH:mm");

    public static final DateFormat HORA = new SimpleDateFormat("HH");
    private Long diffDays;
    public static final Calendar calendar = Calendar.getInstance();
    public static final Calendar calendar2 = Calendar.getInstance();

    public Long getDiferenciaDias(Date inicio, Date fin) {

        calendar.setTime(inicio);
        calendar2.setTime(fin);

        long milis1 = calendar.getTimeInMillis();
        long milis2 = calendar2.getTimeInMillis();

        long diff = milis2 - milis1;

        return diffDays = (diff / (24 * 60 * 60 * 1000)) + 1;
    }

    public Long getDiffDays() {
        return diffDays;
    }

    public void setDiff(Long diffDays) {
        this.diffDays = diffDays;
    }
}
