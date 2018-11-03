/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.services;

import javax.ws.rs.Path;

import com.example.dao.CursoPensumDAO;
import com.example.dao.CursoAprobadoDAO;
import com.example.dao.EstudianteDAO;

import com.example.edd.NodoLista;
import com.example.edd.ListaDoble;
import com.example.edd.ArbolB;
import com.example.edd.CursoAprobado;
import com.example.edd.Esencial;
import com.example.edd.Estudiante;
import com.example.edd.AVL;
import com.example.edd.CursoPensum;
import java.io.File;
import java.io.IOException;
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
@Path("Estudiante")
public class EstudiantesServicio
{
    protected static final ArbolB ESTUDIANTES = new ArbolB(5);
    protected static final ListaDoble POSIBLES_TUTORES = new ListaDoble();
    
    @POST
    @Path("Insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Insertar(EstudianteDAO estudianteDAO)
    {
        if(ESTUDIANTES.Buscar(Integer.parseInt(estudianteDAO.getCarnet())) == null)
        {
            Estudiante nuevo = new Estudiante(
                    Integer.parseInt(estudianteDAO.getCarnet()), 
                    estudianteDAO.getDpi(), 
                    estudianteDAO.getNombre(), 
                    estudianteDAO.getApellidos(), 
                    estudianteDAO.getCorreo(),
                    "", 
                    estudianteDAO.getPassword(), 
                    0
            );
            if(estudianteDAO.getCursos().length > 0)
            {
                CursoAprobadoDAO[] cursosDAO = estudianteDAO.getCursos();

                ListaDoble cursos = new ListaDoble();
                for(int i = 0; i < cursosDAO.length; i++)
                {
                    CursoAprobado curso = new CursoAprobado(
                            cursosDAO[i].getNombre(), 
                            Integer.parseInt(cursosDAO[i].getNota()), 
                            cursosDAO[i].getFecha(), 
                            Integer.parseInt(cursosDAO[i].getCodigoCurso())
                    );
                    cursos.Insertar_CursoAprobado(curso);
                }
                nuevo.Cursos_Aprobados = cursos;
                for(int i = 0; i < cursosDAO.length; i++)
                {
                    NodoLista pivote = CursosServicio.PENSUM.Buscar(Integer.parseInt(cursosDAO[i].getCodigoCurso()));
                    if(pivote != null)
                    {
                        nuevo.Creditos += ((CursoPensum) pivote.data).creditos;
                    }
                }
            }

            ESTUDIANTES.Insertar(nuevo);
            if(nuevo.Creditos >= 100)
            {
                POSIBLES_TUTORES.InsertarAlFinal(nuevo);
            }
                    
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
    
    @GET
    @Path("Grafica")
    public Response Graficar()
    {
        try
        {
            String url = ESTUDIANTES.Graficar("/home/ozmarescobar/Documentos/proyecto2/public/ArbolB_Estudiantes");
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
    
    @POST
    @Path("Buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Buscar(int criterio)
    {
        Estudiante busqueda = ESTUDIANTES.Buscar(criterio);
        if(busqueda != null)
        {
            EstudianteDAO result = new EstudianteDAO();
            result.setCarnet("" + busqueda.Carnet);
            result.setDpi(busqueda.DPI);
            result.setNombre(busqueda.Nombre);
            result.setApellidos(busqueda.Apellido);
            result.setCorreo(busqueda.Email);
            result.setToken(busqueda.Token);
            result.setPassword(busqueda.Password);
            result.setCreditos("" + busqueda.Creditos);
            
            result.setCursos(new CursoAprobadoDAO[0]);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @POST
    @Path("Visualizar")
    public Response VisualizarEstudiante(int criterio)
    {
        try
        {
            Estudiante buscado = ESTUDIANTES.Buscar(criterio);
            if(buscado != null)
            {
                String text = "digraph G\n{\n\tnode[shape = box];\n\trankdir=TB;\n\t";
                text += buscado.toString();
                text += "\n}";
                Esencial esencial = new Esencial();
                File f = esencial.Generar_Archivo(text, "/home/ozmarescobar/Documentos/proyecto2/public/Estudiante" + criterio);
                
                String url = f.getAbsolutePath();
                Runtime.getRuntime().exec("dot -Tpng " + url + " -O");
                url = url.replace("/home/ozmarescobar/Documentos/proyecto2/public/", "http://192.168.56.101:8080/");
                return Response.status(Response.Status.OK).entity(url + ".png").build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        catch (IOException ex)
        {
            Logger.getLogger(EstudiantesServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
    
    @POST
    @Path("Modificar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Modificar(EstudianteDAO estudianteDAO)
    {
        Estudiante modificado = ESTUDIANTES.Buscar(Integer.parseInt(estudianteDAO.getCarnet()));
        if(modificado != null)
        {
            modificado.DPI = estudianteDAO.getDpi();
            modificado.Nombre = estudianteDAO.getNombre();
            modificado.Apellido = estudianteDAO.getApellidos();
            modificado.Email = estudianteDAO.getCorreo();
            modificado.Password = estudianteDAO.getPassword();
            if(estudianteDAO.getCursos().length > 0)
            {
                CursoAprobadoDAO[] cursosDAO = estudianteDAO.getCursos();

                for(int i = 0; i < cursosDAO.length; i++)
                {
                    CursoAprobado curso = new CursoAprobado(
                            cursosDAO[i].getNombre(), 
                            Integer.parseInt(cursosDAO[i].getNota()), 
                            cursosDAO[i].getFecha(), 
                            Integer.parseInt(cursosDAO[i].getCodigoCurso())
                    );
                    modificado.Cursos_Aprobados.Insertar_CursoAprobado(curso);
                }
                for(int i = 0; i < cursosDAO.length; i++)
                {
                    NodoLista pivote2 = CursosServicio.PENSUM.Buscar(Integer.parseInt(cursosDAO[i].getCodigoCurso()));
                    if(pivote2 != null)
                    {
                        modificado.Creditos += ((CursoPensum) pivote2.data).creditos;
                    }
                }
            }
            if(modificado.Creditos >= 100)
            {
                NodoLista est = (NodoLista) POSIBLES_TUTORES.Buscar_Estudiante(modificado.Carnet);
                if(est == null)
                {
                    POSIBLES_TUTORES.InsertarAlFinal(modificado);
                }
            }
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}
