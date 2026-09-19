package edu.umg.peluqueria.modelo;
import java.time.LocalDateTime;

public class Citas {
	private int idCita;
	private int idCliente;
	private int idServicio;
	private LocalDateTime fechahora;
	private int duracion;
	private String estado;
	public Citas(int idCita, int idCliente, int idServicio, LocalDateTime fechahora, int duracion, String estado) {
		super();
		this.idCita = idCita;
		this.idCliente = idCliente;
		this.idServicio = idServicio;
		this.fechahora = fechahora;
		this.duracion = duracion;
		this.estado = estado;
	}
public Citas (int idCliente, int idServicio, LocalDateTime fechahora, int duracion, String estado) {
    this(0, idCliente, idServicio, fechahora, duracion, estado);
}
public int getIdCita() {
	return idCita;
}
public void setIdCita(int idCita) {
	this.idCita = idCita;
}
public int getIdCliente() {
	return idCliente;
}
public void setIdCliente(int idCliente) {
	this.idCliente = idCliente;
}
public int getIdServicio() {
	return idServicio;
}
public void setIdServicio(int idServicio) {
	this.idServicio = idServicio;
}
public LocalDateTime getFechaHora() {
	return fechahora;
}
public void setFechaHora(LocalDateTime fechahora) {
	this.fechahora = fechahora;
}
public void setDuracion(int duracion) {
	this.duracion = duracion;
}
public int getDuracion() {
	return duracion;
}
public String getEstado() {
	return estado;
}
public void setEstado(String estado) {
	this.estado = estado;
}
@Override
public String toString() {
	return "Citas=" + idCita + ", idCliente=" + idCliente + ", idServicio=" + idServicio + ", fechahora="
			+ fechahora + ", duracion=" + duracion + ", estado=" + estado;
}

}
