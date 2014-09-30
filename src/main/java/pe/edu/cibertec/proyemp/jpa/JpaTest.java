package pe.edu.cibertec.proyemp.jpa;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import pe.edu.cibertec.proyemp.domain.Departamento;
import pe.edu.cibertec.proyemp.domain.Empleado;

/**
 * 
 * @author Lysander
 *
 */
public class JpaTest {
		
	private EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}
	
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			test.crearEmpleadosJava();
			test.crearEmpleadosNET();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();

		test.listarEmpleados();

		System.out.println(".. done");
	}


	private void crearEmpleadosNET() {
		
		Departamento departamentoNET = new Departamento(".NET");
		
		Empleado emp1 = new Empleado("Juan", departamentoNET);
		Empleado emp2 = new Empleado("Pedro",departamentoNET);
		Empleado emp3 = new Empleado("Maria",departamentoNET);
		
		List<Empleado> empleados = Arrays.asList(emp1, emp2, emp3);
		
		departamentoNET.setEmpleados(empleados);
		
		manager.persist(departamentoNET);
		
	}


	private void crearEmpleadosJava() {
		int nroDeEmpleados = manager.createQuery("Select a From Empleado a", Empleado.class).getResultList().size();
		if (nroDeEmpleados == 0) {
			Departamento departamento = new Departamento("Java");
			manager.persist(departamento);

			manager.persist(new Empleado("Bob",departamento));
			manager.persist(new Empleado("Mike",departamento));

		}
	}


	private void listarEmpleados() {
		List<Empleado> resultList = manager.createQuery("Select a From Empleado a", Empleado.class).getResultList();
		System.out.println("nro de empleados:" + resultList.size());
		for (Empleado next : resultList) {
			System.out.println("siguiente empleado: " + next);
		}
	}


}
