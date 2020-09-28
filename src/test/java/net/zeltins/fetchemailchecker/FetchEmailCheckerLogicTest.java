/**
 * 
 */
package net.zeltins.fetchemailchecker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Peter
 *
 */
class FetchEmailCheckerLogicTest {

	private FetchEmailCheckerLogic checkerInstance;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		checkerInstance = new FetchEmailCheckerLogic();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		checkerInstance = null;
	}

	/**
	 * Test method for {@link net.zeltins.fetchemailchecker.FetchEmailCheckerLogic#NormalizeEmail(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	void testNormalizeEmail() throws Exception {
		Assertions.assertEquals("abcdef@def", FetchEmailCheckerLogic.NormalizeEmail("abcdef@def"));
		Assertions.assertEquals("abcdef@def", FetchEmailCheckerLogic.NormalizeEmail("ABCDEF@def"));
		Assertions.assertEquals("peter@zeltins.net", FetchEmailCheckerLogic.NormalizeEmail("peter@zeltins.net"));
		Assertions.assertEquals("peter@zeltins.net", FetchEmailCheckerLogic.NormalizeEmail("PETER@zeltins.net"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail("peter@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail("PETER@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail("peter+@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail("peter+zeltins@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail(".peter@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail("peter.@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail("pet.er@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail(".peter+zeltins@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail("peter.+zeltins@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail("pet.er+zeltins@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail(".peter+@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail("peter.+@gmail.com"));
		Assertions.assertEquals("peter@gmail.com", FetchEmailCheckerLogic.NormalizeEmail("pet.er+@gmail.com"));
	}

	/**
	 * Test method for {@link net.zeltins.fetchemailchecker.FetchEmailCheckerLogic#RemoveCharacter(java.lang.String, java.lang.Character)}.
	 */
	@Test
	void testRemoveCharacter() {
		Assertions.assertEquals("abcdef", FetchEmailCheckerLogic.RemoveCharacter("abcdef", '.'));
		Assertions.assertEquals("abcdef", FetchEmailCheckerLogic.RemoveCharacter("abc.def", '.'));
		Assertions.assertEquals("abcdef", FetchEmailCheckerLogic.RemoveCharacter(".ab.cdef.", '.'));
		Assertions.assertEquals("", FetchEmailCheckerLogic.RemoveCharacter("", '.'));
		Assertions.assertEquals("", FetchEmailCheckerLogic.RemoveCharacter(".", '.'));
		Assertions.assertEquals("", FetchEmailCheckerLogic.RemoveCharacter("....", '.'));
	}

	/**
	 * Test method for {@link net.zeltins.fetchemailchecker.FetchEmailCheckerLogic#IsValidEmail(java.lang.String)}.
	 */
	@Test
	void testIsValidEmail() {
		Assertions.assertEquals(true,FetchEmailCheckerLogic.IsValidEmail("peter@zeltins.net"));
		Assertions.assertEquals(true,FetchEmailCheckerLogic.IsValidEmail("pet.er@zeltins.net"));
		Assertions.assertEquals(true,FetchEmailCheckerLogic.IsValidEmail("pe.+ter@zeltins.net"));	
		Assertions.assertEquals(true,FetchEmailCheckerLogic.IsValidEmail("peter@gmail.com"));	
		Assertions.assertEquals(false,FetchEmailCheckerLogic.IsValidEmail("peter@gmail.com@test.com"));	
		Assertions.assertEquals(false,FetchEmailCheckerLogic.IsValidEmail("peter@gmail.com@com"));	
		Assertions.assertEquals(false,FetchEmailCheckerLogic.IsValidEmail("peter@gmail"));	
		Assertions.assertEquals(false,FetchEmailCheckerLogic.IsValidEmail("peter@gmail.com@"));	
		Assertions.assertEquals(false,FetchEmailCheckerLogic.IsValidEmail("@gmail.com"));	
		Assertions.assertEquals(false,FetchEmailCheckerLogic.IsValidEmail(""));	
		Assertions.assertEquals(false,FetchEmailCheckerLogic.IsValidEmail(null));	
	}

	/**
	 * Test method for {@link net.zeltins.fetchemailchecker.FetchEmailCheckerLogic#GetUniqueEmails()}.
	 * @throws Exception 
	 */
	@Test
	void testGetUniqueEmailsSimple() throws Exception {
		checkerInstance.AddEmail("peter@zeltins.net");
		checkerInstance.AddEmail("peter@zeltins.net");
		Assertions.assertEquals(1,checkerInstance.GetUniqueEmails());	
	}
	
	// test.email@gmail.com, test.email+spam@gmail.com and testemail@gmail.com
	@Test
	void testGetUniqueEmailsExample1() throws Exception {
		checkerInstance.AddEmail("test.email@gmail.com");
		checkerInstance.AddEmail("test.email+spam@gmail.com");
		checkerInstance.AddEmail("testemail@gmail.com");
		Assertions.assertEquals(1,checkerInstance.GetUniqueEmails());	
	}
	
	// test.email@gmail.com and test.email@fetchrewards.com
	@Test
	void testGetUniqueEmailsExample2() throws Exception {
		checkerInstance.AddEmail("test.email@gmail.com");
		checkerInstance.AddEmail("test.email@fetchrewards.com");
		Assertions.assertEquals(2,checkerInstance.GetUniqueEmails());	
	}

	@Test
	void testGetUniqueEmailsEdgeCase1() {
		Assertions.assertEquals(0,checkerInstance.GetUniqueEmails());	
	}

	@Test
	void testGetUniqueEmailsEdgeCase2() throws Exception {
		checkerInstance.AddEmail("");
		checkerInstance.AddEmail(null);
		checkerInstance.AddEmail("invalid@invalid");
		Assertions.assertEquals(0,checkerInstance.GetUniqueEmails());	
	}
	
	@Test
	void testGetUniqueEmails() throws Exception {
		checkerInstance.AddEmail("peter@zeltins.net");
		checkerInstance.AddEmail("peter@zeltins.net");
		checkerInstance.AddEmail("peter@gmail.com");
		checkerInstance.AddEmail("peter+ddd@gmail.com");
		checkerInstance.AddEmail("peter@zeltins.net2");
		checkerInstance.AddEmail("peter@zeltins.net3");
		checkerInstance.AddEmail("peter@zeltins.net4");
		Assertions.assertEquals(5,checkerInstance.GetUniqueEmails());	
	}
	
}
