package com.example.demo;

import com.example.demo.dao.demo_userMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	JavaMailSender sender;
	@Autowired
	demo_userMapper usermapper;

	@Test
	public void sendSimpleMail() throws Exception {
		MimeMessage message = null;
		boolean flag = true;
		try {
			message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("839737769@qq.com");
			helper.setTo("360350086@qq.com");
			helper.setSubject("邮件主题");
			helper.setText("内容");
			sender.send(message);
		} catch (Exception e) {
			flag = false;
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("flag:"+flag);
	}

	@Test
	public void url(){
		System.out.println(usermapper.selectPowerByName("root").getUrl());
	}
}
