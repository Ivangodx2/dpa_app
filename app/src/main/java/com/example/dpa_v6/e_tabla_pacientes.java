package com.example.dpa_v6;

public class e_tabla_pacientes {

    private String nombre;
    private String edad;
    private String num_telefonico;

    public e_tabla_pacientes(){

    }

    public e_tabla_pacientes(String nombre,String edad,String num_telefonico){
        this.nombre=nombre;
        this.edad=edad;
        this.num_telefonico=num_telefonico;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getnum_telefonico() {
        return num_telefonico;
    }

    public void setnum_telefonico(String num_telefonico) {
        this.num_telefonico = num_telefonico;
    }
}
