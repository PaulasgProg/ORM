package entidades;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="proyecto")

public class Proyecto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "dni_jefe_proyecto", columnDefinition = "char[9]")
    private String dniJefeProyecto;

    @ManyToOne
    @JoinColumn(name = "dni_jefe_proyecto", referencedColumnName = "dni", insertable = false, updatable = false)
    private Empleado jefeProyecto;

	public Proyecto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Proyecto(String nombre, Date fechaInicio, Date fechaFin, String dniJefeProyecto) {
		super();
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.dniJefeProyecto = dniJefeProyecto;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDniJefeProyecto() {
		return dniJefeProyecto;
	}

	public void setDniJefeProyecto(String dniJefeProyecto) {
		this.dniJefeProyecto = dniJefeProyecto;
	}

	public Empleado getJefeProyecto() {
		return jefeProyecto;
	}

	public void setJefeProyecto(Empleado jefeProyecto) {
		this.jefeProyecto = jefeProyecto;
	}

	@Override
	public String toString() {
		return "Proyecto [id=" + id + ", nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", dniJefeProyecto=" + dniJefeProyecto + ", jefeProyecto=" + jefeProyecto + "]";
	}

}
