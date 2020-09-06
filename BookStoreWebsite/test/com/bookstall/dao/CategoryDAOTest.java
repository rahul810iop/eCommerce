package com.bookstall.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstoredb.entity2.Category;
import com.bookstoredb.entity2.Users;

public class CategoryDAOTest {

	private static CategoryDAO categoryDAO;
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		categoryDAO = new CategoryDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	    categoryDAO.close();
	}

	@Test
	public void testCreateCategory() {
	    Category newCat = new Category("Marketing");
	    Category category = categoryDAO.create(newCat);
	   
	    assertTrue(category != null && category.getCategoryId() > 0);
	}

	@Test
	public void testUpdateCategory() {
		Category cat = new Category("Core Java");
		cat.setCategoryId(12);
		
		Category category = categoryDAO.update(cat);
		
		assertEquals(cat.getName(), category.getName());
	}
	
	@Test
	public void testGet() {
		Integer catId = 20;
		Category cat = categoryDAO.get(catId);
		
		assertNotNull(cat);
	}
	
	@Test
	public void testDelete() {
		Integer catId = 17;
        categoryDAO.delete(catId);
		
		Category cat = categoryDAO.get(catId);
		
		assertNull(cat);
	}
	@Test
	public void testListAll() {
		List<Category> listCategory = categoryDAO.listAll();
		
	    listCategory.forEach(c -> System.out.println(c.getName() + ", "));
		
	    assertTrue(listCategory.size() > 0);
	}
	
	@Test
	public void testCount() {
        long totalCategories = categoryDAO.count();
		
		assertTrue(totalCategories > 0);
	}
	
	@Test
	public void testFindByName() {
		String name = "Advanced Java";
		Category category = categoryDAO.findByName(name);
	
	    assertNotNull(category);
	
	}
	
	@Test
	public void testFindByNameNotFound() {
		String name = "Java";
		Category category = categoryDAO.findByName(name);
	
	    assertNull(category);
	
	}
}
