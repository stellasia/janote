/**
 * 
 * @author Estelle Scifo
 * @version 1.0
 */
package tests.model;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.janote.model.entities.Gender;
import com.janote.model.entities.Student;

/**
 * @author Estelle Scifo
 * @version 1.0
 */
public class StudentTest {

	private Student aStudent;
	
	/**
	 * setUp aStudent before each test
	 */
	@Before
	public void setUp() {
		Gender g = Gender.BOY;
		aStudent = new Student(null, "John", "Doe", g, "john.doe@somewhere.com", "11-10-1978", false, 1);
	}
	
	/**
	 * Test method for {@link com.janote.model.entities.Student#Student()}.
	 */
	@Test
	public void testStudent() {
		Student s = new Student();
		assertNull("Student.id doit être null", s.getId());
		assertNull("Student.name doit être null", s.getName());
		assertNull("Student.surname doit être null", s.getSurname());
		assertNull("Student.gender doit être null", s.getGender());
		assertNull("Student.email doit être null", s.getEmail());
		assertNull("Student.birthday doit être null", s.getBirthday());
		assertFalse("Student.repeating doit être False", s.isRepeating());
		//assertNull("Student.average_grade doit être null", s.getAverage_grade());
	}

	/**
	 * Test method for {@link com.janote.model.entities.Student#getAverage_grade()}.
	 */
	@Ignore
	@Test
	public void testGetAverage_grade() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.janote.model.entities.Student#getGender()}.
	 */
	@Test
	public void testGetGender() {
		assertEquals(aStudent.getGender(), Gender.BOY);
	}

	/**
	 * Test method for {@link com.janote.model.entities.Student#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		assertEquals(aStudent.getName(), "John");
		aStudent.setName("Mary");
		assertEquals(aStudent.getName(), "Mary");
	}

	/**
	 * Test method for {@link com.janote.model.entities.Student#setBirthday(java.util.Date)}.
	 * @throws ParseException 
	 */
	@Test
	public void testSetBirthday() throws ParseException {
		//System.out.println(aStudent.getBirthdayAsString());
		assertEquals(aStudent.getBirthdayAsString(), "11-10-1978");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    	Date d = sdf.parse("11-10-1978");
		assertEquals("Student before birthday change (date)", aStudent.getBirthday(), d);

    	d = sdf.parse("27-12-1978");
		aStudent.setBirthday(d);
		assertEquals("Student after birthday change (string)", aStudent.getBirthdayAsString(), "27-12-1978");
		assertEquals("Student after birthday change (date)", aStudent.getBirthday(), d);
	}

	/**
	 * Test method for {@link com.janote.model.entities.Student#setGender(com.janote.model.entities.Gender)}.
	 */
	@Test
	public void testSetGender() {
		assertTrue(aStudent.getGender() == Gender.BOY);
		Gender g = Gender.GIRL;
		aStudent.setGender(g);
		assertTrue(aStudent.getGender() == g);
	}

}
