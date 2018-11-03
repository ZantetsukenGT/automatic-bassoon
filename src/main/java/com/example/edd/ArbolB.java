package com.example.edd;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ozmarescobar
 */
public class ArbolB
{
    public NodoB raiz;
    public int orden;
    public int tamano;

    public ArbolB()
    {
    }
    
    public ArbolB(int orden)
    {
        if(orden < 3)
            orden = 3;
        if(orden%2 != 0)
            orden = orden/2 + 1;
        else
            orden /= 2;
        this.orden = orden;
        this.raiz = new NodoB(orden);
        this.tamano = 0;
    }

    public String Graficar(String ruta) throws  InterruptedException, IOException
    {
        Esencial esencial = new Esencial();
        String text = "digraph G\n{\n\tdpi = 100;\n\tsize=300;\n\tnode[shape=none];\n\tArbolB[shape=record];\n";
        text += PreOrder_Grafica("ArbolB", raiz);
        text += "\n}";
        
        File f = esencial.Generar_Archivo(text, ruta);
        Runtime.getRuntime().exec("dot -Tpng " + ruta + " -O");
        return f.getAbsolutePath() + ".png";
    }

    private String PreOrder_Grafica(String padre, NodoB actual)
    {

        String text = "<<TABLE CELLSPACING=\"0\"><TR>";

        for (int i = 0; i < 2*orden - 1; i++)
        {
            Estudiante pivote = actual.llaves[i];
            if (pivote != null)
            {
                text += "<TD>Carne: " + pivote.Carnet + "<BR/>";
                text += "DPI: " + pivote.DPI + "<BR/>";
                text += "Nombre: " + pivote.Nombre + "<BR/>";
                text += "Apellidos: " + pivote.Apellido + "<BR/>";
                text += "Correo: " + pivote.Email + "<BR/>";
                text += "Token: " + pivote.Token + "<BR/>";
                text += "Password: " + pivote.Password + "<BR/>";
                text += "Creditos: " + pivote.Creditos + "<BR/>";
                text += "CursosAP: " + pivote.Cursos_Aprobados + "</TD>";
            } else
            {
                text += "<TD></TD>";
            }
        }

        text = "\n\t" + actual.llaves.hashCode() + "[label=" + text + "</TR></TABLE>>];";
        text += "\n\t" + padre + "->" + actual.llaves.hashCode() + ";\n";

        for (int i = 0; i < 2*orden; i++)
        {
            if (actual.hijos[i] != null)
            {
                text += PreOrder_Grafica("" + actual.llaves.hashCode(), actual.hijos[i]);
            }
        }

        return text;
    }

    public Estudiante Buscar(int criterio)
    {
        if(raiz != null)
        {
            return Buscar(criterio, raiz);
        }
        return null;
    }

    private Estudiante Buscar(int criterio, NodoB nodo)
    {
        NodoB pivote = nodo;
        for (int i = 0; i < 2*orden - 1; i++)
        {
            Estudiante actual = pivote.llaves[i];
            if (actual != null && criterio == actual.Carnet)
            {
                return actual;
            }
        }

        for (int i = 0; i < 2*orden; i++)
        {
            NodoB hijo = pivote.hijos[i];
            if (hijo != null)
            {
                Estudiante actual = Buscar(criterio, hijo);
                if (actual != null)
                {
                    return actual;
                }
            }
        }
        return null;
    }

    public void Insertar(Estudiante nuevo)
    {
            if (raiz.estaLlena())
            {
                Dividir_Raiz();
                tamano++;
            }
            raiz.Insertar_Estudiante(nuevo);
    }

    private void Dividir_Raiz()
    {
        NodoB hijoizq = new NodoB(orden);
        NodoB hijoder = new NodoB(orden);

        hijoizq.esHoja = raiz.esHoja;
        hijoder.esHoja = raiz.esHoja;

        hijoizq.numeroElementos = orden - 1;
        hijoder.numeroElementos = orden - 1;

        int centro = orden - 1;

        for (int i = 0; i < orden - 1; i++)
        {
            hijoizq.hijos[i] = raiz.hijos[i];
            hijoizq.llaves[i] = raiz.llaves[i];
        }
        hijoizq.hijos[centro] = raiz.hijos[centro];
        for (int i = centro + 1; i < raiz.numeroElementos; i++)
        {
            hijoder.hijos[i - centro - 1] = raiz.hijos[i];
            hijoder.llaves[i - centro - 1] = raiz.llaves[i];
        }

        hijoder.hijos[centro] = raiz.hijos[raiz.numeroElementos];
        Estudiante aux = raiz.llaves[centro];

        raiz.llaves = new Estudiante[2*orden - 1];
        raiz.llaves[0] = aux;
        raiz.numeroElementos = 1;
        raiz.hijos = new NodoB[2*orden];

        raiz.hijos[0] = hijoizq;
        raiz.hijos[1] = hijoder;

        raiz.esHoja = false;
    }
    
}
