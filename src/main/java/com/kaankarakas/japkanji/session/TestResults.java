package com.kaankarakas.japkanji.session;

import com.kaankarakas.japkanji.entity.Levels;
import com.kaankarakas.japkanji.entity.Questions;
import com.kaankarakas.japkanji.entity.Results;
import com.kaankarakas.japkanji.entity.Users;
import java.util.logging.Level;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;


public class TestResults {
    public TestResults()
    {}
    
    // This public function is inside TestResults class and used for
    //editing answered question 
    public void SaveResult(Results r, String newstr,Questions selected) {
        r.setAnswer(newstr);
        if (newstr.compareTo(selected.getQuestionanswer())==0)
        {
            
            r.setStatus(true);
            r.setPoints(selected.getQuestionpoint());
        }
        else
        {
            r.setStatus(false);
            r.setPoints(0);
        }
        CreateEditResults(r);
    }

    public void SaveNewResult(String newstr, Users user, Levels level,Questions selected) {
        Results re = new Results();
        //Resetting level id, questionid, answer given and user
        re.setLevelid(level);  
        re.setQuestionid(selected);
        re.setAnswer(newstr);
        re.setUserid(user);
        if (newstr.compareTo(selected.getQuestionanswer())==0)
        {
            
            re.setStatus(true); //Used when the answer given is correct
            re.setPoints(selected.getQuestionpoint());
        }
        else
        {//Used when the answer given is false. 
            re.setStatus(false);
            re.setPoints(0);
        }
        CreateEditResults(re);
    }

    private void CreateEditResults(Results r) {
        EntityManager entityManager =  Persistence.createEntityManagerFactory("my_persistence_unit").createEntityManager();
        
        try {
            
            entityManager.persist(r);
            UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            transaction.setTransactionTimeout(300);
            
            transaction.begin();
            entityManager.joinTransaction();
            transaction.commit();
        }
        catch (Exception e) {
        }
    }
    public void DeleteOldResult(Results r) {
        EntityManager entityManager =  Persistence.createEntityManagerFactory("my_persistence_unit").createEntityManager();
        // Try catch method will be used to test while the program is executed 
        try {
             entityManager.remove(entityManager.merge(r));// delete the old results 
            UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            transaction.setTransactionTimeout(300);
            
            transaction.begin();
            entityManager.joinTransaction();
            transaction.commit();
        }
        catch (Exception e) {
        }
    }

    
}
