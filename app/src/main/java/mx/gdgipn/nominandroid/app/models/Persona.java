package mx.gdgipn.nominandroid.app.models;

/**
 * Created by thespianartist on 4/12/14.
 */
public class Persona {

    private  String nombre;
    private  String salarioDiario;
    private  String diasLaborados;
    private  String sueldo;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSalarioDiario() {
        return salarioDiario;
    }

    public void setSalarioDiario(String salarioDiario) {
        this.salarioDiario = "$"+salarioDiario+" por dia";
    }

    public String getDiasLaborados() {
        return diasLaborados;
    }

    public void setDiasLaborados(String diasLaborados) {
        this.diasLaborados = diasLaborados+" dias trabajados";
    }

    public String getSueldo() {
        return sueldo;
    }

    public void setSueldo(String sueldo) {
        this.sueldo = "Sueldo Total $"+sueldo;
    }


}
