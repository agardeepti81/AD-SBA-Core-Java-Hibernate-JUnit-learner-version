package sba.sms.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import sba.sms.utils.CommandLine;
import sba.sms.utils.HibernateUtil;

@FieldDefaults(level = AccessLevel.PRIVATE)
class CourseServiceTest {

	static CourseService cs;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		cs = new CourseService();
		CommandLine.addData();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		HibernateUtil.shutdown();
	}

	@Test
	void testGetCourseById() {
		assertNotNull(cs.getCourseById(1));
		assertNotNull(cs.getCourseById(2));
		assertNotNull(cs.getCourseById(3));
		assertNotNull(cs.getCourseById(4));
		assertNotNull(cs.getCourseById(5));
	}
}
