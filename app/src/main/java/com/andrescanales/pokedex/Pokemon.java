package com.andrescanales.pokedex;

/**
 * Created by andrescanales on 10/26/14.
 */
public class Pokemon {
    // In this class we just have a few setters and getters
    private String nombre;
    private String urlImage;

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getUrlImage(){
        return urlImage;
    }
    public void setUrlImage(String urlImage){
        this.urlImage = urlImage;
    }
}
