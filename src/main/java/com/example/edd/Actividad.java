/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.edd;

/**
 *
 * @author ozmarescobar
 */
public class Actividad
{
    public String nombre;

    public String fecha;

    public int ponderacion;

    public String actividad;

    public String descripcion;

    public Actividad()
    {
    }

    public Actividad(String nombre, String fecha, int ponderacion, String actividad, String descripcion)
    {
        this.nombre = nombre;
        this.fecha = fecha;
        this.ponderacion = ponderacion;
        this.actividad = actividad;
        this.descripcion = descripcion;
    }

}