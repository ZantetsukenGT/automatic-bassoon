package com.example.edd;

import java.io.IOException;

/**
 *
 * @author ozmarescobar
 */
public class TablaHash
{
    public ListaDoble[] tabla;
    public int MAX_TABLA;

    public TablaHash()
    {
        int i, j;
        for (i = 996 + 1;; i++)
        {
            for (j = 2; j < i; j++)
            {
                if (i % j == 0)
                {
                    break;
                }
            }
            if (j == i)
            {
                MAX_TABLA = i;
                break;
            }
        }
        tabla = new ListaDoble[MAX_TABLA];
        for (int k = 0; k < MAX_TABLA; k++)
        {
            tabla[k] = new ListaDoble();
        }
    }

    public TablaHash(int tamanyo)
    {
        int i, j;
        for (i = tamanyo + 1;; i++)
        {
            for (j = 2; j < i; j++)
            {
                if (i % j == 0)
                {
                    break;
                }
            }
            if (j == i)
            {
                MAX_TABLA = i;
                break;
            }
        }
        tabla = new ListaDoble[MAX_TABLA];
        for (int k = 0; k < MAX_TABLA; k++)
        {
            tabla[k] = new ListaDoble();
        }
    }

    public int Insertar(String k, Estudiante t)//k es un nombre, el metodo es plegamiento
    {
        String k_en_string = "";
        for (int i = 0; i < k.length(); i++)
        {
            k_en_string += Integer.toString((int) k.charAt(i));
        }
        
        int tamanyo_K = k_en_string.length();
        int tamanyo_Grupo = ContarDigitos(MAX_TABLA);
        int sumaTotal = 0;
        int i;
        for (i = 0; i < k_en_string.length(); i += tamanyo_Grupo)
        {
            if (i + tamanyo_Grupo <= k_en_string.length())
            {
                String string_grupo = k_en_string.substring(i, i + tamanyo_Grupo);
                sumaTotal += Integer.parseInt(string_grupo);
            }
        }
        if (tamanyo_K % tamanyo_Grupo != 0)
        {
            String remanente = k_en_string.substring(i - tamanyo_Grupo, k_en_string.length());
            sumaTotal += Integer.parseInt(remanente);
        }
        int pos = sumaTotal % MAX_TABLA;
        tabla[pos].InsertarAlFinal(t);
        return pos;
    }
    
    public Estudiante Buscar(String k, Estudiante t)//k es un nombre, el metodo es plegamiento
    {
        String k_en_string = "";
        for (int i = 0; i < k.length(); i++)
        {
            k_en_string += Integer.toString((int) k.charAt(i));
        }
        
        int tamanyo_K = k_en_string.length();
        int tamanyo_Grupo = ContarDigitos(MAX_TABLA);
        int sumaTotal = 0;
        int i;
        for (i = 0; i < k_en_string.length(); i += tamanyo_Grupo)
        {
            if (i + tamanyo_Grupo <= k_en_string.length())
            {
                String string_grupo = k_en_string.substring(i, i + tamanyo_Grupo);
                sumaTotal += Integer.parseInt(string_grupo);
            }
        }
        if (tamanyo_K % tamanyo_Grupo != 0)
        {
            String remanente = k_en_string.substring(i - tamanyo_Grupo, k_en_string.length());
            sumaTotal += Integer.parseInt(remanente);
        }
        int pos = sumaTotal % MAX_TABLA;
        if(tabla[pos].primero != null)
        {
            return (Estudiante) tabla[pos].Buscar_Estudiante(t.Carnet);
        }
        return null;
    }
    
    public String Graficar(String ruta) throws IOException
    {
	String texto = "digraph G { dpi = 100; size = 30;  rankdir = TB; node[shape=box]; \n\n ";
        for(int i = 0; i < tabla.length; i++)
        {
            if(i != tabla.length - 1)
            {
                texto += "\"" + i + "\"->";
            }
            else
            {
                texto += "\"" + i + "\";\n";
            }
        }
        for(int i = 0; i < tabla.length; i++)
        {
            NodoLista pivote = tabla[i].primero;
            while(pivote != null)
            {
                Estudiante estudiante = (Estudiante) pivote.data;
                texto += "\"" + estudiante.hashCode() + "\"[label = \""
                        + "Carnet: " + estudiante.Carnet + "\\n"
                        + "DPI: " + estudiante.DPI + "\\n"
                        + "Nombre: " + estudiante.Nombre + "\\n"
                        + "Apellido: " + estudiante.Apellido + "\\n"
                        + "Email: " + estudiante.Email + "\\n"
                        + "Token: " + estudiante.Token + "\\n"
                        + "Password: " + estudiante.Password + "\\n"
                        + "Creditos: " + estudiante.Creditos
                        + "\"];\n";
                pivote = pivote.siguiente;
            }
            pivote = tabla[i].primero;
            if(pivote != null)
            {
                texto += "subgraph \"G" + i + "\"{\n";
                texto += "rank = \"same\";\n";
                texto += "\"" + i + "\"->";
                while(pivote != null)
                {
                    Estudiante estudiante = (Estudiante) pivote.data;
                    if(pivote.siguiente != null)
                    {
                        texto += "\"" + estudiante.hashCode() + "\"->";
                    }
                    else
                    {
                        texto += "\"" + estudiante.hashCode() + "\";\n";
                    }
                    pivote = pivote.siguiente;
                }
                texto += "}\n";
            }
        }
	texto += "} ";
        Esencial esencial = new Esencial();

        esencial.Generar_Archivo(texto, ruta);
        Runtime.getRuntime().exec("dot -Tpng " + ruta + " -O");

        return ruta + ".png";
    }

    private int ContarDigitos(int n)
    {
        if (n > 9)
        {
            return 1 + ContarDigitos(n / 10);
        }
        return 1;
    }
}
