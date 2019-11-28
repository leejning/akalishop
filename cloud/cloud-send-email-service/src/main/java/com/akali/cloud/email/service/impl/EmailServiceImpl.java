package com.akali.cloud.email.service.impl;

import com.akali.cloud.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;


@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
    private JavaMailSender mailSender;
	@Value("${spring.mail.from}")
    private String from;
	
	/**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }


	@Override
	public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendHtmlMail(String to, String subject, String content, String... cc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendAttachmentsMail(String to, String subject, String content, String filePath)
			throws MessagingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId)
			throws MessagingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId,
			String... cc) {
		// TODO Auto-generated method stub
		
	}

}
