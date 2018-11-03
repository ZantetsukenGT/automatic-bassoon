package com.example.edd;

/**
 *
 * @author ozmarescobar
 */
public class TutorAcademico
{
    public Estudiante tutor;
    public CursoPensum curso;
    public MatrizDispersa notas;
    public TablaHash estudiantes;
    public String periodo;

    public TutorAcademico()
    {
        tutor = null;
        curso = null;
        notas = null;
        estudiantes = null;
        periodo = null;
    }

    public TutorAcademico(Estudiante tutor, CursoPensum curso, String periodo)
    {
        this.tutor = tutor;
        this.curso = curso;
        notas = new MatrizDispersa();
        estudiantes = new TablaHash(23);
        this.periodo = periodo;
    }
    
}
