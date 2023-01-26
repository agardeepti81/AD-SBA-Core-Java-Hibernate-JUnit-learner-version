package sba.sms.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

public class StudentService implements StudentI{

	@Override
	public List<Student> getAllStudents() {
		List<Student> students = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Student> query = session.createQuery("From Student", Student.class);
			students = query.getResultList();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return students;
	}

	@Override
	public void createStudent(Student student) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.persist(student);
			tx.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public Student getStudentByEmail(String email) {
		Student student = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Student> query = session.createNamedQuery("getStudentAndCoursesByEmail", Student.class)
					.setParameter("email", email);
			student = query.getSingleResult();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return student;
	}

	@Override
	public boolean validateStudent(String email, String password) {
		int result = 0;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Student> query = session.createNamedQuery("validateStudent", Student.class)
					.setParameter("email", email).setParameter("password", password);
			List<Student> r = query.getResultList();
			result = r.size();
			System.out.println("Result: " + result);
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public void registerStudentToCourse(String email, int courseId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			Student s = session.get(Student.class,email);
			Course c = session.get(Course.class,courseId);
			tx = session.beginTransaction();
			s.addCourse(c);
			session.merge(s);
			tx.commit();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public List<Course> getStudentCourses(String email) {
		List<Course> course = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Student> query = session.createNamedQuery("getStudentAndCoursesByEmail", Student.class)
					.setParameter("email", email);
			Student s = query.getSingleResult();
			course = s.getCourses();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}finally {
			session.close();
		}
		return course;
	}
}
