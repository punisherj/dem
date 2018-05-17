package com.bonc.dem.service.impl;

import com.bonc.dem.MyApplicationRunner;
import com.bonc.dem.config.AttachmentConfig;
import com.bonc.dem.config.MailConfig;
import com.bonc.dem.service.MailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Log4j2
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConfig mailconfig;

    @Autowired
    private AttachmentConfig attachmentConfig;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendAttachmentsMail(String to, String subject, String date) {
        MimeMessage message = mailSender.createMimeMessage();

        try {

            Context context = new Context();
            context.setVariable("date", date);
            context.setVariable("amount", MyApplicationRunner.tc.getCount());
            context.setVariable("success", MyApplicationRunner.tc.getSuccess());
            context.setVariable("percent", MyApplicationRunner.tc.getSuccessPercent());
            String emailContent = templateEngine.process("emailTemplate", context);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailconfig.getFrom());
            helper.setTo(to);
            helper.setSubject(subject + date);
            helper.setText(emailContent, true);

            //File[] fileArrays = AttachmentUtils.getAttachDir().listFiles();
            //for (File attachment : fileArrays) {
            //    FileSystemResource file = new FileSystemResource(new File(attachment.getAbsolutePath()));
            //    String fileName = attachment.getName();
            //    helper.addAttachment(fileName, file);
            //}

            File attachment = new File(attachmentConfig.getPath() + attachmentConfig.getName(date));
            FileSystemResource file = new FileSystemResource(new File(attachment.getAbsolutePath()));
            helper.addAttachment(attachmentConfig.getName(date), file);

            mailSender.send(message);
            log.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！", e);
        }
    }
}
