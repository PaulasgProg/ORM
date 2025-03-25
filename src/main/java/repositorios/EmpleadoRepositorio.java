package repositorios;

import java.util.Scanner;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entidades.Empleado;

public class EmpleadoRepositorio {
	
	private Session sesion;
	static Scanner sc=new Scanner(System.in);

	public EmpleadoRepositorio(Session sesion) {
		super();
		this.sesion = sesion;
	}
	
	/*Crear, borrar y modificar los datos de un empleado.
Crear, borrar y modificar los datos de un proyecto.
Asignar un empleado a un proyecto.
Indicar el fin de la participación de un empleado en un proyecto.
Cambiar el jefe de un proyecto.
Mostrar los datos de un proyecto (nombre, fechas, empleados…).
Mostrar los datos de los empleados que están en plantilla.
Mostrar los empleados que son jefes de proyecto.*/
	
	public void nuevoEmpleado() {
		//CREA UN EMPLEADO NUEVO,VALIDANDO EL DNI INTRODUCIDO Y COMPROBANDO 
		//QUE NO EXISTE UN EMPLEADO CON ESE DNI
		//EL NOMBRE NO PUEDE SER NULL
		
		Boolean seguir=true;
		while (seguir) {
			System.out.println("Introduce dni");
			String dninuevo=sc.next();
			Transaction t=this.sesion.beginTransaction();
			if(validarDNI(dninuevo)) {
				if(!existeEmpleado(dninuevo)) {
					try {
						System.out.println("Introduce nombre");
						String nombrenuevo=sc.nextLine();
						if(nombrenuevo!=null) {
							Empleado empleado=new Empleado(dninuevo, nombrenuevo);
							
							this.sesion.save(empleado);
							t.commit();
							System.out.println("Empleado añadido");
							seguir=false;
						}else {
							System.out.println("Debes introducir un nombre");
							seguir=true;
							t.rollback();
						}
						
					} catch (Exception e) {
						System.out.println("Error al agregar empleado: " + e.getMessage());
					    e.printStackTrace();
					    t.rollback();
					}
					
					
				}else {
					System.out.println("Ya existe un empleado con ese DNI");
					t.rollback();
				}
				
			}else {
				System.out.println("El dni no es válido");
				t.rollback();
			}
		}				
	}
	
	public void borrarEmpleado(String dni) {
		//BORRA UN EMPLEADO, VALIDA DNI Y COMPRUEBA SI EXISTE
		Boolean seguir=true;
		do {
			Transaction t=this.sesion.beginTransaction();
			try {
				if(validarDNI(dni)) {
					if(existeEmpleado(dni)) {
						Query q=this.sesion.createQuery("SELECT e from Empleado e where e.dni=:dni"	);
						q.setParameter("dni", dni);
						
						Empleado empleado=(Empleado) q.getSingleResult();
						
						this.sesion.delete(empleado);
						t.commit();
						System.out.println("Empleado borrado");
						seguir=false;
						
					}else {
						System.out.println("No existe el empleado");
						t.rollback();
						seguir=false;
					}
				}else {
					System.out.println("DNI no válido");
					t.rollback();
					seguir=true;
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				t.rollback();
				seguir=false;
				
			}
		}while(seguir==true);
	
	}
	public Boolean existeEmpleado(String dni) {
		//COMPRUEBA SI EXISTE UN EMPLEADO DADO UN DNI
		try {	
			if(validarDNI(dni)) {
				Query q=this.sesion.createQuery("SELECT e from Empleado e where e.dni=:dni"	);
				q.setParameter("dni", dni);
				
				Empleado empleado=(Empleado) q.getSingleResult();

				return true;
			}else {
				return false;
			}
					
		} catch (Exception e) {

			return false;
		}
	}
	public Empleado getEmpleado(String dni) {
		//OBTIENE UN EMPLEADO DADO UN DNI
		Transaction t = null;
		try {
		
            t = this.sesion.beginTransaction();
            
			Query q=this.sesion.createQuery("SELECT e from Empleado e where e.dni=:dni"	);
			q.setParameter("dni", dni);
			
			Empleado empleado=(Empleado) q.getSingleResult();
			t.commit();
			return empleado;
			
		} catch (Exception e) {
			if(t!=null) {
				t.rollback();
			}
			return new Empleado();
			
		}
	}
	
	public void modificarEmpleado(String dni) {
		//MODIFICA UN EMPLEADO QUE NO ESTÁ EN PLANTILLA, CAMBIO DE NOMBRE SOLO
		Transaction t = null;
		if (validarDNI(dni)) {
			if (existeEmpleado(dni)) {		
				try {					
					Query q=this.sesion.createQuery("SELECT e from Empleado e where e.dni=:dni"	);
					q.setParameter("dni", dni);
					
					Empleado empleado=(Empleado) q.getSingleResult();
					Boolean seguir=false;
					do {
						System.out.println("Introduce nombre nuevo");
						String nombrenuevo=sc.nextLine();
						if(nombrenuevo!=null) {
							empleado.setNombre(nombrenuevo);		
							t=this.sesion.beginTransaction();
							this.sesion.update(empleado);
							t.commit();
							System.out.println("Empleado modificado");
							seguir=true;
						}else {
							System.out.println("Debes introducir un nombre");
							seguir=false;
						}
					} while (seguir==false);
					     
				} catch (Exception e) {
					e.printStackTrace();	
					if(t!=null) {
						t.rollback();
					}
				}				
			}else {
				System.out.println("El empleado no existe");
			}
		}else {
			System.out.println("El DNI no es válido");
		}		
	}

	public boolean validarDNI(String dni) {
		// VERIFICA EL FORMATO DEL DNI CON UN PATRON
        String patronDNI = "\\d{8}[A-HJ-NP-TV-Z]";

        return dni.matches(patronDNI);
	}
	
}
