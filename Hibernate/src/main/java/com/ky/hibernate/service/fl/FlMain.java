package com.ky.hibernate.service.fl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.ky.hibernate.entity.fl.FlActor;

public class FlMain {

	public static void main(String[] args) {
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		session = sessionFactory.openSession();
		session.beginTransaction();
		List<FlActor> flActors = session.createQuery("from FlActor").setMaxResults(10).list();
		flActors.forEach(a->System.out.println(a.getActorId()+"|"+a.getDocType()+"|"+a.getDocId()));
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}

}
