package com.kaankarakas.japkanji.session;

import com.kaankarakas.japkanji.Authentication.AuthUtil;
import com.kaankarakas.japkanji.entity.Levels;
import com.kaankarakas.japkanji.jsf.QuestionsFacade;
import com.kaankarakas.japkanji.entity.Questions;
import com.kaankarakas.japkanji.entity.Results;
import com.kaankarakas.japkanji.entity.Users;
import com.kaankarakas.japkanji.util.JsfUtil;
import com.kaankarakas.japkanji.util.JsfUtil.PersistAction;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.model.UploadedFile;

@Named("questionsController")
@SessionScoped
public class QuestionsController implements Serializable {

    @EJB
    private com.kaankarakas.japkanji.jsf.QuestionsFacade ejbFacade;
    private List<Questions> items = null;
    private Questions selected;
    private Levels level;
    private String selectedResult;
    
    private int timercount=-1;
    private boolean teststarted=false;
    public QuestionsController() {
    }
    
    public int getTimercount() {
        if(timercount>=0)
            timercount--;
        if (timercount<0 && teststarted)
        {
            finishtest();
        }
        return timercount;
    }

    public String getResult()
    {
        return selectedResult;
    }
    public void setResult(String selectedr) {
        this.selectedResult = selectedr;
    }
    public boolean getTeststarted() {
        return teststarted;
    }
    


    
    public Levels getLevel() {
        return level;
    }

    public void setLevel(Levels level) {
        this.level = level;
    }
    
    public Questions getSelected() {
        return selected;
    }

    public void setSelected(Questions selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuestionsFacade getFacade() {
        return ejbFacade;
    }
    public void starttest()
    {
        TestResults tr= new TestResults();
        if (level==null)
            this.addMessage("Select Level!");
        else
        {
            selectedResult="";
            timercount=level.getLeveltime();
            teststarted=true;
            this.addMessage("Test Started!");
            Collection<Results> res=level.getResultsCollection();
            HttpSession ses=AuthUtil.getSession();
            Users user=(Users)ses.getAttribute("userid");
            for(Results r:res)
            {
                if (r.getUserid().getId().intValue()==user.getId().intValue())
                {
                    tr.DeleteOldResult(r);                    
                }
            }
        }
    }


    public void finishtest()
    {
        level=null;
        timercount=0;
        teststarted=false;
        selectedResult="";
        items = getFacade().findAll();
        this.addMessage("Test Finished!");
    }
    public void onnextpage(PageEvent event)
    {
        selectedResult="";
        
    }
    public void onsaveresult() {
        if (level==null)
            return;
        TestResults tr = new TestResults();
        String newstr= selectedResult; //KAAN Next event ve first event bulup bunu setle
        Collection<Results> res=selected.getResultsCollection();
        HttpSession ses=AuthUtil.getSession();
        Users user=(Users)ses.getAttribute("userid");
        boolean bres=false;
        for(Results r:res)
        {
            if (r.getUserid().getId().intValue()==user.getId().intValue())
            {
                bres=true;
                tr.SaveResult(r, newstr,selected);
            }

         }
        if (!bres)
        {
            tr.SaveNewResult(newstr, user,level,selected);
        }
        
   }


    private void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    public void onLevelsChange() {
        if(level !=null)
        {
            items = (List<Questions>) level.getQuestionsCollection();
        //    result= new Results();
          //  result.setLevelid(level);
            //result.setUserid(userid);
        }

    }

    public void upload(FileUploadEvent event) {
        try {
            UploadedFile ff=event.getFile();
            if (ff==null)
                return;
            InputStream is = ff.getInputstream();
            //byte[] images = IOUtils.toByteArray(fin2);
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[(int)ff.getSize()];
            int length;
            int len=0;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, len, length);
                len+=length;
            }
            
            
            
            this.selected.setQuestioimage(result.toByteArray());

        } catch (Exception e) {

        }
 
    }
    public Questions prepareCreate() {
        selected = new Questions();
        selected.setQuestionanswera(" ");
        selected.setQuestionanswerb(" ");
        selected.setQuestionanswerc(" ");
        selected.setQuestionanswerd(" ");
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("QuestionsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("QuestionsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("QuestionsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Questions> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Questions getQuestions(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Questions> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Questions> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Questions.class)
    public static class QuestionsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionsController controller = (QuestionsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questionsController");
            return controller.getQuestions(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Questions) {
                Questions o = (Questions) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Questions.class.getName()});
                return null;
            }
        }

    }

}
