package repositorios;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entidades.AsigProyecto;
import entidades.DatosProf;
import entidades.DatosProf.Categoria;
import entidades.Empleado;
import entidades.Proyecto;

public class ProyectoRepositorio {

	
	private Session sesion;
	static Scanner sc=new Scanner(System.in);

	public ProyectoRepositorio(Session sesion) {
		super();
		this.sesion = sesion;
	}

	/*id integer auto_increment not null,
    nombre varchar(35) not null,
    fecha_inicio date not null,
    fecha_fin date,
    dni_jefe_proyecto char(9) not null,
    primary key (id),
    foreign key (dni_jefe_proyecto) references empleado(dni)*/
	
	public boolean validarDNI(String dni) {
		// Patrón que coincide con un DNI 
        String patronDNI = "\\d{8}[A-HJ-NP-TV-Z]";

        // Verificar si el DNI coincide con el patrón
        return dni.matches(patronDNI);
	}
	
	public void borrarProyecto(int id) {
		Transaction t=null;
		try {
			if(verificarId(id)) {
				Proyecto p=ObtenerProyecto(id);
				t=this.sesion.beginTransaction();	
				this.sesion.delete(p);
				t.commit();
				System.out.println("Proyecto borrado");
			}else {
				System.out.println("No existe el proyecto");
				if(t!=null) {
					t.rollback();
				}
			}						
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if(t!=null) {
				t.rollback();
			}
		}	
	}
	
	public void modificarProyecto(int id) {
		Transaction t=null;
		if (verificarId(id)) {
			Proyecto p=ObtenerProyecto(id);
								
			System.out.println("Introduce nombre nuevo");
			String nombrenuevo=sc.nextLine();
				
			p.setNombre(nombrenuevo);
	
			Boolean valida=false;
			Date fechaFin =null;
			try {
				System.out.println("Introduce fecha de fin del proyecto (puede estar vacío)");
				String fechaInicioStr = sc.nextLine();
		        // Convertir el String a java.sql.Date
		        fechaFin = convertirStringADate(fechaInicioStr);
		        valida=true;
			}catch (Exception e) {
				System.out.println("Fecha no válida");
				valida=false;
				
			}
			
			if(valida) {
				t=this.sesion.beginTransaction();
				p.setFechaFin(fechaFin);
				this.sesion.update(p);
				t.commit();
			}else {
				System.out.println("La fecha no es válida");
				if(t!=null) {
					t.rollback();
				}
			}
				
		}else {
			System.out.println("El proyecto no existe");
			if(t!=null) {
				t.rollback();
			}
		}		
	}
	
	public void nuevoProyecto() {
		Transaction t=null;
		Boolean seguir=true;
		while (seguir) {
			System.out.println("Introduce el nombre del proyecto");
			String nombre=sc.nextLine();
			if(!nombre.isEmpty()) {
				Boolean valida=false;
				Date fechaInicio =null;
				try {
					System.out.println("Introduce fecha de inicio del proyecto");
					String fechaInicioStr = sc.next();
			        // Convertir el String a java.sql.Date
			        fechaInicio = convertirStringADate(fechaInicioStr);
			        if(fechaInicio!=null) {
			        	valida=true;
			        }else {
			        	valida=false;
			        }
			        
				}catch (Exception e) {
					System.out.println("Fecha no válida");
					if(t!=null) {
						t.rollback();
					}
					valida=false;
				}
				sc.nextLine();
				if(valida) {
						
					System.out.println("Introduce el dni del jefe del proyecto");
					String dni=sc.nextLine();
					if (validarDNI(dni)) {
						if(obtenerDatos(dni)!=null) { //vemos si está en plantilla
							try {
								Proyecto p=new Proyecto(nombre, fechaInicio, null, dni);
								t=this.sesion.beginTransaction();
								this.sesion.save(p);
								t.commit();
								System.out.println("Proyecto añadido");
								seguir=false;
							} catch (Exception e) {
								System.out.println("Error al agregar el proyecto: " + e.getMessage());
								if(t!=null) {
									t.rollback();
								}
							    e.printStackTrace();
							}
						}
						
					
					}else {
							System.out.println("El dni no es correcto");
							if(t!=null) {
								t.rollback();
							}
						}
				}else {
					System.out.println("Tienes que introducir una fecha válida");
					if(t!=null) {
						t.rollback();
					}
						
				}
								
			}else {
				System.out.println("El nombre no puede ser nulo");
				if(t!=null) {
					t.rollback();
				}
			}			
		
		}				
	}
	public void cambiarJefeProyecto(int id,String dni,Empleado emp) {
		Transaction t=null;
		if (verificarId(id)) {
			if (obtenerDatos(dni)!=null) {
				Proyecto p=ObtenerProyecto(id);
				t=this.sesion.beginTransaction();
				p.setJefeProyecto(emp);
				p.setDniJefeProyecto(dni);
				this.sesion.update(p);
				t.commit();
				System.out.println("Actualizado");
			}else {
				System.out.println("El empleado debe estar en plantilla");
				if(t!=null) {
					t.rollback();
				}

			}
			
		}else {
			System.out.println("No existe ese proyecto");
			if(t!=null) {
				t.rollback();
			}
		}
		
	}
	
	public Boolean verificarId(int id) {
		Transaction t=null;
		try {
			t=this.sesion.beginTransaction();
			Query q=this.sesion.createQuery("SELECT p from Proyecto p WHERE p.id=:id");
			q.setParameter("id", id);
			
			Proyecto p=(Proyecto) q.getSingleResult();
			t.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if(t!=null) {
				t.rollback();
			}
			return false;
		}
		
	}
	public Proyecto ObtenerProyecto(int id) {
		Transaction t=null;
		try {
			t=this.sesion.beginTransaction();
			Query q=this.sesion.createQuery("SELECT p from Proyecto p WHERE p.id=:id");
			q.setParameter("id", id);
			
			Proyecto p=(Proyecto) q.getSingleResult();
			t.commit();
			return p;
		} catch (Exception e) {
			if(t!=null) {
				t.rollback();
			}
			return new Proyecto();
		}
		
	}
	public List<Proyecto> ObtenerProyectos(){
		Transaction t=null;
		try {
			t=this.sesion.beginTransaction();
			Query q=this.sesion.createQuery("SELECT p from Proyecto p");
			
			List<Proyecto> listaProyectos=q.getResultList();
			t.commit();
			return listaProyectos;
		} catch (Exception e) {
			if(t!=null) {
				t.rollback();
			}
			return null;
		}
	}
	public void datosProyecto(int id) {
		if(verificarId(id)) {
			Proyecto p=ObtenerProyecto(id);
			System.out.println(p.toString());

		}
		
	}
	public DatosProf obtenerDatos(String dni) {
		Transaction t=null;
		try {
			t=this.sesion.beginTransaction();
			Query q=this.sesion.createQuery("SELECT d from DatosProf d where d.dni=:dni");
			q.setParameter("dni", dni);
			DatosProf datos=(DatosProf) q.getSingleResult();
			t.commit();
			return datos;
		} catch (Exception e) {
			System.out.println("El DNI no está en plantilla");
			if(t!=null) {
				t.rollback();
			}
			return null;
		}
	}	
	private static Date convertirStringADate(String fechaStr) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = formato.parse(fechaStr);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            return null; // Manejo de error: Devuelve null o lanza una excepción según tu caso.
        }
    }

}
	
	/*Crear, borrar y modificar los datos de un empleado.
Crear, borrar y modificar los datos de un proyecto.
Asignar un empleado a un proyecto.
Indicar el fin de la participación de un empleado en un proyecto.
Cambiar el jefe de un proyecto.
Mostrar los datos de un proyecto (nombre, fechas, empleados…).
Mostrar los datos de los empleados que están en plantilla.
Mostrar los empleados que son jefes de proyecto.*/
	
	
