package com.example.edd;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author ozmarescobar
 */
public class Esencial
{
    private static final Random r = new Random(System.currentTimeMillis());
    public Esencial()
    {
    }
    
    public File Generar_Archivo(String texto, String ruta) throws IOException
    {
        if(ruta.length() > 0)
        {
            if(ruta.charAt(ruta.length() - 1) != '/')
            {
                File file = new File(ruta);
                File parent = file.getParentFile();
                if(parent != null)
                {
                    parent.mkdirs();
                }
                if(!file.exists())
                {
                    file.createNewFile();
                }
                if(file.isFile())
                {
                    PrintWriter pw = new PrintWriter(file);
                    pw.print(texto);
                    pw.close();
                }
                return file;
            }
        }
        return null;
    }
    
    public int randomizer(int min, int max)
    {
        return min + r.nextInt()%(max-min+1);
    }
}
