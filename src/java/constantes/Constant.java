/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constantes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author manu
 */
public class Constant {

    public static final Integer TIPO_USUARIO_CLIENTE = 2;
    public static final Integer CONTADOR_HORAS = 0;
    public static final Integer TIPO_USUARIO_PROFESIONAL = 3;
    public static final Integer TIPO_USUARIO_ADMIN = 1;
    public static final String MASAJE_AGENDADO = "Agenda de masaje solicitada exitosamente";
    public static final String ESTADO_AGENDADA = "Agendada";
    public static final String ESTADO_EN_CURSO = "En curso";
    public static final String ESTADO_ATENDIDA = "Atendida";
    public static final String ESTADO_TERMINADA = "Terminada";
    public static final Integer ESTADO_HABILITADO = 0;
    public static final Integer ESTADO_DESHABILITADO = 1;
    public static final String REGISTRO_GUARDADO = "Se a creado usuario correctamente";
    public static final String MASAJE_ERROR = "Error interno, no se pudo guardar";
    public static final String LOGEARSE = "Debe iniciar sesion para poder solicitar un masaje";
    public static final String LOGEARSE_CLIENTE = "Debe ser Cliente para solicitar un masaje";
    public static final String CONTACTO_EXITO = "su mensaje se envio correctamente";
    public static final String CONTACTO_ERROR = "Error interno, no se pudo enviar mensaje";
    public static final String DIRECTORIO = "C:\\boucher";
    public static final Integer CENTRADO = 1;
    public static final Integer NUMERO_COLUMNAS = 2;
    public static final String PLANIFICACION = "Se carga planificacion de profesional ";
    public static final String PLANIFICACION_VACIA = "No existe planificacion para profesional ";
    public static final String PLANI_HORAS = "Se cargan horas disponibles ";
    public static final String PLANI_SIN_HORAS = "No hay mas horas disponibles para el dia seleccionado ";
    public static final ArrayList<String> HORAS = new ArrayList<String>(Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "15:00", "16:00", "17:00"));
    public static final String RATING_SELECCIONADO = "Ha calificado con un: ";
    public static final String RATING_CANCELADO = "calificacion en cero";
    public static final String RATING_CALIFICAR = "Calificar";
    public static final String RATING_CANCELAR = "Cancelar";
    public static final Integer DOMINGO = 1;
    public static final Integer RATING_ONE = 1;
    public static final Integer RATING_TWO = 2;
    public static final Integer RATING_THREE = 3;
    public static final Integer RATING_FOUR = 4;
    public static final Integer RATING_FIVE = 5;
    public static final Integer RATING = 5;
    public static final Integer PERCENT = 100;
    public static final Integer VEINTE_PERCENT = 20;
    public static final Integer CUARENTA_PERCENT = 40;
    public static final Integer SESENTA_PERCENT = 60;
    public static final Integer OCHENTA_PERCENT = 80;

}
