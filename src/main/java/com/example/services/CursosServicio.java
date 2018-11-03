package com.example.services;

import javax.ws.rs.Path;

import com.example.dao.CursoPensumDAO;
import com.example.dao.CursoAprobadoDAO;
import com.example.dao.EstudianteDAO;

import com.example.edd.Pensum;
import com.example.edd.CursoPensum;
import com.example.edd.NodoLista;
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
@Path("Curso")
public class CursosServicio
{

    protected static final Pensum PENSUM = new Pensum();

    @POST
    @Path("Insertar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Insertar(CursoPensumDAO cursopensumDAO)
    {
        NodoLista pivote = PENSUM.Buscar(Integer.parseInt(cursopensumDAO.getCodigo()));
        if (pivote == null)
        {
            CursoPensum curso;
            int[] pre = new int[cursopensumDAO.getPre().length];
            if (pre.length > 0)
            {
                String[] preString = cursopensumDAO.getPre();

                for (int i = 0; i < pre.length; i++)
                {
                    pre[i] = Integer.parseInt(preString[i]);
                }
            }
            curso = new CursoPensum(
                    cursopensumDAO.getNombre(),
                    Integer.parseInt(cursopensumDAO.getCodigo()),
                    Integer.parseInt(cursopensumDAO.getCreditos()),
                    pre,
                    cursopensumDAO.getArea()
            );
            PENSUM.cursos.Insertar_CursoPensum(curso);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("Buscar")
    public Response Buscar(int codigo)
    {
        NodoLista buscado = PENSUM.Buscar(codigo);
        if (buscado != null)
        {
            CursoPensum curso = (CursoPensum) buscado.data;
            CursoPensumDAO result = new CursoPensumDAO();
            result.setArea(curso.area);
            result.setCodigo("" + curso.codigo);
            result.setCreditos("" + curso.creditos);
            result.setNombre(curso.nombre);

            String[] pre = new String[curso.pre.length];
            for (int i = 0; i < pre.length; i++)
            {
                pre[i] = "" + curso.pre[i];
            }
            result.setPre(pre);
            return Response.status(Response.Status.OK).entity(result).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("Eliminar")
    public Response Eliminar(int codigo)
    {

        NodoLista eliminar = PENSUM.Eliminar(codigo);
        if (eliminar != null)
        {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("Modificar")
    public Response Modificar(CursoPensumDAO cursopensumDAO)
    {

        NodoLista modificar = PENSUM.Eliminar(Integer.parseInt(cursopensumDAO.getCodigo()));
        if (modificar != null)
        {
            CursoPensum curso;
            int[] pre = new int[cursopensumDAO.getPre().length];
            if (pre.length > 0)
            {
                String[] preString = cursopensumDAO.getPre();

                for (int i = 0; i < pre.length; i++)
                {
                    pre[i] = Integer.parseInt(preString[i]);
                }
            }
            curso = new CursoPensum(
                    cursopensumDAO.getNombre(),
                    Integer.parseInt(cursopensumDAO.getCodigo()),
                    Integer.parseInt(cursopensumDAO.getCreditos()),
                    pre,
                    cursopensumDAO.getArea()
            );
            PENSUM.cursos.Insertar_CursoPensum(curso);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("Grafo")
    public Response Graficar_Grafo()
    {
        try
        {
            String url = PENSUM.Graficar(Pensum.GRAFO,"/home/ozmarescobar/Documentos/proyecto2/public/Grafo_Pensum");
            url = url.replace("/home/ozmarescobar/Documentos/proyecto2/public/", "http://192.168.56.101:8080/");
            return Response.status(Response.Status.OK).entity(url).build();
        } catch (InterruptedException ex)
        {
            Logger.getLogger(CursosServicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(CursosServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
    
    @GET
    @Path("Matriz")
    public Response Graficar_Matriz()
    {
        try
        {
            String url = PENSUM.Graficar(Pensum.MATRIZ,"/home/ozmarescobar/Documentos/proyecto2/public/Matriz_Pensum");
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
}
