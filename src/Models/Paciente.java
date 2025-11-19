package Models;

import java.time.LocalDate;


public class Paciente extends Base {
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaNacimiento;
    private HistoriaClinica historiaClinica;
    private GrupoSanguineo grupoSanguineo;

    
    //constructor vacio
     public Paciente() {
     super();
     }

    public Paciente(String nombre, String apellido, String dni, LocalDate fechaNacimiento, HistoriaClinica historiaClinica, GrupoSanguineo grupoSanguineo, Long id, boolean eliminado) {
        super(id, eliminado);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.historiaClinica = historiaClinica;
        this.grupoSanguineo = grupoSanguineo;
    }
        
    // Constructor sobrecargado?

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    public GrupoSanguineo getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }
    //toString ()
    @Override
    public String toString() {
        return """
                Paciente:
                ID: """ + getId() + """
                Nombre: """ + nombre + """
                Apellido: """ +  apellido + """
                DNI: """ +  dni + """
                Fecha de nacimiento: """ + fechaNacimiento + """
                Historia Clinica: """ + historiaClinica + """
                Grupo Sanguineo: """ +  grupoSanguineo + """
                Eliminado: """ + isEliminado() + """
                """;
    }
 }
