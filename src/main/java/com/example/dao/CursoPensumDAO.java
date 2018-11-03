package com.example.dao;

/**
 *
 * @author ozmarescobar
 */
public class CursoPensumDAO
{

    private String codigo;

    private String nombre;

    private String area;

    private String[] pre;

    private String creditos;

    public CursoPensumDAO()
    {
    }

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getArea()
    {
        return area;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public String[] getPre()
    {
        return pre;
    }

    public void setPre(String[] pre)
    {
        this.pre = pre;
    }

    public String getCreditos()
    {
        return creditos;
    }

    public void setCreditos(String creditos)
    {
        this.creditos = creditos;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [codigo = " + codigo + ", nombre = " + nombre + ", area = " + area + ", pre = " + pre + ", creditos = " + creditos + "]";
    }
}
