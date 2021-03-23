
package com.kaankarakas.japkanji.jsf;

import com.kaankarakas.japkanji.entity.Levels;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



// inheritance used to access AbstractFacade methods 
public class LevelsFacade extends AbstractFacade<Levels> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LevelsFacade() {
        super(Levels.class);
    }
    
}
