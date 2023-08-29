package charlie.web;

/**
 *
 * @author Eric Babcock <ebabcock@uni-koblenz.de>
 * TODO: model after PollManagerBean
 */
import charlie.domain.Page;
import charlie.domain.Result;
import charlie.dto.QuestionDto;
import charlie.entity.QuestionTypeEnum;
//import charlie.entity.QuestionTypeEnum;
import charlie.logic.QuestionLogic;
import charlie.utils.StringUtils;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


@Named(value = "questionManagerBean")
@SessionScoped
public class QuestionManagerBean implements Serializable {
    
    private static final Logger logger = Logger.getLogger(QuestionManagerBean.class.getName());
    
    @EJB
    private QuestionLogic questionService;//TODO: connect this
    
    private QuestionDto questionInfo = new QuestionDto();
    private String questionId;
    
    public List<QuestionTypeEnum> getQuestionTypes() {
        return Arrays.asList(QuestionTypeEnum.values());
    }
     
    public String addQuestion() {
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequest();
        String pollUuid = request.getParameter("pollUuid");
        
        questionInfo.setUuid(pollUuid);
        
        
        FacesContext context = FacesContext.getCurrentInstance();
        if (!StringUtils.hasText(questionInfo.getTitle())) {
            FacesMessage message = new FacesMessage("Title cannot be empty");
            context.addMessage("addQuestion:title", message);
            return null;
        }
        
        System.out.println("charlie.logic.impl.QuestionManagerLogic.addOrUpdateQuestion()");//TODO: CREATE 
        System.out.println(this.questionInfo);
        
        Result<QuestionDto> result = questionService.addQuestion(this.questionInfo);
        return null;// "TODO       
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



















