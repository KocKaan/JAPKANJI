
package com.kaankarakas.japkanji.entity;

import com.kaankarakas.japkanji.Authentication.AuthUtil;
import java.io.Serializable;
import java.util.Base64;
import java.util.Collection;
import javax.enterprise.inject.Default;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Table(name = "QUESTIONS")
@NamedQueries({
    // In this section I extract data from the database by utilizing query
    @NamedQuery(name = "Questions.findAll", query = "SELECT q FROM Questions q"),
    @NamedQuery(name = "Questions.findById", query = "SELECT q FROM Questions q WHERE q.id = :id"),
    @NamedQuery(name = "Questions.findByQuestiontext", query = "SELECT q FROM Questions q WHERE q.questiontext = :questiontext"),
    @NamedQuery(name = "Questions.findByQuestiontype", query = "SELECT q FROM Questions q WHERE q.questiontype = :questiontype"),
    @NamedQuery(name = "Questions.findByQuestionanswera", query = "SELECT q FROM Questions q WHERE q.questionanswera = :questionanswera"),
    @NamedQuery(name = "Questions.findByQuestionanswerb", query = "SELECT q FROM Questions q WHERE q.questionanswerb = :questionanswerb"),
    @NamedQuery(name = "Questions.findByQuestionanswerc", query = "SELECT q FROM Questions q WHERE q.questionanswerc = :questionanswerc"),
    @NamedQuery(name = "Questions.findByQuestionanswerd", query = "SELECT q FROM Questions q WHERE q.questionanswerd = :questionanswerd"),
    @NamedQuery(name = "Questions.findByQuestionanswer", query = "SELECT q FROM Questions q WHERE q.questionanswer = :questionanswer"),
    @NamedQuery(name = "Questions.findByQuestionpoint", query = "SELECT q FROM Questions q WHERE q.questionpoint = :questionpoint")})
public class Questions implements Serializable {
   
    // Seriablizable means to conbvert the obtained data to byte. 
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "QUESTIOIMAGE")
    private byte[] questioimage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "QUESTIONTEXT")
    private String questiontext;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUESTIONTYPE")
    private Boolean questiontype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "QUESTIONANSWERA")
    private String questionanswera;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "QUESTIONANSWERB")
    private String questionanswerb;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "QUESTIONANSWERC")
    private String questionanswerc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "QUESTIONANSWERD")
    private String questionanswerd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "QUESTIONANSWER")
    private String questionanswer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUESTIONPOINT")
    private int questionpoint;
    @JoinColumn(name = "LEVELID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Levels levelid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionid")
    private Collection<Results> resultsCollection;
// cascade means to convert data type

    public Questions() {
    }

    public Questions(Integer id) {
        this.id = id;
    }
// A question needs to hold few types variables like image and answer keys
    public Questions(Integer id, byte[] questioimage, String questiontext, Boolean questiontype, String questionanswera, String questionanswerb, String questionanswerc, String questionanswerd, String questionanswer, int questionpoint) {
        this.id = id;
        this.questioimage = questioimage;
        this.questiontext = questiontext;
        this.questiontype = questiontype;
        this.questionanswera = questionanswera;
        this.questionanswerb = questionanswerb;
        this.questionanswerc = questionanswerc;
        this.questionanswerd = questionanswerd;
        this.questionanswer = questionanswer;
        this.questionpoint = questionpoint;
    }

    // getter and setter for those factors contributing to the whole question
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestioimage() {
               if (questioimage==null)
            return "";
        return Base64.getEncoder().encodeToString(questioimage);
    }

    public void setQuestioimage(byte[] questioimage) {
        this.questioimage = questioimage;
    }

    public String getQuestiontext() {
        return questiontext;
    }

    public void setQuestiontext(String questiontext) {
        this.questiontext = questiontext;
    }

    public Boolean getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(Boolean questiontype) {
        this.questiontype = questiontype;
    }

    public String getQuestionanswera() {

        return questionanswera;
    }

    public void setQuestionanswera(String questionanswera) {
        this.questionanswera = questionanswera;
    }

    public String getQuestionanswerb() {
        return questionanswerb;
    }

    public void setQuestionanswerb(String questionanswerb) {
        this.questionanswerb = questionanswerb;
    }

    public String getQuestionanswerc() {
        return questionanswerc;
    }

    public void setQuestionanswerc(String questionanswerc) {
        this.questionanswerc = questionanswerc;
    }

    public String getQuestionanswerd() {
        return questionanswerd;
    }

    public void setQuestionanswerd(String questionanswerd) {
        this.questionanswerd = questionanswerd;
    }

    public String getQuestionanswer() {
        return questionanswer;
    }

    public void setQuestionanswer(String questionanswer) {
        this.questionanswer = questionanswer;
    }

    public int getQuestionpoint() {
        return questionpoint;
    }

    public void setQuestionpoint(int questionpoint) {
        this.questionpoint = questionpoint;
    }

    public Levels getLevelid() {
        return levelid;
    }

    public void setLevelid(Levels levelid) {
        this.levelid = levelid;
    }

    public Collection<Results> getResultsCollection() {
        return resultsCollection;
    }
    
    public void setResultsCollection(Collection<Results> resultsCollection) {
        this.resultsCollection = resultsCollection;
    }

   // uniques id assigned for a object
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    
    public boolean equals(Object object) {
        //this method won't work in the case the id fields are not set
        if (!(object instanceof Questions)) {
            return false;
        }
        Questions other = (Questions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    public String toString() {
        return this.questiontext;
    }
    
}
