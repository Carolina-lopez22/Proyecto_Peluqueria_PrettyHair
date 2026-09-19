package edu.umg.peluqueria.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.umg.peluqueria.conexion.Conexion;
import edu.umg.peluqueria.modelo.Cliente;
public class ClienteDAO {
	
	public Cliente crear(Cliente cliente) throws SQLException {
	    
	    String sql = "INSERT INTO clientes (nombre, telefono) VALUES (?, ?)";
	    
	    try (Connection connection = Conexion.conectar();
	         PreparedStatement statement = connection.prepareStatement(sql, 
	                 java.sql.Statement.RETURN_GENERATED_KEYS)) {
	        
	        statement.setString(1, cliente.getNombre());
	        statement.setString(2, cliente.getTelefono());
	        
	        statement.executeUpdate();
	        
	        try (ResultSet resultSet = statement.getGeneratedKeys()) {
	            if (resultSet.next()) {
	                cliente.setIdCliente(resultSet.getInt(1));
	            }
	        }
	    }
	    
	    return cliente;
	}
	public List<Cliente> listarTodos() throws SQLException {

	    List<Cliente> clientes = new ArrayList<>();

	    String sql = "SELECT id_cliente, nombre, telefono FROM clientes";

	    try (Connection connection = Conexion.conectar();
	         PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {

	            Cliente cliente = new Cliente(
	                resultSet.getInt("id_cliente"),
	                resultSet.getString("nombre"),
	                resultSet.getString("telefono")
	            );

	            clientes.add(cliente);
	        }
	    }
	    return clientes;
	}

	    // Buscar un cliente por su ID
	    public Optional<Cliente> buscarPorId(int id) throws SQLException {

	        String sql = "SELECT id_cliente, nombre, telefono "
	                   + "FROM clientes WHERE id_cliente = ?";

	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setInt(1, id);

	            try (ResultSet resultSet = statement.executeQuery()) {

	                if (resultSet.next()) {

	                    Cliente cliente = new Cliente(
	                        resultSet.getInt("id_cliente"),
	                        resultSet.getString("nombre"),
	                        resultSet.getString("telefono")
	                    );

	                    return Optional.of(cliente);
	                }
	            }
	        }

	        return Optional.empty();
	    }

	    // Actualizar un cliente existente
	    public boolean actualizar(Cliente cliente) throws SQLException {

	        String sql = "UPDATE clientes SET nombre = ?, telefono = ? "
	                   + "WHERE id_cliente = ?";

	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setString(1, cliente.getNombre());
	            statement.setString(2, cliente.getTelefono());
	            statement.setInt(3, cliente.getIdCliente());

	            int filasAfectadas = statement.executeUpdate();

	            return filasAfectadas > 0;
	        }
	    }

	    // Eliminar un cliente por su ID
	    public boolean eliminar(int id) throws SQLException {

	        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setInt(1, id);

	            int filasAfectadas = statement.executeUpdate();

	            return filasAfectadas > 0;
	        }
	    }
}
