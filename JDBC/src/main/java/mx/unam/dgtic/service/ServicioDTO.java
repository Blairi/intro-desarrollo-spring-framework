package mx.unam.dgtic.service;

import mx.unam.dgtic.dao.DAO;
import mx.unam.dgtic.modelo.EntidadAlumno;

import java.sql.SQLException;

public class ServicioDTO {
    DAO dao;

    public ServicioDTO() throws SQLException, ClassNotFoundException {
        dao=new DAO();
    }
    public void alumnoID(String matricula){
        EntidadAlumno alumno=dao.getAlumno(matricula);
        System.out.println("Nombre:"+alumno.getNombre());
        System.out.println("Apellido: "+alumno.getPaterno());
    }
    public void cerrar() throws SQLException {
        dao.cerrarConexion();
    }
}