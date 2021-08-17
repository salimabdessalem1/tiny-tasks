package com.coyoapp.tinytask.service;


import com.coyoapp.tinytask.constant.Status;
import com.coyoapp.tinytask.domain.Task;
import com.coyoapp.tinytask.domain.User;
import com.coyoapp.tinytask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender javaMailSender;
  private final UserRepository userRepository;
  @Value("${spring.mail.host}")
  private String mailHost;

  @Autowired
  EmailServiceImpl(JavaMailSender sender,
                   UserRepository userRepository) {
    this.javaMailSender = sender;
    this.userRepository = userRepository;
  }

  @Scheduled(cron ="0 0 0 * * *")
  public void sendMail() {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    try {
      log.info("start mailing");
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
      List<User> userList = userRepository.findAll();
      for (User user : userList) {
       String taskNames = user.getTask().stream().
          filter(task -> task.getStatus() == Status.UNFINISHED).
          map(task -> "-"+task.getName()+"\n").
          collect(Collectors.joining());
        mimeMessageHelper.setSubject("Your daily unfinished tasks");
        mimeMessageHelper.setFrom(mailHost);
        mimeMessageHelper.setTo(user.getEmail());

        mimeMessageHelper.setText(taskNames);

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
        log.debug("finish mailing");
      }
    } catch (MessagingException e) {
      e.printStackTrace();
      log.error("error in mailing {}", e.getMessage());
    }

  }


}
