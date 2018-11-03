package com.example.edd;

/**
 *
 * @author ozmarescobar
 */
public class NodoMatriz
{
    public NodoMatriz arriba;
    public NodoMatriz abajo;
    public NodoMatriz izquierda;
    public NodoMatriz derecha;

    public int i, j;
    public Object data;

    public NodoMatriz()
    {
        i = j = -1;
        data = "NOT_SET";
        arriba = abajo = izquierda = derecha = null;
    }

    public NodoMatriz(int i, int j, Object data)
    {
        this.i = i;
        this.j = j;
        this.data = data;
        arriba = abajo = izquierda = derecha = null;
    }
}
