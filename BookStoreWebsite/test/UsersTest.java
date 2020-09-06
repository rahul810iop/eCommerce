import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.bookstoredb.entity2.Users;

public class UsersTest {

	public static void main(String[] args) {
	    Users user1 = new Users();
	    user1.setEmail("rahul810iop@gmail.com");
	    user1.setPassword("12345");
	    user1.setFullName("Rahul Kumar");
	    
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStoreWebsite");
	    EntityManager entityManager = entityManagerFactory.createEntityManager();

	    entityManager.getTransaction().begin();
	    
	    entityManager.persist(user1);
	    
	    entityManager.getTransaction().commit();
	    entityManager.close();
	    entityManagerFactory.close();
	    
	    System.out.println("Data saved successfully");
	}

}
