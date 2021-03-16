package com.curtis.talent_recruitment.utils.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @author Colin
 * @version 1.0.0
 * @description 邮件工具类
 * @date 2020/10/25 16:25
 */
@Component
public class MailUtils {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String addr, String subject, String content) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        // true 代表是 multipart 类型
        MimeMessageHelper helper = new MimeMessageHelper( message, false );
        helper.setSubject( subject );
        helper.setFrom( "colinchong@163.com" );
        helper.setTo( addr );
        // true 代表支持 HTML
        helper.setText( content, true );
        // 添加附件
        // helper.addAttachment( "通知.docx", new FileSystemResource( "D:\\develop\\springboot.docx" ) );
        mailSender.send( message );
    }
}
