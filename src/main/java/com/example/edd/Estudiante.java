package com.example.edd;

/**
 *
 * @author ozmarescobar
 */
public class Estudiante
{
    public int Carnet;
    public String DPI;
    public String Nombre;
    public String Apellido;
    public String Email;
    public String Token;
    public String Password;
    public int Creditos;
    public ListaDoble Cursos_Aprobados;

    public Estudiante()
    {
        Carnet = -1;
        DPI = "NOT_SET";
        Creditos = -1;
        Password = "NOT_SET";
        Token = "NOT_SET";
        Nombre = "NOT_SET";
        Apellido = "NOT_SET";
        Email = "NOT_SET";
        Cursos_Aprobados = new ListaDoble();
    }

    public Estudiante(int Carnet, String DPI, String Nombre, String Apellido, String Email, String Token, String Password, int Creditos)
    {
        this.Carnet = Carnet;
        this.DPI = DPI;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Email = Email;
        this.Token = Token;
        this.Password = Password;
        this.Creditos = Creditos;
        Cursos_Aprobados = new ListaDoble();
    }

    @Override
    public String toString()
    {
        String result = "\"" + this.hashCode() + "\"[label = \""
                + "Carnet: " + Carnet + "\\n"
                + "DPI: " + DPI + "\\n"
                + "Nombre: " + Nombre + "\\n"
                + "Apellido: " + Apellido + "\\n"
                + "Email: " + Email + "\\n"
                + "Token: " + Token + "\\n"
                + "Password: " + Password + "\\n"
                + "Creditos: " + Creditos
                + "\"];\n";
        NodoLista pivote = Cursos_Aprobados.primero;
        if(pivote != null)
        {
            result += "\"" + this.hashCode() + "\"->\"" + pivote.data.hashCode() + "\";\n";
            result += "subgraph cursos\n{\n";
            result += "rank = \"same\";\n";
            while(pivote != null)
            {
                result += pivote.data.toString() + "\n";
                pivote = pivote.siguiente;
            }
            pivote = Cursos_Aprobados.primero; 
            while(pivote != null)
            {
                if(pivote.siguiente != null)
                {
                    result += "\"" + pivote.data.hashCode() + "\"->";
                }
                else
                {
                    result += "\"" + pivote.data.hashCode() + "\";\n";
                }
                pivote = pivote.siguiente;
            }
            result += "}";
        }
        return result;
    }

}
