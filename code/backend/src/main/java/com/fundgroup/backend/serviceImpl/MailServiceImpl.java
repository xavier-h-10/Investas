package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.service.MailService;

import java.io.File;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class MailServiceImpl implements MailService {

  private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

  @Value("${spring.mail.username}")
  private String from;

  private JavaMailSender mailSender;

  @Autowired
  void setMailSender(JavaMailSender mailSender) {
    JavaMailSenderImpl sender = new JavaMailSenderImpl();
    sender.setHost("smtp.office365.com");
    sender.setUsername("sjtu_fundsystem@outlook.com");
    sender.setPassword("liyiyan_yyds");
    sender.setProtocol("smtp");
    sender.setDefaultEncoding("utf-8"); // 中文支持
    sender.setPort(587);
    Properties properties = sender.getJavaMailProperties();
    properties.put("mail.smtp.auth", true);
    properties.put("mail.smtp.timeout", "25000");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.ssl.trust", "*");
    properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // 这句话很重要，否则会出现无法将套接字转换为TLS的错误 20210804
    this.mailSender = sender;

  }

  @Override
  public void sendSimpleMail(String to, String subject, String content) {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);// 收信人
    message.setSubject(subject);// 主题
    message.setText(content);// 内容
    mailSender.send(message);
  }

  @Override
  public void sendHtmlMail(String to, String subject, String content) {

    logger.info("发送HTML邮件开始：{},{},{}", to, subject, content);
    MimeMessage message = mailSender.createMimeMessage();
    String nick = ""; // 设置发件人姓名
    try {
      nick = javax.mail.internet.MimeUtility.encodeText("交我赚");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    try {
      message.setFrom(new InternetAddress(nick + " <" + from + ">"));
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(new InternetAddress(nick + " <" + from + ">"));
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);// true代表支持html
      mailSender.send(message);
      logger.info("发送HTML邮件成功");
    } catch (MessagingException e) {
      logger.error("发送HTML邮件失败：", e);
    }
  }

  @Override
  public void sendAttachmentMail(String to, String subject, String content, String filePath) {

    logger.info("发送带附件邮件开始：{},{},{},{}", to, subject, content, filePath);
    MimeMessage message = mailSender.createMimeMessage();

    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);
      FileSystemResource file = new FileSystemResource(new File(filePath));
      String fileName = file.getFilename();
      helper.addAttachment(fileName, file);// 添加附件，可多次调用该方法添加多个附件
      mailSender.send(message);
      logger.info("发送带附件邮件成功");
    } catch (MessagingException e) {
      logger.error("发送带附件邮件失败", e);
    }
  }

  @Override
  public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {

    logger.info("发送带图片邮件开始：{},{},{},{},{}", to, subject, content, rscPath, rscId);
    MimeMessage message = mailSender.createMimeMessage();

    MimeMessageHelper helper;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);
      FileSystemResource res = new FileSystemResource(new File(rscPath));
      helper.addInline(rscId, res);// 重复使用添加多个图片
      mailSender.send(message);
      logger.info("发送带图片邮件成功");
    } catch (MessagingException e) {
      logger.error("发送带图片邮件失败", e);
    }
  }
}
