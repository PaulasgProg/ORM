package repositorios;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entidades.DatosProf;
import entidades.DatosProf.Categoria;
import entidades.Empleado;

public class DatosRepositorio {
	
	private Session sesion;

	public DatosRepositorio(Session sesion) {
		super();
		this.sesion = sesion;
	}
	
	public Boolean enPlantilla(String DNI) {
		Transaction t = null;
		try {
			t=this.sesion.beginTransaction();
			Query q=this.sesion.createQuery("SELECT d from DatosProf d where d.dni=:DNI");
			q.setParameter("DNI", DNI);
			
			DatosProf dp=(DatosProf) q.getSingleResult();
			t.commit();
			return true;
		} catch (Exception e) {
			if(t!=null) {
				t.rollback();
			}
			return false;
		}
		
	}
	
	public void datosPlantilla() {
		Transaction t = null;
		try {
			t=this.sesion.beginTransaction();
			Query q=this.sesion.createQuery("SELECT d from DatosProf d");
			
			List<DatosProf> listaDatos=q.getResultList();
			
			t.commit();
			for (DatosProf datosProf : listaDatos) {
				
				System.out.println(datosProf.toString());
				
			}
			
		} catch (Exception e) {
			if(t!=null) {
				t.rollback();
			}
		}
	}
	public void añadirPlantilla(String dni,Categoria categoria,BigDecimal sueldo, Empleado emp) {
		Transaction t = null;
		try {
			t=this.sesion.beginTransaction();
			DatosProf datos=new DatosProf(dni, categoria, sueldo, emp);
			this.sesion.save(datos);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR al añadir el empleado a plantilla");
			if(t!=null) {
				t.rollback();
			}		
		}
		
	}
	public void borrarPlantilla(DatosProf datos) {
		Transaction t = null;
		try {
			t=this.sesion.beginTransaction();
			this.sesion.delete(datos);
			t.commit();
		} catch (Exception e) {
			System.out.println("ERROR al borrar el empleado a plantilla");
			if(t!=null) {
				t.rollback();
			}	
		}
	}
	public void modificarEmpleadoPlantilla(DatosProf datos) {
		Transaction t = null;
		
	
		Boolean seguir=false;
		do {
			System.out.println("Introduce su categoria (A,B,C,D)");
			Scanner sc=new Scanner(System.in);
			try {
				t=this.sesion.beginTransaction();
				char c=sc.next().charAt(0);
				Categoria categoria=obtenerCategoria(c);
				if (categoria!=null) {
					System.out.println("Introduce el salario");
					BigDecimal sueldo=sc.nextBigDecimal();
					if(sueldo.compareTo(BigDecimal.ZERO) > 0) {
						datos.setCategoria(categoria);
						datos.setSueldo(sueldo);
						this.sesion.update(datos);
						t.commit();
						System.out.println("Empleado modificado");
						seguir=true;
						
					}else {
						System.out.println("El sueldo debe ser mayor que 0");
						t.rollback();
						seguir=false;
					}
				}
			}catch (Exception e) {
				System.out.println("Error actualizando el empleado");
				if(t!=null) {
					t.rollback();
				}	
			}
		} while (seguir==false);
		
		
	}
	
	public boolean validarDNI(String dni) {
		// Patrón que coincide con un DNI 
        String patronDNI = "\\d{8}[A-HJ-NP-TV-Z]";

        // Verificar si el DNI coincide con el patrón
        return dni.matches(patronDNI);
	}
	
	public Categoria obtenerCategoria(char letra) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.name().charAt(0) == letra) {
                return categoria;
            }
        }
        return null; // Si no se encuentra ninguna coincidencia
    }
	public DatosProf obtenerDatos(String dni) {
		Transaction t = null;
		try {
			t=this.sesion.beginTransaction();
			Query q=this.sesion.createQuery("SELECT d from DatosProf d where d.dni=:dni");
			q.setParameter("dni", dni);
			DatosProf datos=(DatosProf) q.getSingleResult();
			t.commit();
			return datos;
		} catch (Exception e) {
			System.out.println("Error al obtener los datos");
			if(t!=null) {
				t.rollback();
			}	
			return null;
		}
		
	}

}
