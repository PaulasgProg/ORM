package entidades;

import java.io.Serializable;
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
@Table(name="asig_proyecto")
public class AsigProyecto{
	/*dni_emp char(9) not null,
    id_proyecto integer not null,
    fecha_inicio date not null,
    fecha_fin date,
    primary key (dni_emp, id_proyecto, fecha_inicio),
    foreign key (dni_emp) references empleado(dni),
    foreign key (id_proyecto) references proyecto(id)*/
	

    @Column(name = "dni_emp", columnDefinition = "char(9)", nullable = false)
    private String dniEmpleado;

    @Column(name = "id_proyecto", nullable = false)
    private Integer idProyecto;

    @Id
    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;
	
	@ManyToOne
	@JoinColumn(name = "dni_emp", referencedColumnName = "dni", insertable = false, updatable = false)
	private Empleado empleado;

	@ManyToOne
	@JoinColumn(name = "id_proyecto", referencedColumnName = "id", insertable = false, updatable = false)
	private Proyecto proyecto;

	public AsigProyecto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AsigProyecto(String dniEmpleado, Integer idProyecto, Date fechaInicio, Empleado empleado,
			Proyecto proyecto) {
		super();
		this.dniEmpleado = dniEmpleado;
		this.idProyecto = idProyecto;
		this.fechaInicio = fechaInicio;
		this.empleado = empleado;
		this.proyecto = proyecto;
	}

	public String getDniEmpleado() {
		return dniEmpleado;
	}

	public void setDniEmpleado(String dniEmpleado) {
		this.dniEmpleado = dniEmpleado;
	}

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
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

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Override
	public String toString() {
		return "AsigProyecto [dniEmpleado=" + dniEmpleado + ", idProyecto=" + idProyecto + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", empleado=" + empleado + ", proyecto=" + proyecto + "]";
	}
	
 

	


}
