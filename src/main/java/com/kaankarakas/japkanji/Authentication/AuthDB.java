
package com.kaankarakas.japkanji.Authentication;

import com.kaankarakas.japkanji.entity.Users;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.naming.InitialContext;

public class AuthDB {
    public static Users saveUsers(Users us)
    {
        // Entity manager is an API manges entities 
        EntityManager entityManager =  Persistence.createEntityManagerFactory("my_persistence_unit").createEntityManager();
        // persistance makes the noun outlive the function it created by 
        try {
           entityManager.persist(us);
        UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
        transaction.setTransactionTimeout(300);
        
        transaction.begin();
        entityManager.joinTransaction();
        transaction.commit();
        } catch (Exception e) {
            
  //try catch method helps the progtam to catch and block errors in constraints created by try
    
            }
        return us;
    }
    public static int checkUser (String username, String password)
    {
        EntityManager entityManager =  Persistence.createEntityManagerFactory("my_persistence_unit").createEntityManager();
        Query query = entityManager.createNamedQuery("Users.findByUsername");
        query.setParameter("username", username);
        List<Users> resultList = query.getResultList();
        for (Users u : resultList) {
            if (u.getPassword().indexOf(password)>=0)
                {
                    if (u.getUsertype())
                        return 1;//admin
                    else    // When the variable is returned as 1 the user type is admin while if it returns 0 
                        //it means the variable will return as student. The 2 retuns means fail.
                        return 0;// student
                }
                
        }
        
        return 2;//fail
    }
    public static Users getUser (String username, String password)
    {
        
        EntityManager entityManager =  Persistence.createEntityManagerFactory("my_persistence_unit").createEntityManager();
        Query query = entityManager.createNamedQuery("Users.findByUsername");
        query.setParameter("username", username);
        List<Users> resultList = query.getResultList();
        for (Users u : resultList) {
            if (u.getPassword().indexOf(password)>=0)
                {
                    return u;
                }
                
        }
        
        return null;//fail
    }
}
