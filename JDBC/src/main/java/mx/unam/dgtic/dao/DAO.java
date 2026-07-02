package mx.unam.dgtic.dao;

import mx.unam.dgtic.modelo.EntidadAlumno;

import java.sql.*;

public class DAO {
    //jdbc:mysql://localhost:3306/modulo06
    private String url="jdbc:mariadb://127.0.0.1:3306/modulo6?serverTimezone=UTC";
    private String usuario="appuser";
    private String psw="MiPasswordSegura123!";
    private Connection conexion;

    public DAO() throws ClassNotFoundException, SQLException {
        //com.mysql.cj.jdbc.Driver
        Class.forName("org.mariadb.jdbc.Driver");
        conexion=DriverManager.getConnection(url,usuario,psw);
    }

    public EntidadAlumno getAlumno(String matricula) {
        EntidadAlumno entidadAlumno=null;
        try {
            String query="SELECT * FROM alumnos where matricula=?";
            PreparedStatement pstm=conexion.prepareStatement(query);
            pstm.setString(1,matricula);
            ResultSet datos=pstm.executeQuery();
            if(!datos.next()) {
                System.out.println("No hay datos");
            }else {
                do {
                    entidadAlumno=new EntidadAlumno();
                    entidadAlumno.setMatricula(datos.getString(1));
                    entidadAlumno.setNombre(datos.getString(2));
                    entidadAlumno.setPaterno(datos.getString(3));
                    entidadAlumno.setDate(datos.getDate(4));
                    entidadAlumno.setEstatura(datos.getFloat(5));
                }while(datos.next());
            }
            pstm.close();
            datos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entidadAlumno;
    }
    public void cerrarConexion() throws SQLException {
        conexion.close();
    }
}