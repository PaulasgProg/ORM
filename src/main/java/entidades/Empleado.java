package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="empleado")
public class Empleado {

	@Id
	@Column(name="dni", columnDefinition = "char[9]")
	private String dni;
	
	@Column(name = "nombre")
    private String nombre;

	public Empleado() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Empleado(String dni, String nombre) {
		super();
		this.dni = dni;
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Empleado [dni=" + dni + ", nombre=" + nombre + "]";
	}
	
	
	
}
