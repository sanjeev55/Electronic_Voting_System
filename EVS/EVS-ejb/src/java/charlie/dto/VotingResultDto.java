/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.dto;

import java.util.Map;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
public class VotingResultDto {
    private String questionTitle;
    private Map<String, Integer> answerChoiceCounts;

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Map<String, Integer> getAnswerChoiceCounts() {
        return answerChoiceCounts;
    }

    public void setAnswerChoiceCounts(Map<String, Integer> answerChoiceCounts) {
        this.answerChoiceCounts = answerChoiceCounts;
    }
    
    
}
