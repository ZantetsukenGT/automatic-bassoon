package com.example.edd;

/**
 *
 * @author ozmarescobar
 */
public class CursoAprobado
{

    public String nombre;

    public int nota;

    public String fecha;

    public int codigoCurso;
    
    public CursoAprobado()
    {
        
    }

    public CursoAprobado(String nombre, int nota, String fecha, int codigoCurso)
    {
        this.nombre = nombre;
        this.nota = nota;
        this.fecha = fecha;
        this.codigoCurso = codigoCurso;
    }

    @Override
    public String toString()
    {
        String result = "\"" + this.hashCode() + "\"[label = \""
                + "Codigo Curso: " + codigoCurso + "\\n"
                + "Nombre: " + nombre + "\\n"
                + "Nota: " + nota + "\\n"
                + "Fecha: " + fecha
                + "\"];\n";
        return result;
    }
    
}
