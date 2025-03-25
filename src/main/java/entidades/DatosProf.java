package entidades;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="datos_profesionales")

public class DatosProf {
	public enum Categoria{A,B,C,D}
	
	/*dni char(9) not null,
    categoria enum('A', 'B', 'C', 'D') not null,
    sueldo_bruto decimal (8,2),
    primary key (dni),
    foreign key (dni) references empleado(dni)*/

	@Id
	@Column(name="dni",columnDefinition = "char[9]")
	private String dni;
	
	@Column(name = "categoria", columnDefinition = "enum('A', 'B', 'C', 'D')")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
	
	@Column(name = "sueldo_bruto")
	private BigDecimal sueldo;
	
	@OneToOne
    @JoinColumn(name = "dni", referencedColumnName = "dni", insertable = false, updatable = false)
    private Empleado empleado;

	public DatosProf() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DatosProf(String dni, Categoria categoria, BigDecimal sueldo, Empleado empleado) {
		super();
		this.dni = dni;
		this.categoria = categoria;
		this.sueldo = sueldo;
		this.empleado = empleado;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getSueldo() {
		return sueldo;
	}

	public void setSueldo(BigDecimal sueldo) {
		this.sueldo = sueldo;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	@Override
	public String toString() {
		return "DatosProf [dni=" + dni + ", categoria=" + categoria + ", sueldo=" + sueldo + ", empleado=" + empleado
				+ "]";
	}
	
	
	
	
	
}

