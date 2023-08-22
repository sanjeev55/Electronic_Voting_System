package charlie.web;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 * TODO: model after PollManagerBean
 */

import charlie.utils.StringUtils;
import charlie.dto.QuestionDto;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


@Named(value = "questionManager")
@SessionScoped
// QUESTION: 
public class QuestionManagerBean implements Serializable {
    
    private static final Logger logger = Logger.getLogger(QuestionManagerBean.class.getName());
    
    @EJB
    private QuestionLogic questionService;//TODO: connect this
    
    private QuestionDto questionInfo = new QuestionDto();
    //TODO: ADD QuestionPagination info
    
    public String addQuestion() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInsance().getExternalContext().getRequest();
        
        FacesContext context = FacesContext.getCurrentInstance();
        if (!StringUtils.hasText(questionInfo.getTitle())) {
            FacesMessage message = new FacesMessage("Title cannot be empty");
            context.addMessage("addQuestion:title", message);
            return null;
        }
        
        System.out.println("charlie.logic.impl.QuestionManagerLogic.addOrUpdateQuestion()");//TODO: CREATE 
        System.out.println(this.questionInfo);
        
        return null;// "/pages
       
    }
    
    public QuestionDto getQuestionInfo() {
        return questionInfo;
    }
    
    public void setQuestionInfo(QuestionDto questionInfo) {
        this.questionInfo = questionInfo;
    }
    
    public String getQuestionId() {
        return questionId;
    }
    
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    
}



//TODO: FINISH



















