package com.example.edd;

/**
 *
 * @author ozmarescobar
 */
public class CabeceraMatriz
{
    public NodoMatriz primero;

    public CabeceraMatriz siguiente;
    public CabeceraMatriz anterior;

    public int fila_o_columna;
    public String nombre;
    public Actividad actividad;

    public CabeceraMatriz()
    {
        primero = null;
        siguiente = anterior = null;
        fila_o_columna = 0;
    }

    public CabeceraMatriz(int fila_o_columna)
    {
        primero = null;
        siguiente = anterior = null;
        this.fila_o_columna = fila_o_columna;
    }
    
    /////////NODO//////////
    public void Insertar_Al_Frente_Fila(NodoMatriz nuevo)
    {
        if(primero == null)
        {
            primero = nuevo;
        }
        else
        {
            nuevo.derecha = primero;
            primero.izquierda = nuevo;
            primero = nuevo;
        }
    }

    public void Insertar_Al_Final_Fila(NodoMatriz nuevo)
    {
        if(primero == null)
        {
            primero = nuevo;
        }
        else
        {
            NodoMatriz pivote = primero;
            while(pivote.derecha != null)
            {
                pivote = pivote.derecha;
            }
            nuevo.izquierda = pivote;
            pivote.derecha = nuevo;
        }
    }

    public NodoMatriz Remover_Del_Frente_Fila()
    {
        if(primero != null)
        {
            NodoMatriz pivote = primero;
            if(primero.derecha == null)
            {
                primero = null;
            }
            else
            {
                primero = primero.derecha;
                primero.izquierda = null;

            }
            return pivote;
        }
        return null;
    }

    public NodoMatriz Remover_Del_Final_Fila()
    {
        if(primero != null)
        {
            NodoMatriz pivote = primero;
            if(primero.derecha == null)
            {
                primero = null;
            }
            else
            {
                NodoMatriz pivote2 = null;

                while(pivote.derecha != null)
                {
                    pivote2 = pivote;
                    pivote = pivote.derecha;
                }
                pivote2.derecha = null;
            }
            return pivote;
        }
        return null;
    }

    public NodoMatriz Buscar_En_Fila(int criterio)
    {
        if(primero != null)
        {
            NodoMatriz pivote = primero;
            while(pivote != null)
            {
                if(pivote.j == criterio)
                {
                    return pivote;
                }
                pivote = pivote.derecha;
            }
        }
        return null;
    }

    public void Insertar_Al_Frente_Columna(NodoMatriz nuevo)
    {
        if(primero == null)
        {
            primero = nuevo;
        }
        else
        {
            nuevo.abajo = primero;
            primero.arriba = nuevo;
            primero = nuevo;
        }
    }

    public void Insertar_Al_Final_Columna(NodoMatriz nuevo)
    {
        if(primero == null)
        {
            primero = nuevo;
        }
        else
        {
            NodoMatriz pivote = primero;
            while(pivote.abajo != null)
            {
                pivote = pivote.abajo;
            }
            nuevo.arriba = pivote;
            pivote.abajo = nuevo;
        }
    }

    public NodoMatriz Remover_Del_Frente_Columna()
    {
        if(primero != null)
        {
            NodoMatriz pivote = primero;
            if(primero.abajo == null)
            {
                primero = null;
            }
            else
            {
                primero = primero.abajo;
                primero.arriba = null;

            }
            return pivote;
        }
        return null;
    }

    public NodoMatriz Remover_Del_Final_Columna()
    {
        if(primero != null)
        {
            NodoMatriz pivote = primero;
            if(primero.abajo == null)
            {
                primero = null;
            }
            else
            {
                NodoMatriz pivote2 = null;

                while(pivote.abajo != null)
                {
                    pivote2 = pivote;
                    pivote = pivote.abajo;
                }
                pivote2.abajo = null;
            }
            return pivote;
        }
        return null;
    }

    public NodoMatriz Buscar_En_Columna(int criterio)
    {
        if(primero != null)
        {
            NodoMatriz pivote = primero;
            while(pivote != null)
            {
                if(pivote.i == criterio)
                {
                    return pivote;
                }
                pivote = pivote.abajo;
            }
        }
        return null;
    }



    public void Insertar_En_Fila(NodoMatriz nuevo)
    {
        if(primero != null)
        {
            if(nuevo.j == primero.j)
            {
                return;
            }
            if(nuevo.j < primero.j)
            {
                Insertar_Al_Frente_Fila(nuevo);
                return;
            }
            NodoMatriz pivote = primero.derecha;
            while(pivote != null)
            {
                if(nuevo.j == pivote.j)
                {
                    return;
                }
                if(nuevo.j < pivote.j)
                {
                    nuevo.derecha = pivote;
                    nuevo.izquierda = pivote.izquierda;

                    pivote.izquierda.derecha = nuevo;
                    pivote.izquierda = nuevo;
                    return;
                }
                pivote = pivote.derecha;
            }
            Insertar_Al_Final_Fila(nuevo);
            return;
        }
        Insertar_Al_Frente_Fila(nuevo);
    }

    public void Insertar_En_Columna(NodoMatriz nuevo)
    {
        if(primero != null)
        {
            if(nuevo.i == primero.i)
            {
                return;
            }
            if(nuevo.i < primero.i)
            {
                Insertar_Al_Frente_Columna(nuevo);
                return;
            }
            NodoMatriz pivote = primero.abajo;
            while(pivote != null)
            {
                if(nuevo.i == pivote.i)
                {
                    return;
                }
                if(nuevo.i < pivote.i)
                {
                    nuevo.abajo = pivote;
                    nuevo.arriba = pivote.arriba;

                    pivote.arriba.abajo = nuevo;
                    pivote.arriba = nuevo;
                    return;
                }
                pivote = pivote.abajo;
            }
            Insertar_Al_Final_Columna(nuevo);
            return;
        }
        Insertar_Al_Frente_Columna(nuevo);
    }

    public NodoMatriz Remover_En_Fila(int criterio)
    {
        NodoMatriz aux = Buscar_En_Fila(criterio);
        if(aux != null)
        {
            if(aux == primero)
            {
                return Remover_Del_Frente_Fila();
            }
            else if(aux.derecha == null)
            {
                return Remover_Del_Final_Fila();
            }
            aux.izquierda.derecha = aux.derecha;
            aux.derecha.izquierda = aux.izquierda;
            return aux;
        }
        return null;
    }

    public NodoMatriz Remover_En_Columna(int criterio)
    {
        NodoMatriz aux = Buscar_En_Columna(criterio);
        if(aux != null)
        {
            if(aux == primero)
            {
                return Remover_Del_Frente_Columna();
            }
            else if(aux.abajo == null)
            {
                return Remover_Del_Final_Columna();
            }
            aux.arriba.abajo = aux.abajo;
            aux.abajo.arriba = aux.arriba;
            return aux;
        }
        return null;
    }
    /////////NODO//////////

}
