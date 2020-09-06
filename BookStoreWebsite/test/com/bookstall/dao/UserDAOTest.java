package com.bookstall.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstoredb.entity2.Users;

public class UserDAOTest {
    private static UserDAO userDAO;
    
	@BeforeClass
	public static void setUpClass() throws Exception{
		userDAO = new UserDAO();

	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	    userDAO.close();
	}
	
    @Test
	public void testCreateUsers() {
		Users user1 = new Users();
	    user1.setEmail("krishna@gopal.com");
	    user1.setPassword("divine");
	    user1.setFullName("Krishna Dev");
	    
	    user1 = userDAO.create(user1);
	    
	    assertTrue(user1.getUserId() > 0);
	}
	
	@Test(expected = PersistenceException.class)
	public void testCreateUsersFieldsNotSet() {
		Users user1 = new Users();
	    
	    user1 = userDAO.create(user1);
	    
	}

	@Test
	public void testUpdateUsers() {
		Users user = new Users();
		user.setUserId(19);
		user.setEmail("rahul810iop@gmail.com");
		user.setFullName("Rahul kumar");
		user.setPassword("67890");
		
		user = userDAO.update(user);
		String expected = "67890";
		String actual = user.getPassword();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetUsersFound() {
		Integer userId = 20;
		Users user = userDAO.get(userId);
		if(user != null) {
		   System.out.println(user.getFullName());
		}
		assertNotNull(user);
	}
	
	@Test
	public void testGetUsersNotFound() {
		Integer userId = 99;
		Users user = userDAO.get(userId);
		
		assertNull(user);
	}
	
	@Test
	public void testDeleteUsers() {
		Integer userId = 21;
		userDAO.delete(userId);
		
		Users user = userDAO.get(userId);
		
		assertNull(user);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testDeleteNonExistUsers() {
		Integer userId = 55;
		userDAO.delete(userId);
	}
	
	@Test
	public void testListAll() {
		List<Users> listUsers = userDAO.listAll();
	    
		for(Users user : listUsers) {
			System.out.println(user.getEmail());
		}
		
		assertTrue(listUsers.size() > 0);
	}
	
	@Test
	public void testCount() {
		long totalUsers = userDAO.count();
		
		assertTrue(totalUsers > 0);
	}
	
	@Test
	public void testFindByEmail() {
		String email = "rahul810iop@gmail.com";
		Users user = userDAO.findByEmail(email);
	
	    assertNotNull(user);
	}
	
	@Test
	public void testCheckloginSuccess() {
		String email = "rahul810iop@gmail.com";
		String password = "67890";
		
		boolean loginResult = userDAO.checkLogin(email, password);
		
		assertTrue(loginResult);
	}
	
	@Test
	public void testCheckloginFailure() {
		String email = "rahul810iop@gmail.com";
		String password = "678qwert";
		
		boolean loginResult = userDAO.checkLogin(email, password);
		
		assertFalse(loginResult);
	}
		
}
