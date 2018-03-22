package com.bonc.dem.service.impl;

import com.bonc.dem.MyApplicationRunner;
import com.bonc.dem.config.ExcelConfig;
import com.bonc.dem.config.MailConfig;
import com.bonc.dem.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public void sendAttachmentsMail(String to, String subject, String date) {
        MimeMessage message = mailSender.createMimeMessage();

        try {

            Context context = new Context();
            context.setVariable("date", date);
            context.setVariable("amount", MyApplicationRunner.result[0]);
            context.setVariable("success", MyApplicationRunner.result[1]);
            context.setVariable("percent", MyApplicationRunner.result[2]);
            String emailContent = templateEngine.process("emailTemplate", context);

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailconfig.getFromMail());
            helper.setTo(to);
            helper.setSubject(subject + date);
            helper.setText(emailContent, true);

            //File[] fileArrays = AttachmentUtils.getAttachDir().listFiles();
            //for (File attachment : fileArrays) {
            //    FileSystemResource file = new FileSystemResource(new File(attachment.getAbsolutePath()));
            //    String fileName = attachment.getName();
            //    helper.addAttachment(fileName, file);
            //}

            File attachment = new File(excelConfig.getAttachmentPath() + excelConfig.getAttachmentName(date));
            FileSystemResource file = new FileSystemResource(new File(attachment.getAbsolutePath()));
            helper.addAttachment(excelConfig.getAttachmentName(date), file);

            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }
}
