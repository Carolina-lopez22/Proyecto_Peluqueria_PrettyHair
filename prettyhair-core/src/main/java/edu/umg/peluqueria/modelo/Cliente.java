package edu.umg.peluqueria.modelo;

public class Cliente {

	private int idCliente;
	private String nombre;
	private String telefono;
	private int visitasPrevias;
	
	public Cliente(int idCliente, String nombre, String telefono, int visitasPrevias) {
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.telefono = telefono;
		this.visitasPrevias = visitasPrevias;
	}
	public Cliente(String nombre, String telefono, int visitasPrevias) {
        this(0, nombre, telefono, visitasPrevias);
    }
	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public int getVisitasPrevias() {
	    return visitasPrevias;
	}

	public void setVisitasPrevias(int visitasPrevias) {
	    this.visitasPrevias = visitasPrevias;
	}
	@Override
	public String toString() {
		return nombre;
	}
}
