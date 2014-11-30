package com.andrescanales.pokedex;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andrescanales on 10/26/14.
 */
public class Pokemon implements Parcelable {
    // In this class we just have a few setters and getters
    private String nombre;
    private String avatar;

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getUrlImage(){ return avatar; }
    public void setUrlImage(String avatar){
        this.avatar = avatar;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.avatar);
    }

    public Pokemon() {
    }

    private Pokemon(Parcel in) {
        this.nombre = in.readString();
        this.avatar = in.readString();
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Parcelable.Creator<Pokemon>() {
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }

        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };
}
