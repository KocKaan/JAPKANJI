package com.kaankarakas.japkanji.session;

import com.kaankarakas.japkanji.Authentication.AuthUtil;
import com.kaankarakas.japkanji.jsf.ResultsFacade;
import com.kaankarakas.japkanji.entity.Results;
import com.kaankarakas.japkanji.entity.Users;
import com.kaankarakas.japkanji.util.JsfUtil;
import com.kaankarakas.japkanji.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;

@Named("resultsController")
@SessionScoped
public class ResultsController implements Serializable {

    @EJB
    private com.kaankarakas.japkanji.jsf.ResultsFacade ejbFacade;
    private List<Results> items = null;
    private Results selected;

    public ResultsController() {
    }

    public Results getSelected() {
        return selected;
    }

    public void setSelected(Results selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ResultsFacade getFacade() {
        return ejbFacade;
    }

    public Results prepareCreate() {
        selected = new Results();
        initializeEmbeddableKey();
        return selected;
    }
    
    private void filterUser()
    {
        HttpSession ses=AuthUtil.getSession();
        Users user=(Users)ses.getAttribute("userid");
        Object is=user.getResultsCollection();
        items=(List<Results>) is;
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ResultsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ResultsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ResultsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Results> getItems() {
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

    public Results getResults(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Results> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Results> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Results.class)
    public static class ResultsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ResultsController controller = (ResultsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "resultsController");
            return controller.getResults(getKey(value));
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
            if (object instanceof Results) {
                Results o = (Results) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Results.class.getName()});
                return null;
            }
        }

    }

}
