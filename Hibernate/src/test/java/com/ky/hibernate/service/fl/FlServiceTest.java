package com.ky.hibernate.service.fl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.ky.hibernate.entity.fl.FlActor;

import junit.framework.TestCase;

public class FlServiceTest extends TestCase {

	private SessionFactory sessionFactory;
	
	@Override
	protected void setUp() throws Exception{
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		if(sessionFactory!=null) {
			sessionFactory.close();
		}
	}
	
	public void test() {
		Session session = sessionFactory.openSession();
//		session.beginTransaction();
//		FlActor flActor = new FlActor();
//		flActor.setDocType("PR");
//		flActor.setDocId(-1l);
//		flActor.setRoleCode("INVALID_USER");
//		flActor.setUserId(-1l);
//		flActor.setSalutation("Encik");
//		flActor.setUserName("temporary user");
//		flActor.setDesignation("CEO");
//		flActor.setGrade("55");
//		flActor.setActionDate(new Date());
//		session.save(flActor);
//		session.getTransaction().commit();
//		session.close();
		
		session = sessionFactory.openSession();
		session.beginTransaction();
		List<FlActor> flActors = session.createQuery("from FlActor").setMaxResults(10).list();
		flActors.forEach(a->System.out.println(a.getActorId()+"|"+a.getDocType()+"|"+a.getDocId()));
		session.getTransaction().commit();
		session.close();
	}
}
