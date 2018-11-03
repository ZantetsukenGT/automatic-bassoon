package com.example.edd;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ozmarescobar
 */
public class MatrizDispersa
{
    public CabeceraMatriz filas;
    public CabeceraMatriz columnas;
    
    public int actividades;
    
    public MatrizDispersa()
    {
        filas = columnas = null;
        actividades = 0;
    }
    /////////MATRIZ//////////
    public void Insertar(int i, int j, Object data)
    {
        CabeceraMatriz fila = Insertar_Fila(i);
        CabeceraMatriz columna = Insertar_Columna(j);

        NodoMatriz nuevo = new NodoMatriz(i, j, data);

        fila.Insertar_En_Fila(nuevo);
        columna.Insertar_En_Columna(nuevo);
    }

    public NodoMatriz Remover(int i, int j)
    {
        CabeceraMatriz fila = Buscar_Fila(i);
        CabeceraMatriz columna = Buscar_Columna(j);
        NodoMatriz pivote;
        if(fila != null && columna != null)
        {
            pivote = fila.Remover_En_Fila(j);
            pivote = columna.Remover_En_Columna(i);
            Remover_Fila(i);
            Remover_Columna(j);
            return pivote;
        }
        return null;
    }

    public int Obtener_Min(CabeceraMatriz pivote)
    {
        return pivote!=null?pivote.fila_o_columna:0;
    }

    public int Obtener_Max(CabeceraMatriz pivote)
    {
        if(pivote != null)
        {
            while(pivote.siguiente != null)
            {
                pivote = pivote.siguiente;
            }
            return pivote.fila_o_columna;
        }
        return 0;
    }

    public NodoMatriz Buscar(int i, int j)
    {
        CabeceraMatriz fila = Buscar_Fila(i);

        if(fila != null)
        {
            NodoMatriz pivote = fila.Buscar_En_Fila(j);
            if(pivote != null)
                return pivote;
        }
        return null;
    }
    
    public String Graficar(int criterio, String ruta) throws InterruptedException, IOException
    {
        String result = "";
        String input = "";
        
        input = ImprimirMatrizNodos();
        
        Esencial esencial = new Esencial();
        
        File f = esencial.Generar_Archivo(input, ruta);
        Runtime.getRuntime().exec("dot -Tpng " + ruta + " -O");
        
        return f.getAbsolutePath() + ".png";
    }

    private void Imprimir()
    {
        for(int i = Obtener_Min(filas); i <= Obtener_Max(filas); i++)
        {
            for(int j = Obtener_Min(columnas); j <= Obtener_Max(columnas); j++)
            {
                NodoMatriz pivote = Buscar(i,j);
                if(pivote != null)
                    System.out.print(pivote.data);
                else
                    System.out.print('-');
            }
            System.out.println("");
        }
    }

    private String ImprimirFormatNodos()
    {
        String result = "";
        CabeceraMatriz pivote = columnas;
        while(pivote != null)
        {
            result += "\t\"CabeceraColumna" + pivote.fila_o_columna + "\"";
            result += "[label = \""+ pivote.nombre +"\\n" + pivote.fila_o_columna + "\"];\n";
            pivote = pivote.siguiente;
        }

        result += "\n";
        pivote = filas;
        while(pivote != null)
        {
            result += "\t\"CabeceraFila" + pivote.fila_o_columna + "\"";
            result += "[label = \""+ pivote.nombre +"\\n" + pivote.fila_o_columna + "\"];\n";
            pivote = pivote.siguiente;
        }
        result += "\n";
        pivote = filas;
        while(pivote != null)
        {
            NodoMatriz pivote2 = pivote.primero;
            if(pivote2 != null)
            {
                String x;
                String y;
                String data;
                while(pivote2 != null)
                {
                    x = "" + pivote2.j;
                    y = "" + pivote2.i;
                    data = "" + pivote2.data.toString();

                    result += "\t\"NodoX" + x +"Y"+ y + "\"";
                    result += "[label = \"xy:" + x + "," + y +"\n"+ data + "\"];\n";
                    pivote2 = pivote2.derecha;
                }
            }
            pivote = pivote.siguiente;
        }
        result += "\n";
        return result;
    }

    private String ImprimirMatrizNodos()
    {
        String result = "digraph g\n{\n\tdpi = \"100\";\n\tsize = \"100\";\n\trankdir = \"TB\";\n\tnode[shape = box];\n\t";
        result += "Matriz;\n\n";

        result += ImprimirFormatNodos();

        result += ImprimirCabecerasFilasNodos();
        result += ImprimirCabecerasColumnasNodos();

        result += ImprimirFilaNodos();
        result += ImprimirColumnaNodos();

        result += "}";
        return result;
    }

    private String ImprimirCabecerasFilasNodos()
    {
        CabeceraMatriz pivote = filas;
        String result = "";
        if(pivote != null)
        {
            result += "\tsubgraph cabeceras_filas";
            result += "\n\t{\n";
            result += "\n\tMatriz->";
            while(pivote != null)
            {
                if(pivote.siguiente != null)
                {
                    result += "\"CabeceraFila" + pivote.fila_o_columna;
                    result += "\"->";
                }
                else
                {
                    result += "\"CabeceraFila" + pivote.fila_o_columna;
                    result += "\";\n\t";
                }
                pivote = pivote.siguiente;
            }
            result += "}\n\n";
        }
        return result;
    }

    private String ImprimirCabecerasColumnasNodos()
    {
        CabeceraMatriz pivote = columnas;
        String result = "";
        if(pivote != null)
        {
            result += "\tsubgraph cabeceras_columnas";
            result += "\n\t{\n\trank = \"same\"\n\n";
            result += "\n\tMatriz->";
            while(pivote != null)
            {
                if(pivote.siguiente != null)
                {
                    result += "\"CabeceraColumna" + pivote.fila_o_columna;
                    result += "\"->";
                }
                else
                {
                    result += "\"CabeceraColumna" + pivote.fila_o_columna;
                    result += "\";\n\t";
                }
                pivote = pivote.siguiente;
            }
            result += "}\n\n";
        }
        return result;
    }

    private String ImprimirFilaNodos()
    {
        String result = "";
        CabeceraMatriz pivote = filas;
        while(pivote != null)
        {
            result += "\tsubgraph \"y" + pivote.fila_o_columna + "_fila\"";
            result += "\n\t{\n\trank = \"same\"\n\n";
            NodoMatriz pivote2 = pivote.primero;
            if(pivote2 != null)
            {
                String x;
                String y;
                result += "\n\t\"CabeceraFila" + pivote.fila_o_columna + "\"->";
                while(pivote2 != null)
                {
                    x = "" + pivote2.j;
                    y = "" + pivote2.i;

                    if(pivote2.derecha != null)
                    {
                        result += "\"NodoX" + x +"Y"+ y;
                        result += "\"->";
                    }
                    else
                    {
                        result += "\"NodoX" + x +"Y"+ y;
                        result += "\";\n\t";
                        break;
                    }
                    pivote2 = pivote2.derecha;
                }

                while(pivote2 != null)
                {
                    x = "" + pivote2.j;
                    y = "" + pivote2.i;

                    if(pivote2.izquierda != null)
                    {
                        result += "\"NodoX" + x +"Y"+ y;
                        result += "\"->";
                    }
                    else
                    {
                        result += "\"NodoX" + x +"Y"+ y;
                        result += "\";\n\t";
                    }
                    pivote2 = pivote2.izquierda;
                }
            }
            result += "}\n\n";
            pivote = pivote.siguiente;
        }
        return result;
    }

    private String ImprimirColumnaNodos()
    {
        String result = "";
        CabeceraMatriz pivote = columnas;
        while(pivote != null)
        {
            result += "\tsubgraph \"x" + pivote.fila_o_columna + "_columna\"";
            result += "\n\t{\n\n";
            NodoMatriz pivote2 = pivote.primero;
            if(pivote2 != null)
            {
                String x;
                String y;

                result += "\n\t\"CabeceraColumna" + pivote.fila_o_columna + "\"->";
                while(pivote2 != null)
                {
                    x = "" + pivote2.j;
                    y = "" + pivote2.i;

                    if(pivote2.abajo != null)
                    {
                        result += "\"NodoX" + x +"Y"+ y;
                        result += "\"->";
                    }
                    else
                    {
                        result += "\"NodoX" + x +"Y"+ y;
                        result += "\";\n\t";
                        break;
                    }
                    pivote2 = pivote2.abajo;
                }

                while(pivote2 != null)
                {
                    x = "" + pivote2.j;
                    y = "" + pivote2.i;

                    if(pivote2.arriba != null)
                    {
                        result += "\"NodoX" + x +"Y"+ y;
                        result += "\"->";
                    }
                    else
                    {
                        result += "\"NodoX" + x +"Y"+ y;
                        result += "\";\n\t";
                    }
                    pivote2 = pivote2.arriba;
                }
            }
            result += "}\n";
            pivote = pivote.siguiente;
        }
        return result;
    }
    /////////MATRIZ//////////
    
    /////////CABECERA//////////
    private void Insertar_Al_Frente_Fila(CabeceraMatriz nuevo)
    {
        if(filas == null)
        {
            filas = nuevo;
        }
        else
        {
            nuevo.siguiente = filas;
            filas.anterior = nuevo;
            filas = nuevo;
        }
    }
    
    private void Insertar_Al_Frente_Columna(CabeceraMatriz nuevo)
    {
        if(columnas == null)
        {
            columnas = nuevo;
        }
        else
        {
            nuevo.siguiente = columnas;
            columnas.anterior = nuevo;
            columnas = nuevo;
        }
    }

    private void Insertar_Al_Final_Fila(CabeceraMatriz nuevo)
    {
        if(filas == null)
        {
            filas = nuevo;
        }
        else
        {
            CabeceraMatriz pivote = filas;
            while(pivote.siguiente != null)
            {
                pivote = pivote.siguiente;
            }
            nuevo.anterior = pivote;
            pivote.siguiente = nuevo;
        }
    }
    
    private void Insertar_Al_Final_Columna(CabeceraMatriz nuevo)
    {
        if(columnas == null)
        {
            columnas = nuevo;
        }
        else
        {
            CabeceraMatriz pivote = columnas;
            while(pivote.siguiente != null)
            {
                pivote = pivote.siguiente;
            }
            nuevo.anterior = pivote;
            pivote.siguiente = nuevo;
        }
    }
    
    private CabeceraMatriz Remover_Del_Frente_Fila()
    {
        if(filas != null)
        {
            CabeceraMatriz pivote = filas;
            if(filas.siguiente == null)
            {
                filas = null;
            }
            else
            {
                filas = filas.siguiente;
                filas.anterior = null;
            }
            return pivote;
        }
        return null;
    }
    
    private CabeceraMatriz Remover_Del_Frente_Columna()
    {
        if(columnas != null)
        {
            CabeceraMatriz pivote = columnas;
            if(columnas.siguiente == null)
            {
                columnas = null;
            }
            else
            {
                columnas = columnas.siguiente;
                columnas.anterior = null;
            }
            return pivote;
        }
        return null;
    }

    private CabeceraMatriz Remover_Del_Final_Fila()
    {
        if(filas != null)
        {
            CabeceraMatriz pivote = filas;
            if(pivote.siguiente == null)
            {
                filas = null;
            }
            else
            {
                CabeceraMatriz pivote2 = null;

                while(pivote.siguiente != null)
                {
                    pivote2 = pivote;
                    pivote = pivote.siguiente;
                }
                pivote2.siguiente = null;
            }
            return pivote;
        }
        return null;
    }

    private CabeceraMatriz Remover_Del_Final_Columna()
    {
        if(columnas != null)
        {
            CabeceraMatriz pivote = columnas;
            if(pivote.siguiente == null)
            {
                columnas = null;
            }
            else
            {
                CabeceraMatriz pivote2 = null;

                while(pivote.siguiente != null)
                {
                    pivote2 = pivote;
                    pivote = pivote.siguiente;
                }
                pivote2.siguiente = null;
            }
            return pivote;
        }
        return null;
    }

    public CabeceraMatriz Buscar_Fila(int criterio)
    {
        if(filas != null)
        {
            CabeceraMatriz pivote = filas;
            while(pivote != null)
            {
                if(pivote.fila_o_columna == criterio)
                {
                    return pivote;
                }
                pivote = pivote.siguiente;
            }
        }
        return null;
    }

    public CabeceraMatriz Buscar_Columna(int criterio)
    {
        if(columnas != null)
        {
            CabeceraMatriz pivote = columnas;
            while(pivote != null)
            {
                if(pivote.fila_o_columna == criterio)
                {
                    return pivote;
                }
                pivote = pivote.siguiente;
            }
        }
        return null;
    }

    public CabeceraMatriz Insertar_Fila(int criterio)
    {
        CabeceraMatriz aux = Buscar_Fila(criterio);

        if(aux != null)
        {
            return aux;
        }
        CabeceraMatriz nuevo = new CabeceraMatriz(criterio);
        CabeceraMatriz pivote = null;

        if(filas != null)
        {
            if(criterio < filas.fila_o_columna)
            {
                Insertar_Al_Frente_Fila(nuevo);
                return nuevo;
            }
            pivote = filas.siguiente;
            while(pivote != null)
            {
                if(criterio < pivote.fila_o_columna)
                {
                    pivote.anterior.siguiente = nuevo;
                    nuevo.anterior = pivote.anterior;

                    pivote.anterior = nuevo;
                    nuevo.siguiente = pivote;
                    return nuevo;
                }
                pivote = pivote.siguiente;
            }
            Insertar_Al_Final_Fila(nuevo);
            return nuevo;
        }
        Insertar_Al_Frente_Fila(nuevo);
        return nuevo;
    }

    public CabeceraMatriz Insertar_Columna(int criterio)
    {
        CabeceraMatriz aux = Buscar_Columna(criterio);

        if(aux != null)
        {
            return aux;
        }
        CabeceraMatriz nuevo = new CabeceraMatriz(criterio);
        CabeceraMatriz pivote = null;

        if(columnas != null)
        {
            if(criterio < columnas.fila_o_columna)
            {
                Insertar_Al_Frente_Columna(nuevo);
                return nuevo;
            }
            pivote = columnas.siguiente;
            while(pivote != null)
            {
                if(criterio < pivote.fila_o_columna)
                {
                    pivote.anterior.siguiente = nuevo;
                    nuevo.anterior = pivote.anterior;

                    pivote.anterior = nuevo;
                    nuevo.siguiente = pivote;
                    return nuevo;
                }
                pivote = pivote.siguiente;
            }
            Insertar_Al_Final_Columna(nuevo);
            return nuevo;
        }
        Insertar_Al_Frente_Columna(nuevo);
        return nuevo;
    }

    public void Remover_Fila(int criterio)
    {
        CabeceraMatriz aux = Buscar_Fila(criterio);

        if(aux != null && aux.primero == null)
        {
            if(aux == filas)
            {
                Remover_Del_Frente_Fila();
                return;
            }
            else if(aux.siguiente == null)
            {
                Remover_Del_Final_Fila();
                return;
            }
            aux.anterior.siguiente = aux.siguiente;
            aux.siguiente.anterior = aux.anterior;
        }
    }

    public void Remover_Columna(int criterio)
    {
        CabeceraMatriz aux = Buscar_Columna(criterio);

        if(aux != null && aux.primero == null)
        {
            if(aux == columnas)
            {
                Remover_Del_Frente_Fila();
                return;
            }
            else if(aux.siguiente == null)
            {
                Remover_Del_Final_Fila();
                return;
            }
            aux.anterior.siguiente = aux.siguiente;
            aux.siguiente.anterior = aux.anterior;
        }
    }
    /////////CABECERA//////////
}
