/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.services;

import com.example.dao.ActividadDAO;
import javax.ws.rs.Path;

import com.example.dao.CursoPensumDAO;
import com.example.dao.CursoAprobadoDAO;
import com.example.dao.EstudianteDAO;
import com.example.dao.NotaDAO;
import com.example.dao.SesionDAO;
import com.example.dao.TutorAcademicoDAO;

import com.example.edd.NodoLista;
import com.example.edd.ListaDoble;
import com.example.edd.ArbolB;
import com.example.edd.CursoAprobado;
import com.example.edd.Esencial;
import com.example.edd.Estudiante;
import com.example.edd.AVL;
import com.example.edd.Actividad;
import com.example.edd.CabeceraMatriz;
import com.example.edd.CursoPensum;
import com.example.edd.MatrizDispersa;
import com.example.edd.NodoAVL;
import com.example.edd.TutorAcademico;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ozmarescobar
 */
@Path("Tutor")
public class TutoresServicio
{

    protected static final AVL TUTORES = new AVL();
    protected static int actual = -1;

    @GET
    @Path("SetupEstudiantes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Setup1()
    {
        List<EstudianteDAO> response = new ArrayList<EstudianteDAO>();

        NodoLista pivote = EstudiantesServicio.POSIBLES_TUTORES.primero;

        while (pivote != null)
        {
            Estudiante estudiante = (Estudiante) pivote.data;
            EstudianteDAO estudianteDAO = new EstudianteDAO();
            estudianteDAO.setCarnet("" + estudiante.Carnet);
            estudianteDAO.setDpi(estudiante.DPI);
            estudianteDAO.setNombre(estudiante.Nombre);
            estudianteDAO.setApellidos(estudiante.Apellido);
            estudianteDAO.setCorreo(estudiante.Email);
            estudianteDAO.setToken("");
            estudianteDAO.setPassword("");
            estudianteDAO.setCreditos("" + estudiante.Creditos);
            estudianteDAO.setCursos(new CursoAprobadoDAO[0]);
            response.add(estudianteDAO);
            pivote = pivote.siguiente;
        }
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @GET
    @Path("SetupCursos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Setup2()
    {
        List<CursoPensumDAO> response = new ArrayList<CursoPensumDAO>();

        NodoLista pivote = CursosServicio.PENSUM.cursos.primero;

        while (pivote != null)
        {
            CursoPensum curso = (CursoPensum) pivote.data;
            CursoPensumDAO cursoDAO = new CursoPensumDAO();
            cursoDAO.setArea(curso.area);
            cursoDAO.setCodigo("" + curso.codigo);
            cursoDAO.setCreditos("" + curso.creditos);
            cursoDAO.setNombre(curso.nombre);

            String[] pre = new String[curso.pre.length];
            for (int i = 0; i < pre.length; i++)
            {
                pre[i] = "" + curso.pre[i];
            }
            cursoDAO.setPre(pre);
            response.add(cursoDAO);
            pivote = pivote.siguiente;
        }
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @POST
    @Path("Insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Insertar(TutorAcademicoDAO tutor)
    {
        NodoLista pivote = (NodoLista) EstudiantesServicio.POSIBLES_TUTORES.Buscar_Estudiante(tutor.getEstudiante());
        if (pivote != null)
        {
            NodoLista pivote2 = CursosServicio.PENSUM.Buscar(Integer.parseInt(tutor.getCurso()));
            if (pivote2 != null)
            {
                TUTORES.Insertar(new TutorAcademico((Estudiante) pivote.data, (CursoPensum) pivote2.data, tutor.getPeriodo()));
                return Response.status(Response.Status.OK).build();
            }
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("Eliminar")
    public Response Insertar(int estudiante)
    {
        NodoLista pivote = (NodoLista) EstudiantesServicio.POSIBLES_TUTORES.Buscar_Estudiante(estudiante);
        if (pivote != null)
        {
            TUTORES.Remover(estudiante);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("Grafica")
    public Response Grafica()
    {
        try
        {
            String url = TUTORES.Graficar("/home/ozmarescobar/Documentos/proyecto2/public/AVL_Tutores");
            url = url.replace("/home/ozmarescobar/Documentos/proyecto2/public/", "http://192.168.56.101:8080/");
            return Response.status(Response.Status.OK).entity(url).build();
        } catch (InterruptedException ex)
        {
            Logger.getLogger(EstudiantesServicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(EstudiantesServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
////////////////////////////////////////////////////////////////////////////////

    @GET
    @Path("MisEstudiantes")
    public Response MisEstudiantes()
    {
        try
        {
            NodoAVL pivote = TUTORES.Buscar(actual);
            if (pivote != null)
            {
                TutorAcademico tutor = pivote.Get_Data();
                if (tutor != null)
                {
                    String url = tutor.estudiantes.Graficar("/home/ozmarescobar/Documentos/proyecto2/public/CursoDe_" + actual);
                    url = url.replace("/home/ozmarescobar/Documentos/proyecto2/public/", "http://192.168.56.101:8080/");
                    return Response.status(Response.Status.OK).entity(url).build();
                }
            }
            return Response.status(Response.Status.CONFLICT).build();
        } catch (IOException ex)
        {
            Logger.getLogger(EstudiantesServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("Sesion")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Iniciar(SesionDAO sesion)
    {
        NodoAVL pivote = TUTORES.Buscar(Integer.parseInt(sesion.getUsuario()));
        if (pivote != null)
        {
            if (pivote.Get_Data().tutor.Password.equals(sesion.getPassword()))
            {
                actual = Integer.parseInt(sesion.getUsuario());
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("InsertarEstudiantes")
    public Response InsertarEstudiantes(String[] estudiantes)
    {
        NodoAVL pivote = TUTORES.Buscar(actual);
        if (pivote != null)
        {
            TutorAcademico tutor = pivote.Get_Data();
            for (int i = 0; i < estudiantes.length; i++)
            {
                Estudiante est = EstudiantesServicio.ESTUDIANTES.Buscar(Integer.parseInt(estudiantes[i]));
                if (est != null)
                {
                    CabeceraMatriz fila = tutor.notas.Insertar_Fila(Integer.parseInt(estudiantes[i]));
                    fila.nombre = estudiantes[i];
                    tutor.estudiantes.Insertar(est.Nombre + " " + est.Apellido, est);
                } else
                {
                    return Response.status(Response.Status.NOT_FOUND).build();
                }
            }
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("InsertarActividad")
    public Response InsertarActividad(ActividadDAO[] actividades)
    {
        NodoAVL pivote = TUTORES.Buscar(actual);
        if (pivote != null)
        {
            TutorAcademico tutor = pivote.Get_Data();
            if (tutor != null)
            {
                for (int i = 0; i < actividades.length; i++)
                {
                    Actividad act = new Actividad(
                            actividades[i].getNombre(),
                            actividades[i].getFecha(),
                            Integer.parseInt(actividades[i].getPonderacion()),
                            actividades[i].getActividad(),
                            actividades[i].getDescripcion()
                    );
                    CabeceraMatriz columna = tutor.notas.Insertar_Columna(tutor.notas.actividades++);
                    columna.nombre = act.nombre;
                    columna.actividad = act;
                }
                return Response.status(Response.Status.OK).build();
            }
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("InsertarNotas")
    public Response InsertarNotas(NotaDAO[] notas)
    {
        NodoAVL pivote = TUTORES.Buscar(actual);
        if (pivote != null)
        {
            TutorAcademico tutor = pivote.Get_Data();
            if (tutor != null)
            {
                for (int i = 0; i < notas.length; i++)
                {
                    CabeceraMatriz pivote2 = tutor.notas.columnas;
                    while (pivote2 != null)
                    {
                        if (pivote2.actividad.nombre.equals(notas[i].getActividad()))
                        {
                            tutor.notas.Insertar(Integer.parseInt(notas[i].getCarnet()), pivote2.fila_o_columna, notas[i].getNota());
                        }
                        pivote2 = pivote2.siguiente;
                    }
                }
                return Response.status(Response.Status.OK).build();
            }
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @GET
    @Path("ControlNotas")
    public Response ControlNotas()
    {
        try
        {
            NodoAVL pivote = TUTORES.Buscar(actual);
            if (pivote != null)
            {
                TutorAcademico tutor = pivote.Get_Data();
                if (tutor != null)
                {
                    String url = tutor.notas.Graficar(actual, "/home/ozmarescobar/Documentos/proyecto2/public/ControlDe_" + actual);
                    url = url.replace("/home/ozmarescobar/Documentos/proyecto2/public/", "http://192.168.56.101:8080/");
                    return Response.status(Response.Status.OK).entity(url).build();
                }
            }
            return Response.status(Response.Status.CONFLICT).build();
        } catch (IOException ex)
        {
            Logger.getLogger(EstudiantesServicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(TutoresServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @GET
    @Path("VerActividades")
    public Response VerActividades()
    {
        try
        {
            NodoAVL pivote = TUTORES.Buscar(actual);
            if (pivote != null)
            {
                TutorAcademico tutor = pivote.Get_Data();
                if (tutor != null)
                {
                    CabeceraMatriz columna = tutor.notas.columnas;
                    if (columna != null)
                    {
                        String text = "digraph G { dpi = 100; size = 30;  rankdir = LR; node[shape=box]; \n\n ";
                        while (columna != null)
                        {
                            text += "\"" + columna.hashCode() + "\"[label =\""
                                    + "Tipo: " + columna.actividad.actividad
                                    + "\\nNombre: " + columna.actividad.nombre
                                    + "\\nFecha: " + columna.actividad.fecha
                                    + "\\nPonderacion: " + columna.actividad.ponderacion
                                    + "\\nDescripcion: " + columna.actividad.descripcion
                                    + "\"];";
                            columna = columna.siguiente;
                        }
                        columna = tutor.notas.columnas;
                        while (columna != null)
                        {
                            if (columna.siguiente != null)
                            {
                                text += "\"" + columna.hashCode() + "\"->";
                            } else
                            {
                                text += "\"" + columna.hashCode() + "\";\n";
                            }
                            columna = columna.siguiente;
                        }
                        text += "}";
                        Esencial es = new Esencial();
                        String ruta = "/home/ozmarescobar/Documentos/proyecto2/public/ActividadesDe_" + actual;
                        
                        File f = es.Generar_Archivo(text, ruta);
                        Runtime.getRuntime().exec("dot -Tpng " + ruta + " -O");
                        
                        String url = f.getAbsolutePath() + ".png";
                        url = url.replace("/home/ozmarescobar/Documentos/proyecto2/public/", "http://192.168.56.101:8080/");
                        return Response.status(Response.Status.OK).entity(url).build();
                    }
                }
            }
            return Response.status(Response.Status.CONFLICT).build();
        } catch (IOException ex)
        {
            Logger.getLogger(EstudiantesServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

}
