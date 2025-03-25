package main;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.text.DateFormatter;

import org.hibernate.Session;

import entidades.AsigProyecto;
import entidades.DatosProf;
import entidades.DatosProf.Categoria;
import entidades.Empleado;
import entidades.Proyecto;
import repositorios.AsigRepositorio;
import repositorios.DatosRepositorio;
import repositorios.EmpleadoRepositorio;
import repositorios.ProyectoRepositorio;

public class App {
	
	static Scanner sc;

	public static void main(String[] args) {
		System.out.println("Tarea 401");
		
		Session sesion=HibernateUtil.get().openSession();
		AsigRepositorio ar=new AsigRepositorio(sesion);
		EmpleadoRepositorio er=new EmpleadoRepositorio(sesion);
		ProyectoRepositorio pr=new ProyectoRepositorio(sesion);
		DatosRepositorio dr=new DatosRepositorio(sesion);
		
		sc=new Scanner(System.in);
		Boolean salir=false;
		do {
			System.out.println("1.Crear, borrar y modificar los datos de un empleado.\r\n"
					+ "2.Crear, borrar y modificar los datos de un proyecto.\r\n"
					+ "3.Asignar un empleado a un proyecto.\r\n"
					+ "4.Indicar el fin de la participación de un empleado en un proyecto.\r\n"
					+ "5.Cambiar el jefe de un proyecto.\r\n"
					+ "6.Mostrar los datos de un proyecto (nombre, fechas, empleados…).\r\n"
					+ "7.Mostrar los datos de los empleados que están en plantilla.\r\n"
					+ "8.Mostrar los empleados que son jefes de proyecto.\r\n"
					+ "9.Salir");
			//PONER TRY CATCH
			int opcion=sc.nextInt();
			
			switch (opcion) {
			case 1://1.Crear, borrar y modificar los datos de un empleado
				int opcion2=-1;
				do {
					System.out.println("Escoge una:\r\n"
							+ "1.Crear un empleado\r\n"
							+ "2.Borrar un empleado\r\n"
							+ "3.Modificar un empleado\r\n");
					opcion2=sc.nextInt();
					switch (opcion2) {
					
					case 1:
						er.nuevoEmpleado();
						break;
					case 2:
						System.out.println("Introduce el DNI del empleado a borrar");
						sc.nextLine();
						String dni=sc.nextLine();
						er.borrarEmpleado(dni);
						break;
					case 3:
						System.out.println("Escoge una opción:\r\n"
								+ "1.Añadirlo a plantilla\r\n"
								+ "2.Borrarlo de la plantilla\r\n"
								+ "3.Modificar datos de la plantilla\r\n");
						int opcion3=sc.nextInt();
						
						switch (opcion3) {
						case 1://añadir empleado a plantilla
							Boolean next=false;
							do {			
								System.out.println("Introduce el DNI del empleado a añadir a la plantilla");
								sc.nextLine();
								dni=sc.nextLine();
								if(er.existeEmpleado(dni)) { //comprueba si existe el empleado
									if(!dr.enPlantilla(dni)) {//comprueba que NO esté en plantilla
										System.out.println("Introduce su categoria (A,B,C,D)");
										try {
											char c=sc.next().charAt(0);
											Categoria categoria=dr.obtenerCategoria(c);
											if (categoria!=null) {//si la categoria no es null seguimos
												Boolean seguir=false;
												do {
													System.out.println("Introduce el salario");
													BigDecimal sueldo=sc.nextBigDecimal();
													if(sueldo.compareTo(BigDecimal.ZERO) > 0) {//sueldo>0
														Empleado empl=er.getEmpleado(dni);
														dr.añadirPlantilla(dni, categoria, sueldo, empl);
														seguir=true;
														next=true;
													}else {
														System.out.println("El sueldo debe ser mayor que 0");
														seguir=false;
													}
												}while(seguir==false);	
											}else {
												System.out.println("Debe ser una letra");
												next=false;
											}
										}catch (Exception e) {
											e.printStackTrace();
											
											next=false;
										}	
									}else {
										System.out.println("Ya se encuentra en plantilla");
										next=true;
									}
								}else {
									if (er.validarDNI(dni)) {
										System.out.println("El empleado no existe");
										next=false;
										
									}else {
										System.out.println("El DNI no es válido");
										next=false;
									}
								}
							} while (next==false);
							
							break;
						case 2://borrar de plantilla
							next=false;
							do {
								System.out.println("Introduce el DNI del empleado a borrar de la plantilla");
								sc.nextLine();
								dni=sc.nextLine();
								if(er.existeEmpleado(dni)) {
									if(dr.enPlantilla(dni)) {
										DatosProf d=dr.obtenerDatos(dni);
										dr.borrarPlantilla(d);
										next=true;
									}else {
										System.out.println("El empleado ya no se encuentra en plantilla");
										next=true;
									}			
								}else {
									System.out.println("El empleado no existe");
									next=false;
								}
							} while (next==false);
							
							break;
						case 3://modificar empleado
							System.out.println("Introduce el DNI del empleado a modificar");
							sc.nextLine();
							dni=sc.nextLine();
							if(er.existeEmpleado(dni)) {
								if(dr.enPlantilla(dni)) {
									DatosProf d=dr.obtenerDatos(dni);
									dr.modificarEmpleadoPlantilla(d);
								}else {
									er.modificarEmpleado(dni);
								}
									
							}else {
								System.out.println("No existe el empleado");
							}
							break;
						default:
							System.out.println("La selección no es correcta");
							break;
						}
		
						break;
						
					default:
						System.out.println("La selección no es correcta");
						break;
					}
				}while(opcion2>3||opcion2<1);
				
				break;

			case 2: //2.Crear, borrar y modificar los datos de un proyecto.
				
				int opcion3=-1;
				do {
					System.out.println("Escoge una:\r\n"
							+ "1.Crear un proyecto\r\n"
							+ "2.Borrar un proyecto\r\n"
							+ "3.Modificar un proyecto.");
					opcion3=sc.nextInt();
					switch (opcion3) {
					case 1:
						pr.nuevoProyecto();
						break;
					case 2:
						System.out.println("Introduce el id del proyecto a borrar");
						int id=sc.nextInt();
						pr.borrarProyecto(id);
						break;
					case 3: //hacer bucle 
						System.out.println("Introduce el id del proyecto a modificar");
						id=sc.nextInt();
						pr.modificarProyecto(id);
						break;

					default:
						System.out.println("La selección no es correcta");
						break;
					}
				}while(opcion3>3||opcion3<1);
				break;
			case 3://3.Asignar un empleado a un proyecto. BIEN
				sc.nextLine();
				System.out.println("Introduce el dni del empleado");
				String dni_emp=sc.nextLine();
				if(er.validarDNI(dni_emp)&&er.existeEmpleado(dni_emp)) {
					Empleado emp=er.getEmpleado(dni_emp);
					System.out.println("Introduce el id del proyecto");
					int idPr=sc.nextInt();
					if(pr.verificarId(idPr)) {
						Proyecto p=pr.ObtenerProyecto(idPr);
						System.out.println("Introduce la fecha de inicio en formato yyyy-MM-dd:");
						try {
							String fechaInicioStr = sc.next();
					        // Convertir el String a java.sql.Date
					        Date fechaInicio = convertirStringADate(fechaInicioStr);
							ar.asig_empleado(emp, p, fechaInicio);
						}catch (Exception e) {
							e.printStackTrace();
							System.out.println("el formato de fecha no es válido");
						}
				        
					}else {
						System.out.println("El proyecto no existe");
					}
					
				}else {
					if(er.validarDNI(dni_emp)) {
						System.out.println("DNI no es válido");
					}else {
						System.out.println("El empleado no existe, créalo");
					}			
				}
				
				break;
				
			case 4://4.Indicar el fin de la participación de un empleado en un proyecto
				sc.nextLine();
				System.out.println("Introduce el dni del empleado");
				dni_emp=sc.nextLine();
				if(er.validarDNI(dni_emp)&&er.existeEmpleado(dni_emp)) {
					Empleado emp=er.getEmpleado(dni_emp);
					System.out.println("Introduce el id del proyecto");
					int idPr=sc.nextInt();
					if(pr.verificarId(idPr)) {
						Proyecto p=pr.ObtenerProyecto(idPr);
						System.out.println("Introduce la fecha fin en formato yyyy-MM-dd:");
						try {
							String fechaFinStr = sc.next();
					        // Convertir el String a java.sql.Date
					        Date fechaFin = convertirStringADate(fechaFinStr);
					        
					        if (fechaFin.compareTo(p.getFechaInicio()) > 0) {
					        	ar.desasignar_empleado(emp, p, fechaFin);
					        }else{
					        	System.out.println("La fecha debe ser posterior");
					        }
							
						}catch (Exception e) {
							e.printStackTrace();
							System.out.println("el formato de fecha no es válido");
						}
				        
					}else {
						System.out.println("El proyecto no existe");
					}
					
				}else {
					if(er.validarDNI(dni_emp)) {
						System.out.println("DNI no es válido");
					}else {
						System.out.println("El empleado no existe, créalo");
					}
				}
				
				break;
				
			case 5://5.Cambiar el jefe de un proyecto 
				sc.nextLine();
				System.out.println("Introduce el dni del empleado que será jefe");
				dni_emp=sc.nextLine();
				if(er.existeEmpleado(dni_emp)) {
					
					//Solo puede ser JEFE UN EMPLEADO EN PLANTILLA (datosProf)
					if(dr.enPlantilla(dni_emp)) {
						System.out.println("Introduce el id del proyecto");
						int idp=sc.nextInt();
						if(pr.verificarId(idp)) {
							Empleado emp=er.getEmpleado(dni_emp);
							pr.cambiarJefeProyecto(idp, dni_emp,emp);

						}
					}else {
						System.out.println("El empleado no está en plantilla");
					}				
				}else {
					System.out.println("El empleado no existe");
				}
				break;
				
			case 6://6.Mostrar los datos de un proyecto (nombre, fechas, empleados…)
				System.out.println("Introduce el id del proyecto");
				int id=sc.nextInt();
				if(pr.verificarId(id)) {
					pr.datosProyecto(id);
					ar.empleadosProyecto(id);
			
				}else {
					System.out.println("No existe ese proyecto");
				}
				break;
				
			case 7://7.Mostrar los datos de los empleados que están en plantilla CORRECTO
				
				dr.datosPlantilla();
				break;
				
			case 8://8.Mostrar los empleados que son jefes de proyecto.
				List<Proyecto> listaProyectos=pr.ObtenerProyectos();
				Map<String,Empleado > hashMap = new HashMap<>();
				for (Proyecto proyecto : listaProyectos) {
					
					Empleado jefe=proyecto.getJefeProyecto();
					String nombreProy=proyecto.getNombre();
					hashMap.put(nombreProy, jefe);
				}
				 for (Entry<String, Empleado> entry : hashMap.entrySet()) {
			            System.out.println("El jefe del proyecto: " + entry.getKey() + ", es: " + entry.getValue());
			        }
				break;
			case 9:
				salir=true;
				System.out.println("SALIENDO");
				break;
			default:
				break;
			}
		} while (salir==false);
		
		

	}
	private static Date convertirStringADate(String fechaStr) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = formato.parse(fechaStr);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Manejo de error: Devuelve null o lanza una excepción según tu caso.
        }
    }

}
