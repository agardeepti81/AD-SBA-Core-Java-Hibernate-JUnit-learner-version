package sba.sms.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

public class CourseService implements CourseI {

	@Override
	public void createCourse(Course course) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.persist(course);
			tx.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		}finally {
			session.close();
		}
	}

	@Override
	public Course getCourseById(int courseId) {
		Course result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Course> query = session.createNamedQuery("getCourseById", Course.class)
					.setParameter("id", courseId);
			result = query.getSingleResult();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}finally {
			session.close();
		}
		return result;
	}

	@Override
	public List<Course> getAllCourses() {
		List<Course> result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Course> query = session.createQuery("From Course", Course.class);
			result = query.getResultList();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}finally {
			session.close();
		}
		return result;
	}
}
