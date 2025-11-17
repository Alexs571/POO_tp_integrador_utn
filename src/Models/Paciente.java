package Models;

import java.time.LocalDate;


public class Paciente {
    private long id;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaNacimiento;
    private boolean eliminado;
    private HistoriaClinica historiaClinica;
    private GrupoSanguineo grupoSanguineo;

    
    //constructor vacio
     public Paciente() {}
    //  Constructor completo
    public Paciente(String nombre, String apellido, String dni, LocalDate fechaNacimiento,
                    HistoriaClinica historiaClinica, GrupoSanguineo grupoSanguineo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.historiaClinica = historiaClinica;
        this.grupoSanguineo = grupoSanguineo;
        this.eliminado = false;
    }
    // Constructor sobrecargado?

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
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
               Paciente:"
                + " ID: " + id
                + " Nombre: " + nombre 
                + " Apellido: " + apellido
                + " DNI: " + dni
                + " Fecha de nacimiento: " + fechaNacimiento
                + " Eliminado: " + eliminado
                + " Historia Clinica: " + historiaClinica
                + " Grupo Sanguineo: " + grupoSanguineo;
                """;                                                
    }
 }
