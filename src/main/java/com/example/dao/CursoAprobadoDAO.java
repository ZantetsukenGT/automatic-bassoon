package com.example.dao;

/**
 *
 * @author ozmarescobar
 */
public class CursoAprobadoDAO
{
    private String nombre;

    private String nota;

    private String codigoCurso;

    private String fecha;

    public CursoAprobadoDAO()
    {
    }

    public String getNombre ()
    {
        return nombre;
    }

    public void setNombre (String nombre)
    {
        this.nombre = nombre;
    }

    public String getNota ()
    {
        return nota;
    }

    public void setNota (String nota)
    {
        this.nota = nota;
    }

    public String getCodigoCurso ()
    {
        return codigoCurso;
    }

    public void setCodigoCurso (String codigoCurso)
    {
        this.codigoCurso = codigoCurso;
    }

    public String getFecha ()
    {
        return fecha;
    }

    public void setFecha (String fecha)
    {
        this.fecha = fecha;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [nombre = "+nombre+", nota = "+nota+", codigoCurso = "+codigoCurso+", fecha = "+fecha+"]";
    }
}
			
			