package com.example.edd;

/**
 *
 * @author ozmarescobar
 */
public class ListaDoble
{
    public NodoLista primero;
    public int size;

    public ListaDoble()
    {
        primero = null;
        size = 0;
    }

    public void InsertarAlFrente(Object data)
    {
        if(data != null)
        {
            NodoLista nuevo = new NodoLista(data);
            if (primero == null)
            {
                primero = nuevo;
                size = 1;
            } else
            {
                nuevo.siguiente = primero;
                primero.anterior = nuevo;
                primero = nuevo;
                size++;
            }
        }
    }

    public void InsertarAlFinal(Object data)
    {
        if(data != null)
        {
            NodoLista nuevo = new NodoLista(data);
            if (primero == null)
            {
                primero = nuevo;
                size = 1;
            } else
            {
                NodoLista pivote = primero;
                while (pivote.siguiente != null)
                {
                    pivote = pivote.siguiente;
                }
                pivote.siguiente = nuevo;
                nuevo.anterior = pivote;
                size++;
            }
        }
    }

    public Object RemoverDelFrente()
    {
        if (primero != null)
        {
            NodoLista pivote = primero;
            if (primero.siguiente == null)
            {
                primero = null;
                size = 0;
            } else
            {
                primero = primero.siguiente;
                primero.anterior = null;
                size--;
            }
            return pivote.data;
        }
        return null;
    }

    public Object RemoverDelFinal()
    {
        if (primero != null)
        {
            NodoLista pivote = primero;
            if (primero.siguiente == null)
            {
                primero = null;
                size = 0;
            } else
            {
                while (pivote.siguiente != null)
                {
                    pivote = pivote.siguiente;
                }
                pivote.anterior.siguiente = null;
                size--;
            }
            return pivote.data;
        }
        return null;
    }

    /**
     *
     * Inserta un Curso en esta lista de forma ordenada
     */
    public void Insertar_CursoAprobado(Object data)
    {
        if(data != null)
        {
            if (primero != null)
            {
                int criterio = ((CursoAprobado) data).codigoCurso;

                if (criterio < ((CursoAprobado) primero.data).codigoCurso)
                {
                    InsertarAlFrente(data);
                    return;
                }
                if (criterio == ((CursoAprobado) primero.data).codigoCurso)
                {
                    return;
                }
                NodoLista pivote = primero.siguiente;
                while (pivote != null)
                {
                    if (criterio < ((CursoAprobado) pivote.data).codigoCurso)
                    {
                        NodoLista nuevo = new NodoLista(data);

                        pivote.anterior.siguiente = nuevo;
                        pivote.anterior = nuevo;

                        nuevo.anterior = pivote.anterior;
                        nuevo.siguiente = pivote;
                        size++;
                        return;
                    }
                    if (criterio == ((CursoAprobado) pivote.data).codigoCurso)
                    {
                        return;
                    }
                    pivote = pivote.siguiente;
                }
                InsertarAlFinal(data);
            } else
            {
                InsertarAlFrente(data);
            }
        }
    }
    
    /**
     *
     * Inserta un Curso en esta lista al final
     */
    public void Insertar_CursoPensum(Object data)
    {
        if(data != null)
        {
            if (primero != null)
            {
                int criterio = ((CursoPensum) data).codigo;

                if (criterio == ((CursoPensum) primero.data).codigo)
                {
                    return;
                }
                NodoLista pivote = primero.siguiente;
                while (pivote != null)
                {
                    if (criterio == ((CursoPensum) pivote.data).codigo)
                    {
                        return;
                    }
                    pivote = pivote.siguiente;
                }
                InsertarAlFinal(data);
            } else
            {
                InsertarAlFrente(data);
            }
        }
    }
    
    /**
     *
     * Inserta un Curso en esta lista de forma ordenada
     */
    public void Insertar_AreaPensum(Object data)
    {
        if(data != null)
        {
            if (primero != null)
            {
                String criterio = (String) data;
                if (criterio.equals((String) primero.data))
                {
                    return;
                }
                NodoLista pivote = primero.siguiente;
                while (pivote != null)
                {
                    if (criterio.equals((String) pivote.data))
                    {
                        return;
                    }
                    pivote = pivote.siguiente;
                }
                InsertarAlFinal(data);
            } else
            {
                InsertarAlFrente(data);
            }
        }
    }
    
    /**
     *
     * Busca algo en esta lista, no utilizar
     */
    public Object Buscar(Object data)
    {
        if (primero != null)
        {
            NodoLista pivote = primero;
            while(pivote != null)
            {
                if(pivote.data.equals(data))
                {
                    return pivote.data;
                }
                pivote = pivote.siguiente;
            }
        }
        return null;
    }
    public Object Buscar_Curso_Codigo(Object data)
    {
        if (primero != null)
        {
            NodoLista pivote = primero;
            while(pivote != null)
            {
                CursoPensum curso = (CursoPensum)pivote.data;
                if(curso.codigo == Integer.parseInt(data.toString()))
                {
                    return pivote.data;
                }
                pivote = pivote.siguiente;
            }
        }
        return null;
    }
    /**
     @param data Busca un curso en la lista de cursos del pensum por medio del nombre del area
     * y retorna el codigo de dicho curso
     */
    public int Buscar_Curso_Por_Area(Object data)
    {
        if (primero != null)
        {
            NodoLista pivote = primero;
            while(pivote != null)
            {
                CursoPensum curso = (CursoPensum)pivote.data;
                if(curso.area.equals(data))
                {
                    return curso.codigo;
                }
                pivote = pivote.siguiente;
            }
        }
        return -1;
    }
    
    public Object Buscar_Estudiante(Object data)
    {
        if (primero != null)
        {
            int criterio = Integer.parseInt(data.toString());
            NodoLista pivote = primero;
            while(pivote != null)
            {
                Estudiante estudiante = (Estudiante)pivote.data;
                if(estudiante.Carnet == criterio)
                {
                    return pivote;
                }
                pivote = pivote.siguiente;
            }
        }
        return null;
    }
}
