/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartelesdecinejavafx;

import java.io.Serializable;

/**
 *
 * @author Juan José Burgos Sánchez
 */
public class Cartel implements Serializable{
    
    /** Nombre de la película*/
    private String nombre;
    
    /** Año en que se estrenó la película*/
    private int anyo;
    
    /** Almacena la ruta donde está la imagen de la película*/
    private String ruta;
    
    
    //Métodos getter
    String getNombreCartel(){
        return this.nombre;
    }
    
    int getAnyoCartel(){
        return this.anyo;
    }
    
    String getRutaCartel(){
        return this.ruta;
    }
    
    //Métodos Setter
    public void setNombreCartel(String nombre){
        this.nombre = nombre;
    }
    
    public void setAnyoCartel(int anyo){
        this.anyo = anyo;
    }
    
    public void setRutaCartel(String ruta){
        this.ruta = ruta;
    }
    
    @Override
    public String toString() {
        return String.format ("%s || %d || %s",
                this.nombre, this.anyo, this.ruta);
    }
    
    

}
