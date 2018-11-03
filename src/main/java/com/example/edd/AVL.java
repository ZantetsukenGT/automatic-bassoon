package com.example.edd;

import java.io.IOException;

/**
 *
 * @author ozmarescobar
 */
public class AVL {
    
    public NodoAVL raiz;
    public ListaDoble cola_busqueda;
    public int report_dummy;
    
    public AVL()
    {
        raiz = null;
        cola_busqueda = new ListaDoble();
        report_dummy = 0;
    }
    
    public AVL(TutorAcademico data)
    {
        raiz = new NodoAVL(data);
        cola_busqueda = new ListaDoble();
        report_dummy = 0;
    }

    public NodoAVL Buscar(int criterio)
    {
        return AyudanteBuscar(raiz, criterio);
    }
    
    public void Buscar(String criterio)
    {
        AyudantePreOrderBusqueda(raiz, criterio);
    }
    
    private NodoAVL BuscarPadre(int criterio)
    {
        return AyudanteBuscarPadre(raiz, null, criterio);
    }
    
    public NodoAVL Insertar(TutorAcademico data)
    {
        if(raiz == null)
        {
            raiz = new NodoAVL(data);
            return raiz;
        }
        return AyudanteInsertar(raiz, data);
    }

    public void Modificar(TutorAcademico nuevo, int viejo)
    {
        NodoAVL old = Remover(viejo);
        Insertar(nuevo);
    }
    
    public NodoAVL Remover(int criterio)
    {
        NodoAVL remover = Buscar(criterio);
        NodoAVL padreRemover = BuscarPadre(remover.Obtener_Clave());
        if(remover.izquierda != null && remover.derecha != null)
        {
            
            if(remover == raiz)
            {
                NodoAVL menorDeMayores = BuscarMenorDeMayores(raiz.derecha);
                Remover(menorDeMayores.Obtener_Clave());
                
                menorDeMayores.izquierda = remover.izquierda;
                menorDeMayores.derecha = remover.derecha;
                raiz = menorDeMayores;
            }
            else if(remover.Obtener_Clave() < padreRemover.Obtener_Clave())
            {
                NodoAVL menorDeMayores = BuscarMenorDeMayores(remover.derecha);
                Remover(menorDeMayores.Obtener_Clave());
                
                menorDeMayores.izquierda = remover.izquierda;
                menorDeMayores.derecha = remover.derecha;
                padreRemover.izquierda = menorDeMayores;
            }
            else
            {
                NodoAVL menorDeMayores = BuscarMenorDeMayores(remover.derecha);
                Remover(menorDeMayores.Obtener_Clave());
                
                menorDeMayores.izquierda = remover.izquierda;
                menorDeMayores.derecha = remover.derecha;
                padreRemover.derecha = menorDeMayores;
            }
        }
        else if(remover.izquierda == null && remover.derecha != null)
        {
            if(remover == raiz)
            {
                raiz = raiz.derecha;
            }
            else if(remover.Obtener_Clave() < padreRemover.Obtener_Clave())
            {
                padreRemover.izquierda = remover.derecha;
            }
            else
            {
                padreRemover.derecha = remover.derecha;
            }
        }
        else if(remover.izquierda != null && remover.derecha == null)
        {
            if(remover == raiz)
            {
                raiz = raiz.izquierda;
            }
            else if(remover.Obtener_Clave() < padreRemover.Obtener_Clave())
            {
                padreRemover.izquierda = remover.izquierda;
            }
            else
            {
                padreRemover.derecha = remover.izquierda;
            }
        }
        else
        {
            if(remover == raiz)
            {
                raiz = null;
            }
            else if(remover.Obtener_Clave() < padreRemover.Obtener_Clave())
            {
                padreRemover.izquierda = null;
            }
            else
            {
                padreRemover.derecha = null;
            }
        }
        return remover;
    }
    
    
    private NodoAVL BuscarMenorDeMayores(NodoAVL raiz)
    {
        if(raiz.izquierda == null) return raiz;
        return BuscarMenorDeMayores(raiz.izquierda);
    }
    
    private NodoAVL AyudanteInsertar(NodoAVL raiz, TutorAcademico data)
    {
        if(raiz == null)
        {
            return new NodoAVL(data);
        }
        else if(data.tutor.Carnet < raiz.Obtener_Clave())
        {
            raiz.izquierda = AyudanteInsertar(raiz.izquierda, data);
            raiz = Balancear(raiz);
        }
        else if(data.tutor.Carnet > raiz.Obtener_Clave())
        {
            raiz.derecha = AyudanteInsertar(raiz.derecha, data);
            raiz = Balancear(raiz);
        }
        return raiz;
    }
    private NodoAVL AyudanteBuscar(NodoAVL raiz, int criterio)
    {
        if(raiz == null) return null;
        if(criterio < raiz.Obtener_Clave())
        {
            return AyudanteBuscar(raiz.izquierda, criterio);
        }
        if(criterio > raiz.Obtener_Clave())
        {
            return AyudanteBuscar(raiz.derecha, criterio);
        }
        return raiz;
    }
    
    private NodoAVL AyudanteBuscarPadre(NodoAVL raiz, NodoAVL padre, int criterio)
    {
        if(raiz == null) return padre;
        if(criterio < raiz.Obtener_Clave())
        {
            return AyudanteBuscarPadre(raiz.izquierda, raiz,  criterio);
        }
        if(criterio > raiz.Obtener_Clave())
        {
            return AyudanteBuscarPadre(raiz.derecha, raiz, criterio);
        }
        return padre;
    }
    
    public int Calcular_Altura(NodoAVL raiz)
    {
        if(raiz == null)
        {
            return 0;
        }
        int alturaIzq = Calcular_Altura(raiz.izquierda);
        int alturaDer = Calcular_Altura(raiz.derecha);
        if(alturaIzq > alturaDer)
        {
            return alturaIzq + 1;
        }
        else
        {
            return alturaDer + 1;
        }
    }
    
    private void Recalcular_Factor_Equilibrio(NodoAVL raiz)
    {
        raiz.factor_equilibrio = Calcular_Altura(raiz.derecha) - Calcular_Altura(raiz.izquierda);
    }
    
    private NodoAVL Balancear(NodoAVL raizZ)
    {
        Recalcular_Factor_Equilibrio(raizZ);//si no entra a ninguno de los if, todavia entra en los valores permitidos
        
        if(raizZ.factor_equilibrio < -1)//desequilibrio hacia la izquierda
        {
            if(raizZ.izquierda.factor_equilibrio < 0)//desequilibrio total hacia la izquierda
            {
                return Rotacion_Izquierda_Izquierda(raizZ);
            }
            else if(raizZ.izquierda.factor_equilibrio > 0)//desequilibrio izq luego derecha
            {
                return Rotacion_Izquierda_Derecha(raizZ);
            }
        }
        else if(raizZ.factor_equilibrio > 1)//desequilibrio hacia la derecha
        {
            if(raizZ.derecha.factor_equilibrio > 0)//desequilibrio total hacia la derecha
            {
                return Rotacion_Derecha_Derecha(raizZ);
            }
            else if(raizZ.derecha.factor_equilibrio < 0)//desequilibrio derecha luego izq
            {
                return Rotacion_Derecha_Izquierda(raizZ);
            }
        }
        return raizZ;
    }
    private NodoAVL Rotacion_Izquierda_Izquierda(NodoAVL raizZ)
    {
        NodoAVL padreZ = BuscarPadre(raizZ.Obtener_Clave());
        NodoAVL raizY = raizZ.izquierda;
        NodoAVL raizX = raizY.izquierda;
        
        raizZ.izquierda = raizY.derecha;
        raizY.derecha = raizZ;
        
        
        if(raizZ == this.raiz)
        {
            this.raiz = raizY;
        }
        else if(padreZ.derecha == raizZ)
        {
            padreZ.derecha = raizY;
        }
        else if(padreZ.izquierda == raizZ)
        {
            padreZ.izquierda = raizY;
        }
        Recalcular_Factor_Equilibrio(raizZ);
        Recalcular_Factor_Equilibrio(raizY);
        Recalcular_Factor_Equilibrio(raizX);
        return raizY;
    }
    private NodoAVL Rotacion_Derecha_Derecha(NodoAVL raizZ)
    {
        NodoAVL padreZ = BuscarPadre(raizZ.Obtener_Clave());
        NodoAVL raizY = raizZ.derecha;
        NodoAVL raizX = raizY.derecha;
        
        raizZ.derecha = raizY.izquierda;
        raizY.izquierda = raizZ;
        
        
        if(raizZ == this.raiz)
        {
            this.raiz = raizY;
        }
        else if(padreZ.derecha == raizZ)
        {
            padreZ.derecha = raizY;
        }
        else if(padreZ.izquierda == raizZ)
        {
            padreZ.izquierda = raizY;
        }
        Recalcular_Factor_Equilibrio(raizZ);
        Recalcular_Factor_Equilibrio(raizY);
        Recalcular_Factor_Equilibrio(raizX);
        return raizY;
    }
    private NodoAVL Rotacion_Izquierda_Derecha(NodoAVL raizZ)
    {
        NodoAVL raizY = raizZ.izquierda;
        NodoAVL raizX = raizY.derecha;
        
        raizY.derecha = raizX.izquierda;
        raizX.izquierda = raizY;
        raizZ.izquierda = raizX;
        
        return Rotacion_Izquierda_Izquierda(raizZ);
    }
    private NodoAVL Rotacion_Derecha_Izquierda(NodoAVL raizZ)
    {
        NodoAVL raizY = raizZ.derecha;
        NodoAVL raizX = raizY.izquierda;
        
        raizY.izquierda = raizX.derecha;
        raizX.derecha = raizY;
        raizZ.derecha = raizX;
        
        return Rotacion_Derecha_Derecha(raizZ);
    }
    
    public String Graficar(String ruta) throws InterruptedException, IOException
    {
	String texto = "digraph G { dpi = 100; size = 30;  rankdir = TB; node[shape=box]; \n\n ";
	texto += PreOrderFormat();
	texto+= "\n\n";
        report_dummy = 0;
	texto+= PreOrderLink();
        report_dummy = 0;
	texto+= "} ";
        Esencial esencial = new Esencial();

        esencial.Generar_Archivo(texto, ruta);
        Runtime.getRuntime().exec("dot -Tpng " + ruta + " -O");

        return ruta + ".png";
    }
    
    private String PreOrderFormat()
    {
        String text = "\tAVL;\n";
        if(raiz != null)
            text += AyudantePreOrderFormat(raiz);
        return text;
    }
    
    private String PreOrderLink()
    {
        String text = "";
        if(raiz != null)
        {
            text += "\tAVL->\"N" + raiz.Obtener_Clave() + "\";\n";
            text += AyudantePreOrderLink(raiz);
        }
        return text;
    }
    
    private String AyudantePreOrderFormat(NodoAVL raiz)
    {
        String a = "";
        if(raiz == null) 
        {
            a += "\t\"D" + report_dummy++ + "\"[style = invis];\n";
            return a;
        }
        a += VisitarFormatear(raiz);
        a += AyudantePreOrderFormat(raiz.izquierda);
        a += AyudantePreOrderFormat(raiz.derecha);
        return a;
    }
    
    private void AyudantePreOrderBusqueda(NodoAVL raiz, String criterio)
    {
        if(raiz == null) return;
        
        VisitarBusqueda(raiz, criterio);
        AyudantePreOrderBusqueda(raiz.izquierda, criterio);
        AyudantePreOrderBusqueda(raiz.derecha, criterio);
    }
    
    private String AyudantePreOrderLink(NodoAVL raiz)
    {
        String a = "";
        if(raiz == null) return a;
        a += VisitarLinkear(raiz);
        a += AyudantePreOrderLink(raiz.izquierda);
        a += AyudantePreOrderLink(raiz.derecha);
        return a;
    }
    private void VisitarBusqueda(NodoAVL raiz, String criterio)
    {
        TutorAcademico actual = raiz.Get_Data();
        String text = actual.tutor.Nombre + " " + actual.tutor.Apellido;
        if(text.contains(criterio))
        {
            cola_busqueda.InsertarAlFinal(actual);
        }
    }
    private String VisitarFormatear(NodoAVL raiz)
    {
        String clave = "" + raiz.Obtener_Clave();
        TutorAcademico pivote = raiz.Get_Data();
        String text = "\t\"N" + clave + "\"[label=\"Periodo: " + pivote.periodo + "\\n______\\n"
                + "Codigo: " + pivote.curso.codigo + "\\nNombre: " + pivote.curso.nombre + "\\n______\\n"
                + "Carnet: " + pivote.tutor.Carnet + "\\nNombre: " + pivote.tutor.Nombre + "\\nApellidos: " + pivote.tutor.Apellido + "\\nCreditos: " + pivote.tutor.Creditos + "\"];\n";
        return text;
    }
    private String VisitarLinkear(NodoAVL raiz)
    {
        String text = "";
        
        if(raiz.izquierda != null)
        {
            text += "\t\"N" + raiz.Obtener_Clave();
            text += "\"->\"N" + raiz.izquierda.Obtener_Clave() + "\";\n";
        }
        else
        {
            text += "\t\"N" + raiz.Obtener_Clave();
            text += "\"->\"D" + report_dummy++ + "\";\n"; 
        }
        if (raiz.derecha != null)
        {
            text += "\t\"N" + raiz.Obtener_Clave();
            text += "\"->\"N" + raiz.derecha.Obtener_Clave() + "\";\n";
        }
        else
        {
            text += "\t\"N" + raiz.Obtener_Clave();
            text += "\"->\"D" + report_dummy++ + "\";\n"; 
        }
        return text;
    }
}
