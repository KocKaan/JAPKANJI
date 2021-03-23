package com.kaankarakas.japkanji.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// query function for fetching results from database 
@Entity
@Table(name = "RESULTS")
@NamedQueries({
    @NamedQuery(name = "Results.findAll", query = "SELECT r FROM Results r"),
    @NamedQuery(name = "Results.findById", query = "SELECT r FROM Results r WHERE r.id = :id"),
    @NamedQuery(name = "Results.findByPoints", query = "SELECT r FROM Results r WHERE r.points = :points"),
    @NamedQuery(name = "Results.findByAnswer", query = "SELECT r FROM Results r WHERE r.answer = :answer"),
    @NamedQuery(name = "Results.findByStatus", query = "SELECT r FROM Results r WHERE r.status = :status")})

public class Results implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POINTS")
    private int points;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ANSWER")
    private String answer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private Boolean status;
    @JoinColumn(name = "LEVELID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Levels levelid;
    @JoinColumn(name = "QUESTIONID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Questions questionid;
    @JoinColumn(name = "USERID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users userid;

    public Results() {
    }

    public Results(Integer id) {
        this.id = id;
    }

    public Results(Integer id, int points, String answer, Boolean status) {
        this.id = id;
        this.points = points;
        this.answer = answer;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTotalpoints() {
        int total=0;
       // Collecting results as a collection
        Collection<Results> rslts=userid.getResultsCollection();
        for(Results r:rslts)
        {
            if(r.levelid.getId()==this.levelid.getId())
            {
                total+=r.getPoints();
            }
        }
        return total;
    }
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Levels getLevelid() {
        return levelid;
    }

    public void setLevelid(Levels levelid) {
        this.levelid = levelid;
    }

    public Questions getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Questions questionid) {
        this.questionid = questionid;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    
    public boolean equals(Object object) {
        //this method won't work in the case the id fields are not set
        if (!(object instanceof Results)) {
            return false;
        }
        Results other = (Results) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.status.toString()+" "+this.answer;
    }
    
}
