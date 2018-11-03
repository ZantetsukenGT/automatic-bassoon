package com.example.edd;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ozmarescobar
 */
public class Pensum
{
    public ListaDoble cursos;
    private MatrizDispersa mAdyacencia;
    public static final int MATRIZ = 0;
    public static final int GRAFO = 1;

    public Pensum()
    {
        cursos = new ListaDoble();
        mAdyacencia = null;
    }

    public Pensum(ListaDoble cursos)
    {
        this.cursos = cursos;
        mAdyacencia = null;
    }
    
    public NodoLista Buscar(int criterio)
    {
        if(cursos.primero != null)
        {
            NodoLista pivote = cursos.primero;
            while(pivote != null)
            {
                if(((CursoPensum)pivote.data).codigo == criterio)
                {
                    return pivote;
                }
                pivote = pivote.siguiente;
            }
        }
        return null;
    }
    
    public NodoLista Eliminar(int criterio)
    {
        NodoLista eliminar = Buscar(criterio);
        if(eliminar != null)
        {
            if(eliminar == cursos.primero)
            {
                cursos.RemoverDelFrente();
            }
            else if(eliminar.siguiente == null)
            {
                cursos.RemoverDelFinal();
            }
            else
            {
                eliminar.anterior.siguiente = eliminar.siguiente;
                eliminar.siguiente.anterior = eliminar.anterior;
            }
            return eliminar;
        }
        return null;
    }

    public void Generar_mAdyacencia()
    {
        if (cursos.primero != null)
        {
            NodoLista pivote = cursos.primero;

            mAdyacencia = new MatrizDispersa();
            CursoPensum curso;

            while (pivote != null)
            {
                curso = (CursoPensum) pivote.data;
                mAdyacencia.Insertar(curso.codigo, curso.codigo, 0);
                pivote = pivote.siguiente;
            }

            pivote = cursos.primero;
            while (pivote != null)
            {
                curso = (CursoPensum) pivote.data;
                for (int i = 0; i < curso.pre.length; i++)
                {
                    if(cursos.Buscar_Curso_Codigo(curso.pre[i]) != null)
                    {
                        mAdyacencia.Insertar(curso.codigo, curso.pre[i], 1);
                    }
                }
                pivote = pivote.siguiente;
            }
            
            pivote = cursos.primero;
            while (pivote != null)
            {
                curso = (CursoPensum)pivote.data;
                CabeceraMatriz pivote2 = mAdyacencia.Buscar_Fila(curso.codigo);
                pivote2.nombre = curso.nombre;
                pivote2 = mAdyacencia.Buscar_Columna(curso.codigo);
                pivote2.nombre = curso.nombre;
                pivote = pivote.siguiente;
            }
        }
    }

    public String Graficar(int numeroGrafica, String ruta) throws InterruptedException, IOException
    {
        if (cursos.primero != null)
        {
            Generar_mAdyacencia();
            String file;
            switch (numeroGrafica)
            {
                case MATRIZ:
                    file = mAdyacencia.Graficar(MATRIZ, ruta);
                    break;
                case GRAFO:
                    file = GenerarGrafo(ruta);
                    break;
                default:
                    file = "ERROR: Opcion de graficacion no valida";
            }
            return file;
        }
        return "ERROR: Pensum no definido";
    }

    private String GenerarGrafo(String ruta) throws InterruptedException, IOException
    {
        String result = "";
        if (cursos.primero != null)
        {
            String input = "digraph G\n{\n\tdpi = 100;\n\tsize = 300;\n\tnewrank = true;\n\trankdir = TB;\n"
                    + "\tnode[shape = box];\n\tsplines=ortho\n";

            ListaDoble areas = new ListaDoble();

            NodoLista pivoteCurso = cursos.primero;
            CursoPensum curso;

            while (pivoteCurso != null)
            {
                curso = (CursoPensum) pivoteCurso.data;
                areas.Insertar_AreaPensum(curso.area);
                input += "\n\t\"" + curso.codigo + "\"["
                        + "label = \"Codigo: " + curso.codigo + "\\n"
                        + curso.nombre + "\\n";
                input += "pre:[";
                for(int i = 0; i < curso.pre.length; i++)
                {
                    if(i == curso.pre.length - 1)
                    {
                        input += curso.pre[i];
                    }
                    else
                    {
                        input += curso.pre[i] + ",";
                    }
                }
                input += "]";
                input+= "\"];";

                pivoteCurso = pivoteCurso.siguiente;
            }
            input += "\n";

            pivoteCurso = cursos.primero;
            while (pivoteCurso != null)
            {
                curso = (CursoPensum) pivoteCurso.data;
                CabeceraMatriz cabecera = mAdyacencia.Buscar_Columna(curso.codigo);
                NodoMatriz pivoteCurso2 = cabecera.primero;
                while (pivoteCurso2 != null)
                {
                    if (pivoteCurso2.data.equals(1))
                    {
                        input += "\n\t\"" + curso.codigo + "\"->\"" + pivoteCurso2.i + "\";";
                    }
                    pivoteCurso2 = pivoteCurso2.abajo;
                }

                pivoteCurso = pivoteCurso.siguiente;
            }

            input += "\n";
            String[] string = new String[areas.size];
            ListaDoble same = new ListaDoble();
            for (int i = 0; i < string.length; i++)
            {
                string[i] = (String) areas.RemoverDelFrente();
                same.InsertarAlFinal(cursos.Buscar_Curso_Por_Area(string[i]));
            }

            input += "\t{rank = \"same\"; ";
            NodoLista pivoteSame = same.primero;
            while (pivoteSame != null)
            {
                input += "\"" + pivoteSame.data.toString() + "\"; ";
                pivoteSame = pivoteSame.siguiente;
            }
            input += "}\n";

            for (int i = 0; i < string.length; i++)
            {
                input += "\n\tsubgraph \"cluster" + i + "\"\n\t{";
                input += "\n\t\tlabel = \"" + string[i] + "\";\n\t\tstyle = filled;";
                input += "\n\t\tfillcolor = \"#ffe0b3\";\n\t\tcolor = white;";
                
                pivoteCurso = cursos.primero;
                while (pivoteCurso != null)
                {
                    curso = (CursoPensum) pivoteCurso.data;
                    if (curso.area.equals(string[i]))
                    {
                        input += "\n\t\t\"" + curso.codigo + "\";";
                    }
                    pivoteCurso = pivoteCurso.siguiente;
                }
                input += "\n\t}";
            }
            input += "\n}";

            Esencial esencial = new Esencial();

            File f = esencial.Generar_Archivo(input, ruta);
            Runtime.getRuntime().exec("dot -Tpng " + ruta + " -O");
            result = f.getAbsolutePath() + ".png";
        }
        return result;
    }
}
