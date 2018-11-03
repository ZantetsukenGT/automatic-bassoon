package com.example.edd;

/**
 *
 * @author ozmarescobar
 */
public class NodoLista
{
    public NodoLista anterior;
    public NodoLista siguiente;
    public Object data;

    public NodoLista()
    {
    }

    public NodoLista(Object data)
    {
        this.data = data;
        siguiente = null;
        anterior = null;
    }
}
