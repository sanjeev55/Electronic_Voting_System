/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package charlie.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import javax.faces.context.FacesContext;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Named(value = "localeBean")
@SessionScoped
public class LocaleBean implements Serializable {
    
    private Locale userLocale;

    public Locale getUserLocale() {
        if (userLocale == null) {
            userLocale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        }
        if (userLocale == null) {
            userLocale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
        }
        return userLocale;
    }

    public void setUserLocale(Locale userLocale) {
        this.userLocale = userLocale;
    }

    public void selectEnglish() {
        userLocale = Locale.ENGLISH;
    }

    public void selectGerman() {
        userLocale = Locale.GERMAN;
    }

    public Date getCurrentDate() {
        return new Date();
    }
}
