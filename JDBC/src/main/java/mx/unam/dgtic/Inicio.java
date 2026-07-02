package mx.unam.dgtic;

import mx.unam.dgtic.service.ServicioDTO;

import java.sql.SQLException;

public class Inicio {
    public static void main(String[] args) {
        try {
            ServicioDTO servicioDTO=new ServicioDTO();
            servicioDTO.alumnoID("5A");
            servicioDTO.cerrar();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}