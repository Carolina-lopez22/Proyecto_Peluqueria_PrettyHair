package edu.umg.peluqueria.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.umg.peluqueria.conexion.Conexion;
import edu.umg.peluqueria.modelo.Servicios;
public class ServiciosDAO {
	
	    public Servicios crear(Servicios servicio) throws SQLException {
	        String sql = "INSERT INTO servicios (nombre, precio) VALUES (?, ?)";
	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(
	                     sql, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setString(1, servicio.getNombre());
	            statement.setDouble(2, servicio.getPrecio());
	            statement.executeUpdate();
	            // Obtener el ID generado automáticamente
	            try (ResultSet resultSet = statement.getGeneratedKeys()) {
	                if (resultSet.next()) {
	                    servicio.setIdServicio(resultSet.getInt(1));
	                }
	            }
	        }
	        return servicio;
	    }
	    // Obtener todos los servicios
	    public List<Servicios> listarTodos() throws SQLException {
	        List<Servicios> servicios = new ArrayList<>();
	        String sql = "SELECT id_servicio, nombre, precio FROM servicios";
	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(sql);
	             ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Servicios servicio = new Servicios(
	                    resultSet.getInt("id_servicio"),
	                    resultSet.getString("nombre"),
	                    resultSet.getDouble("precio")
	                );
	                servicios.add(servicio);
	            }
	        }
	        return servicios;
	    }
	    // Buscar un servicio por su ID
	    public Optional<Servicios> buscarPorId(int id) throws SQLException {
	        String sql = "SELECT id_servicio, nombre, precio "
	                   + "FROM servicios WHERE id_servicio = ?";
	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, id);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    Servicios servicio = new Servicios(
	                        resultSet.getInt("id_servicio"),
	                        resultSet.getString("nombre"),
	                        resultSet.getDouble("precio")
	                    );
	                    return Optional.of(servicio);
	                }
	            }
	        }
	        return Optional.empty();
	    }
	    // Actualizar un servicio existente
	    public boolean actualizar(Servicios servicio) throws SQLException {
	        String sql = "UPDATE servicios SET nombre = ?, precio = ? "
	                   + "WHERE id_servicio = ?";
	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, servicio.getNombre());
	            statement.setDouble(2, servicio.getPrecio());
	            statement.setInt(3, servicio.getIdServicio());
	            int filasAfectadas = statement.executeUpdate();
	            return filasAfectadas > 0;
	        }
	    }
	    // Eliminar un servicio por su ID
	    public boolean eliminar(int id) throws SQLException {
	        String sql = "DELETE FROM servicios WHERE id_servicio = ?";
	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setInt(1, id);
	            int filasAfectadas = statement.executeUpdate();
	            return filasAfectadas > 0;
	        }
	    }
	}