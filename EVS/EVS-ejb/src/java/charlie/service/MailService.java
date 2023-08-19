/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charlie.service;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Sanjeev Sun Shakya <sshakya@uni-koblenz.de>
 */
@Stateless
public class MailService implements Serializable {
    @Resource(lookup = "mail/uniko-mail")
    private Session mailSession;
    
    
    public void sendMail(String recipient, String subject, String message) throws MessagingException {
        Message msg = new MimeMessage(mailSession);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipient, false));
        msg.setFrom(InternetAddress.parse("team-charlie@uni.ko")[0]);
        msg.setText(message);
        Transport.send(msg);
    }
}
