
package com.kaankarakas.japkanji.jsf;

import com.kaankarakas.japkanji.entity.Results;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
// inheritance used to access AbstractFacade methods 
public class ResultsFacade extends AbstractFacade<Results> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResultsFacade() {
        super(Results.class);
    }
    
}
