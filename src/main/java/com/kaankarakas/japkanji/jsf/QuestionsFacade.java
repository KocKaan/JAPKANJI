
package com.kaankarakas.japkanji.jsf;

import com.kaankarakas.japkanji.entity.Questions;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
// inheritance used to access AbstractFacade methods 
public class QuestionsFacade extends AbstractFacade<Questions> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionsFacade() {
        super(Questions.class);
    }
    
}
