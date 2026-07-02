package mx.unam.dgtic.dao;

import mx.unam.dgtic.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UsuarioDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long guardar(Usuario usuario) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO usuario (nombre, email, edad) VALUES (?, ?, ?)";
        jdbcTemplate.update(conexion->{
            PreparedStatement psm=conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psm.setString(1,usuario.getNombre());
            psm.setString(2,usuario.getEmail());
            psm.setInt(3,usuario.getEdad());
            return psm;
        },keyHolder);
        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
    }

    public int actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre=?, email=?, edad=? WHERE id=?";
        return jdbcTemplate.update(sql,
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getEdad(),
                usuario.getId());
    }

    public int eliminar(Long id){
        String sql="DELETE FROM usuario WHERE id=?";
        return jdbcTemplate.update(sql,id);
    }

    public Usuario buscarPorId(Long id){
        String sql="SELECT * FROM usuario WHERE id=?";
        return jdbcTemplate.queryForObject(sql,
                new Object[]{id}, new UsuarioRowMapper());
    }

    public List<Usuario> listarTodos(){
        String sql="SELECT * FROM usuario";
        return jdbcTemplate.query(sql,new UsuarioRowMapper());
    }
}