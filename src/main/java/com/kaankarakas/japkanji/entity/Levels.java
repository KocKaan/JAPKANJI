package com.kaankarakas.japkanji.entity;

import com.kaankarakas.japkanji.Authentication.AuthUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "LEVELS")
@NamedQueries({
    // In this section I extract data from the database by utilizing query
    @NamedQuery(name = "Levels.findAll", query = "SELECT l FROM Levels l"),
    @NamedQuery(name = "Levels.findById", query = "SELECT l FROM Levels l WHERE l.id = :id"),
    @NamedQuery(name = "Levels.findByLevelname", query = "SELECT l FROM Levels l WHERE l.levelname = :levelname"),
    @NamedQuery(name = "Levels.findByLeveltime", query = "SELECT l FROM Levels l WHERE l.leveltime = :leveltime"),
    @NamedQuery(name = "Levels.findByLevelorder", query = "SELECT l FROM Levels l WHERE l.levelorder = :levelorder")})
public class Levels implements Serializable {
// Creating private methods to make sure the method cannot be used used this class. 
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "LEVELNAME")
    private String levelname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEVELTIME")
    private int leveltime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LEVELORDER")
    private int levelorder;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "levelid")
    private Collection<Questions> questionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "levelid")
    private Collection<Results> resultsCollection;
    
    public Levels() {
    }

    public Levels(Integer id) {
        this.id = id;
    }

    public Levels(Integer id, String levelname, int leveltime, int levelorder) {
        this.id = id;
        this.levelname = levelname;
        this.leveltime = leveltime;
        this.levelorder = levelorder;
    }

    // These getters and setters are used to update the data extracted from the database. 
    //Thse relatioships created between the database and the program is cruial as it 
    //ensures the data entered olso effects the overall system (updated).
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public int getLeveltime() {
        return leveltime;
    }

    public void setLeveltime(int leveltime) {
        this.leveltime = leveltime;
    }

    public int getLevelorder() {
        return levelorder;
    }

    public void setLevelorder(int levelorder) {
        this.levelorder = levelorder;
    }
    public void setTotalresult(Integer Totalresult)
    {
        
    }
    public Integer getTotalresult()
    {
        Integer res = 0;
        for(Questions q:this.questionsCollection)
        {    
          
                res+=q.getQuestionpoint();
        }
        return res;
    }
    public void setTestresult(Integer Totalresult)
    {
        
    }
    public Integer getTestresult()
    {
        HttpSession ses=AuthUtil.getSession();
        Users user=(Users)ses.getAttribute("userid");
        Integer res = 0;
        for(Results r:resultsCollection)
        {         
            if (r.getUserid().getId()==user.getId())
                if(r.getStatus())
                    res+=r.getPoints();
            // This simple mathematical equation ensures the points gathered
            // is added on top of each other at a single level for the particular id player
        }
        return res;
    }
    public Collection<Questions> getQuestionsCollection() {
        return questionsCollection;
    }

    public void setQuestionsCollection(Collection<Questions> questionsCollection) {
        this.questionsCollection = questionsCollection;
    }

    public Collection<Results> getResultsCollection() {
        return resultsCollection;
    }

    public void setResultsCollection(Collection<Results> resultsCollection) {
        this.resultsCollection = resultsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // this method won't work in the case the id fields are not set
        if (!(object instanceof Levels)) {
            return false;
        }
        Levels other = (Levels) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.levelname;
    }
    
}
