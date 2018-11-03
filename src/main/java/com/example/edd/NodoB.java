package com.example.edd;

/**
 *
 * @author ozmarescobar
 */
public class NodoB
{
    public NodoB[] hijos;
    public Estudiante[] llaves;
    public int numeroElementos;
    public boolean esHoja;
    public int orden;

    public NodoB()
    {
    }

    public NodoB(int orden)
    {
        this.orden = orden;
        esHoja = true;
        numeroElementos = 0;
        llaves = new Estudiante[2*orden - 1];
        hijos = new NodoB[2*orden];
    }

    public void Insertar_Estudiante(Estudiante nuevo)
    {
        int i = numeroElementos - 1;
        if (esHoja)
        {
            while (i >= 0 && nuevo.Carnet < llaves[i].Carnet)
            {
                llaves[i + 1] = llaves[i];
                i--;
            }
            numeroElementos++;
            llaves[i + 1] = nuevo;
        }
        else
        {
            while (i >= 0 && nuevo.Carnet < llaves[i].Carnet)
            {
                i--;
            }

            int hijo = i + 1;

            if (hijos[hijo].estaLlena())
            {

                numeroElementos++;
                hijos[numeroElementos] = hijos[numeroElementos - 1];

                for (int j = numeroElementos - 1; j > hijo; j--)
                {
                    hijos[j] = hijos[j - 1];
                    llaves[j] = llaves[j - 1];
                }

                llaves[hijo] = hijos[hijo].llaves[orden - 1];
                hijos[hijo].numeroElementos = orden - 1;

                NodoB nuevoNodo = new NodoB(orden);

                int k;
                for (k = 0; k < orden - 1; k++)
                {
                    nuevoNodo.hijos[k] = hijos[hijo].hijos[k + orden];
                    nuevoNodo.llaves[k] = hijos[hijo].llaves[k + orden];
                    hijos[hijo].hijos[k + orden] = null;
                    hijos[hijo].llaves[k + orden - 1] = null;
                }

                hijos[hijo].llaves[k + orden - 1] = null;

                nuevoNodo.hijos[orden - 1] = hijos[hijo].hijos[2 * orden - 1];
                nuevoNodo.numeroElementos = orden - 1;
                nuevoNodo.esHoja = hijos[hijo].esHoja;
                hijos[hijo + 1] = nuevoNodo;

                if (nuevo.Carnet < llaves[hijo].Carnet)
                {
                    hijos[hijo].Insertar_Estudiante(nuevo);
                } else
                {
                    hijos[hijo + 1].Insertar_Estudiante(nuevo);
                }
                hijos[hijo].hijos[k + orden] = null;

            } else
            {
                hijos[hijo].Insertar_Estudiante(nuevo);
            }
        }
    }

    public boolean estaLlena()
    {
        return numeroElementos == llaves.length;
    }
}
