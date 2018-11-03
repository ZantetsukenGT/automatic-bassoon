package com.example.edd;

/**
 *
 * @author ozmarescobar
 */
public class NodoAVL {
    public NodoAVL izquierda;
    public NodoAVL derecha;
    private TutorAcademico tutor;
    public int factor_equilibrio;
    
    public NodoAVL()
    {
        this.izquierda = this.derecha = null;
        this.tutor = null;
        this.factor_equilibrio = 0;
    }
    public NodoAVL(TutorAcademico tutor)
    {
        this.izquierda = this.derecha = null;
        this.tutor = tutor;
        this.factor_equilibrio = 0;
    }

    public int Obtener_Clave()
    {
        return this.tutor.tutor.Carnet;
    }
    public TutorAcademico Get_Data()
    {
        return this.tutor;
    }
}
