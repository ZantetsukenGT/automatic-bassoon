package com.example.edd;

/**
 *
 * @author ozmarescobar
 */
public class CursoPensum
{
    public String nombre;
    public int codigo;
    public int creditos;
    public int[] pre;
    public String area;
    
    public CursoPensum()
    {
        nombre = "NOT_SET";
        area = "NOT_SET";
        codigo = -1;
        creditos = -1;
        pre = new int[0];
    }

    public CursoPensum(String nombre, int codigo, int creditos, int[] pre, String area)
    {
        this.nombre = nombre;
        this.codigo = codigo;
        this.creditos = creditos;
        this.pre = pre;
        this.area = area;
    }

}
