package dgtic.unam.mx.core.m5demoweb.modelo;

import org.springframework.stereotype.Component;

@Component
public class Automovil {
    private String marca;
    private String modelo;
    private String foto;

    @Override
    public String toString() {
        return "Automovil{" +
                "marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }

    public Automovil() {
    }

    public Automovil(String marca, String modelo, String foto) {
        this.marca = marca;
        this.modelo = modelo;
        this.foto = foto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
