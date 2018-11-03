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
public class TutorAcademicoDAO
{
    private String curso;

    private String estudiante;

    private String periodo;

    public String getCurso ()
    {
        return curso;
    }

    public void setCurso (String curso)
    {
        this.curso = curso;
    }

    public String getEstudiante ()
    {
        return estudiante;
    }

    public void setEstudiante (String estudiante)
    {
        this.estudiante = estudiante;
    }

    public String getPeriodo ()
    {
        return periodo;
    }

    public void setPeriodo (String periodo)
    {
        this.periodo = periodo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [curso = "+curso+", estudiante = "+estudiante+", periodo = "+periodo+"]";
    }
}