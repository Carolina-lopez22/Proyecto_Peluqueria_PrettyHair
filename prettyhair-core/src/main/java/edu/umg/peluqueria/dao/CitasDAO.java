package edu.umg.peluqueria.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.umg.peluqueria.conexion.Conexion;
import edu.umg.peluqueria.modelo.Citas;
public class CitasDAO {
	
	    public Citas crear(Citas cita) throws SQLException {

	        String sql = "INSERT INTO citas "
	                   + "(id_cliente, id_servicio, fecha_hora, duracion, estado) "
	                   + "VALUES (?, ?, ?, ?, ?)";

	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(
	                     sql, Statement.RETURN_GENERATED_KEYS)) {

	            statement.setInt(1, cita.getIdCliente());
	            statement.setInt(2, cita.getIdServicio());
	            statement.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora()));
	            statement.setInt(4, cita.getDuracion());
	            statement.setString(5, cita.getEstado());

	            statement.executeUpdate();

	            // Obtener el ID generado automáticamente
	            try (ResultSet resultSet = statement.getGeneratedKeys()) {

	                if (resultSet.next()) {
	                    cita.setIdCita(resultSet.getInt(1));
	                }
	            }
	        }

	        return cita;
	    }

	    // Obtener todas las citas
	    public List<Citas> listarTodos() throws SQLException {

	        List<Citas> citas = new ArrayList<>();

	        String sql = "SELECT id_cita, id_cliente, id_servicio, "
	                   + "fecha_hora, duracion, estado FROM citas";

	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(sql);
	             ResultSet resultSet = statement.executeQuery()) {

	            while (resultSet.next()) {

	                Citas cita = new Citas(
	                    resultSet.getInt("id_cita"),
	                    resultSet.getInt("id_cliente"),
	                    resultSet.getInt("id_servicio"),
	                    resultSet.getTimestamp("fecha_hora").toLocalDateTime(),
	                    resultSet.getInt("duracion"),
	                    resultSet.getString("estado")
	                );

	                citas.add(cita);
	            }
	        }

	        return citas;
	    }

	    // Buscar una cita por su ID
	    public Optional<Citas> buscarPorId(int id) throws SQLException {

	        String sql = "SELECT id_cita, id_cliente, id_servicio, "
	                   + "fecha_hora, duracion, estado "
	                   + "FROM citas WHERE id_cita = ?";

	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setInt(1, id);

	            try (ResultSet resultSet = statement.executeQuery()) {

	                if (resultSet.next()) {

	                    Citas cita = new Citas(
	                        resultSet.getInt("id_cita"),
	                        resultSet.getInt("id_cliente"),
	                        resultSet.getInt("id_servicio"),
	                        resultSet.getTimestamp("fecha_hora").toLocalDateTime(),
	                        resultSet.getInt("duracion"),
	                        resultSet.getString("estado")
	                    );

	                    return Optional.of(cita);
	                }
	            }
	        }

	        return Optional.empty();
	    }

	    // Actualizar una cita existente
	    public boolean actualizar(Citas cita) throws SQLException {

	        String sqlBuscar = "SELECT estado FROM citas WHERE id_cita = ?";

	        String sqlActualizar = "UPDATE citas SET id_cliente = ?, "
	                + "id_servicio = ?, fecha_hora = ?, duracion = ?, estado = ? "
	                + "WHERE id_cita = ?";

	        try (Connection connection = Conexion.conectar()) {

	            // Obtener el estado anterior de la cita
	            String estadoAnterior = null;

	            try (PreparedStatement statementBuscar =
	                         connection.prepareStatement(sqlBuscar)) {

	                statementBuscar.setInt(1, cita.getIdCita());

	                try (ResultSet resultSet = statementBuscar.executeQuery()) {

	                    if (resultSet.next()) {
	                        estadoAnterior = resultSet.getString("estado");
	                    }
	                }
	            }

	            // Actualizar la cita
	            try (PreparedStatement statement =
	                         connection.prepareStatement(sqlActualizar)) {

	                statement.setInt(1, cita.getIdCliente());
	                statement.setInt(2, cita.getIdServicio());
	                statement.setTimestamp(
	                        3,
	                        Timestamp.valueOf(cita.getFechaHora())
	                );
	                statement.setInt(4, cita.getDuracion());
	                statement.setString(5, cita.getEstado());
	                statement.setInt(6, cita.getIdCita());

	                int filasAfectadas = statement.executeUpdate();

	                // Si la cita acaba de pasar a completada,
	                // aumentar las visitas del cliente
	                if (filasAfectadas > 0
	                        && !"completada".equals(estadoAnterior)
	                        && "completada".equals(cita.getEstado())) {

	                    String sqlVisitas =
	                            "UPDATE clientes "
	                            + "SET visitas_previas = visitas_previas + 1 "
	                            + "WHERE id_cliente = ?";

	                    try (PreparedStatement statementVisitas =
	                                 connection.prepareStatement(sqlVisitas)) {

	                        statementVisitas.setInt(
	                                1,
	                                cita.getIdCliente()
	                        );

	                        statementVisitas.executeUpdate();
	                    }
	                }

	                return filasAfectadas > 0;
	            }
	        }
	    }

	    // Eliminar una cita por su ID
	    public boolean eliminar(int id) throws SQLException {

	        String sql = "DELETE FROM citas WHERE id_cita = ?";

	        try (Connection connection = Conexion.conectar();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setInt(1, id);

	            int filasAfectadas = statement.executeUpdate();

	            return filasAfectadas > 0;
	        }
	    }
	}
