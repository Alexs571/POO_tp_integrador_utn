
import Models.GrupoSanguineo;
import Models.HistoriaClinica;
import Models.Paciente;

import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        
        
         // Historias clínicas
        HistoriaClinica hc1 = new HistoriaClinica("HC-0001", GrupoSanguineo.O_POS,
                "Asma leve", "Salbutamol", "Control anual");
        HistoriaClinica hc2 = new HistoriaClinica("HC-0002", GrupoSanguineo.A_NEG,
                "Hipertensión", "Enalapril", "Dieta hiposódica");
        HistoriaClinica hc3 = new HistoriaClinica("HC-0003", GrupoSanguineo.B_NEG,
                "Sin antecedentes", "—", "Vacunas al día");
        HistoriaClinica hc4 = new HistoriaClinica("HC-0004", GrupoSanguineo.AB_POS,
                "Diabetes tipo 2", "Metformina", "Controles trimestrales");
        HistoriaClinica hc5 = new HistoriaClinica("HC-0005", GrupoSanguineo.O_NEG,
                "Alergia a penicilina", "—", "Usar macrólidos si antibiótico");

        // Pacientes
        Paciente p1 = new Paciente("Ana", "Paz", "40234567",
                LocalDate.of(1999, 5, 10), hc1, GrupoSanguineo.O_POS);

        Paciente p2 = new Paciente("Bruno", "Luna", "35111222",
                LocalDate.of(1988, 11, 23), hc2, GrupoSanguineo.A_NEG);

        Paciente p3 = new Paciente("Carla", "Ríos", "28999888",
                LocalDate.of(2001, 2, 3), hc3, GrupoSanguineo.B_NEG);

        Paciente p4 = new Paciente("Diego", "Suárez", "31222444",
                LocalDate.of(1975, 7, 15), hc4, GrupoSanguineo.AB_POS);

        Paciente p5 = new Paciente("Eva", "Mena", "45666777",
                LocalDate.of(2003, 9, 29), hc5, GrupoSanguineo.O_NEG);

        
        // Mostrar
        System.out.println("\n----------------Historias Clinicas---------------------");
        System.out.println(hc1);
        System.out.println(hc2);
        System.out.println(hc3);
        System.out.println(hc4);
        System.out.println(hc5);
        System.out.println(" ");
        System.out.println("\n----------------Pacientes-----------------------");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(p5);
        System.out.println(" ");
        
        System.out.println("-------------prueba constructore vacio--------------");
        //constructoress vacios,instanciamos un o por uno
        
        // ---- 1. Creamos objetos usando el constructor vacío
        HistoriaClinica hcvacio = new HistoriaClinica();
        Paciente p = new Paciente();

        // ---- 2. Cargar datos con setters ----
        hcvacio.setId(1L);
        hcvacio.setNroHistoria("HC-0101");
        hcvacio.setGrupoSanguineo(GrupoSanguineo.A_POS);
        hcvacio.setAntecedentes("Sin antecedentes");
        hcvacio.setMedicacionActual("Ninguna");
        hcvacio.setObservaciones("Control general");
        hcvacio.setEliminado(false);

        p.setId(1L);
        p.setNombre("Juan");
        p.setApellido("Pérez");
        p.setDni("40123456");
        p.setFechaNacimiento(LocalDate.of(1990, 3, 25));
        p.setGrupoSanguineo(GrupoSanguineo.A_POS);
        p.setHistoriaClinica(hcvacio);
        p.setEliminado(false);

        // ---- 3. Mostrar los datos cargados ----
        System.out.println(hcvacio);
        System.out.println(p);
        }
    }
