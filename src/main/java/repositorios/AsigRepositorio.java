package repositorios;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entidades.AsigProyecto;
import entidades.Empleado;
import entidades.Proyecto;

public class AsigRepositorio {

	private Session sesion;
	static Scanner sc;
	public AsigRepositorio(Session sesion) {
		super();
		this.sesion = sesion;
	}
	
	public void asig_empleado(Empleado emp, Proyecto p, Date fecha) {
		AsigProyecto a = null;
		Transaction t=null;
		if(buscarDNI(emp.getDni(), p.getId())) {
			List<AsigProyecto> as=buscarDNI_Asig(emp.getDni(), p.getId());
			for (AsigProyecto asigProyecto : as) {
				if(asigProyecto.getFechaFin()==null) {
					a=asigProyecto;
				}
			}
			
			if(a!=null&&a.getFechaFin()!=null){
				AsigProyecto ap=new AsigProyecto(emp.getDni(),p.getId(),fecha,emp,p);
				t=this.sesion.beginTransaction();
				this.sesion.save(ap);
				t.commit();
				System.out.println("Empleado asignado correctamente");
			}else {
				System.out.println("El empleado ya está asignado a ese proyecto");
				
			}
		}else {
			AsigProyecto ap=new AsigProyecto(emp.getDni(),p.getId(),fecha,emp,p);
			t=this.sesion.beginTransaction();
			this.sesion.save(ap);
			t.commit();
			System.out.println("Empleado asignado correctamente");
		}
		
		
	}
	public void desasignar_empleado(Empleado emp, Proyecto p, Date fecha) {
		AsigProyecto a = null;
		Transaction t=null;
		if (buscarDNI(emp.getDni(), p.getId())) {
			List<AsigProyecto> as=buscarDNI_Asig(emp.getDni(), p.getId());
			for (AsigProyecto asigProyecto : as) {
				if(asigProyecto.getFechaFin()==null) {
					a=asigProyecto;
				}
			}
			
			if(a!=null&&a.getFechaFin()==null){
				a.setFechaFin(fecha);
				t=this.sesion.beginTransaction();
				this.sesion.update(a);
				t.commit();
			}else {
				System.out.println("Ya estaba desasignado");
			}
		}else {
			System.out.println("No se encuentra el empleado asignado al proyecto");
		}
	
		
	}
	
	
	public Boolean buscarDNI(String dni,int idProyecto) {
		Transaction t=null;
		try {
			t=this.sesion.beginTransaction();
			Query q=this.sesion.createQuery("SELECT a from AsigProyecto a where a.dniEmpleado=:dni and a.idProyecto=:idProyecto");
			q.setParameter("dni", dni);
			q.setParameter("idProyecto", idProyecto);
			List<AsigProyecto> lista=q.getResultList();
			t.commit();
			if (lista.isEmpty()) {
				return false;
			}else {
				return true;
			}	
		} catch (Exception e) {
			e.printStackTrace();
			if(t!=null) {
				t.rollback();
			}
			
			return false;
		}
		
	}
	public List<AsigProyecto> buscarDNI_Asig(String dni,int idProyecto) {
		Transaction t=null;
		try {
			t =this.sesion.beginTransaction();
			Query q=this.sesion.createQuery("SELECT a from AsigProyecto a where a.dniEmpleado=:dni and a.idProyecto=:idProyecto");
			q.setParameter("dni", dni);
			q.setParameter("idProyecto", idProyecto);
			List<AsigProyecto> lista=q.getResultList();
			t.commit();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			if(t!=null) {
				t.rollback();
			}
			List<AsigProyecto> lista=null;
			return lista;
		}
		
	}

	
	public void empleadosProyecto(int id){
		Transaction t=this.sesion.beginTransaction();
		Query q=this.sesion.createQuery("SELECT a from AsigProyecto a where a.idProyecto=:id and a.fechaFin=null");
		q.setParameter("id", id);
		
		List<AsigProyecto> listaAsign=q.getResultList();
		t.commit();
		
		System.out.println("EMPLEADOS:");
		for (AsigProyecto asigProyecto : listaAsign) {
			Empleado e=asigProyecto.getEmpleado();
			System.out.println(e.toString());
		}
	}
}
