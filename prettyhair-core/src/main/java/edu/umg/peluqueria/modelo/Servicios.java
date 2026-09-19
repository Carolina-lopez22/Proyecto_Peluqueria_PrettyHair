package edu.umg.peluqueria.modelo;

public class Servicios {
	private int idServicio;
	private String nombre;
	private double precio;
	
	public Servicios(int idServicio, String nombre, double precio) {
		super();
		this.idServicio = idServicio;
		this.nombre = nombre;
		this.precio = precio;
	}
	public Servicios(String nombre, double precio) {
        this(0, nombre, precio);
}
	public int getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	@Override
	public String toString() {
		return nombre + ", Precio:" + precio;
	}
}
