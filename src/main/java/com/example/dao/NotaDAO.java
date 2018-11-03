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
public class NotaDAO
{
    private String carnet;

    private String nota;

    private String actividad;

    public String getCarnet ()
    {
        return carnet;
    }

    public void setCarnet (String carnet)
    {
        this.carnet = carnet;
    }

    public String getNota ()
    {
        return nota;
    }

    public void setNota (String nota)
    {
        this.nota = nota;
    }

    public String getActividad ()
    {
        return actividad;
    }

    public void setActividad (String actividad)
    {
        this.actividad = actividad;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [carnet = "+carnet+", nota = "+nota+", actividad = "+actividad+"]";
    }
}