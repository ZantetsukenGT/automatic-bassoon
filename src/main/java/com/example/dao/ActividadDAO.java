/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.dao;

/**
 *
 * @author ozmarescobar
 */
public class ActividadDAO
{
    private String nombre;

    private String fecha;

    private String ponderacion;

    private String actividad;

    private String descripcion;

    public String getNombre ()
    {
        return nombre;
    }

    public void setNombre (String nombre)
    {
        this.nombre = nombre;
    }

    public String getFecha ()
    {
        return fecha;
    }

    public void setFecha (String fecha)
    {
        this.fecha = fecha;
    }

    public String getPonderacion ()
    {
        return ponderacion;
    }

    public void setPonderacion (String ponderacion)
    {
        this.ponderacion = ponderacion;
    }

    public String getActividad ()
    {
        return actividad;
    }

    public void setActividad (String actividad)
    {
        this.actividad = actividad;
    }

    public String getDescripcion ()
    {
        return descripcion;
    }

    public void setDescripcion (String descripcion)
    {
        this.descripcion = descripcion;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [nombre = "+nombre+", fecha = "+fecha+", ponderacion = "+ponderacion+", actividad = "+actividad+", descripcion = "+descripcion+"]";
    }
}
