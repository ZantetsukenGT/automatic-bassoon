package com.example.dao;

/**
 *
 * @author ozmarescobar
 */
public class EstudianteDAO
{

    private String nombre;

    private CursoAprobadoDAO[] cursos;

    private String carnet;

    private String apellidos;

    private String token;

    private String dpi;

    private String creditos;

    private String password;

    private String correo;

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public CursoAprobadoDAO[] getCursos()
    {
        return cursos;
    }

    public void setCursos(CursoAprobadoDAO[] cursos)
    {
        this.cursos = cursos;
    }

    public String getCarnet()
    {
        return carnet;
    }

    public void setCarnet(String carnet)
    {
        this.carnet = carnet;
    }

    public String getApellidos()
    {
        return apellidos;
    }

    public void setApellidos(String apellidos)
    {
        this.apellidos = apellidos;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getDpi()
    {
        return dpi;
    }

    public void setDpi(String dpi)
    {
        this.dpi = dpi;
    }

    public String getCreditos()
    {
        return creditos;
    }

    public void setCreditos(String creditos)
    {
        this.creditos = creditos;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getCorreo()
    {
        return correo;
    }

    public void setCorreo(String correo)
    {
        this.correo = correo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [nombre = " + nombre + ", cursos = " + cursos + ", carnet = " + carnet + ", apellidos = " + apellidos + ", token = " + token + ", dpi = " + dpi + ", creditos = " + creditos + ", password = " + password + ", correo = " + correo + "]";
    }
}
