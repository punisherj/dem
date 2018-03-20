package com.bonc.dem.service.impl;

import com.bonc.dem.config.ExcelConfig;
import com.bonc.dem.config.MailConfig;
import com.bonc.dem.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Service
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConfig mailconfig;

    @Autowired
    ExcelConfig excelConfig;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailconfig.getFromMail());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }

    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailconfig.getFromMail());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            //File[] fileArrays = AttachmentUtils.getAttachDir().listFiles();
            //for (File attachment : fileArrays) {
            //    FileSystemResource file = new FileSystemResource(new File(attachment.getAbsolutePath()));
            //    String fileName = attachment.getName();
            //    helper.addAttachment(fileName, file);
            //}

            File attachment = new File(excelConfig.getAttachmentPath() + excelConfig.getAttachmentName(new Date()));
            FileSystemResource file = new FileSystemResource(new File(attachment.getAbsolutePath()));
            String fileName = attachment.getName();
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailconfig.getFromMail());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }

    @Override
    public void sendTemplateMail(String to, String subject) {
        Context context = new Context();
        context.setVariable("id", "006");
        context.setVariable("name","徐奎践");
        String emailContent = templateEngine.process("emailTemplate", context);

        this.sendHtmlMail(to, subject, emailContent);
    }
}
