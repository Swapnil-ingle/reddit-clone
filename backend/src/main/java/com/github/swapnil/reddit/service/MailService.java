package com.github.swapnil.reddit.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.swapnil.reddit.exception.SpringRedditException;
import com.github.swapnil.reddit.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	private final JavaMailSender mailSender;

	private final MailContentBuilder mailContentBuilder;

	@Async
	public void sendMail(NotificationEmail notifEmail) {
		MimeMessagePreparator msgPreparator = mimeMsg -> {
			MimeMessageHelper msgHelper = new MimeMessageHelper(mimeMsg);
			msgHelper.setFrom("dummy@gmail.com");
			msgHelper.setTo(notifEmail.getRecipient());
			msgHelper.setSubject(notifEmail.getSubject());
			msgHelper.setText(mailContentBuilder.build(notifEmail.getBody()));
		};

		try {
			mailSender.send(msgPreparator);
			log.info("Activation email sent!");
		} catch (MailException e) {
			log.error("Exception occurred when sending mail", e);
			throw new SpringRedditException("Exception occurred when sending mail to " + notifEmail.getRecipient());
		}
	}
}
