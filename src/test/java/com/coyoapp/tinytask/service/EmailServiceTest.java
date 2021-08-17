package com.coyoapp.tinytask.service;

import com.coyoapp.tinytask.constant.Status;
import com.coyoapp.tinytask.domain.Task;
import com.coyoapp.tinytask.domain.User;
import com.coyoapp.tinytask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

  @Mock
  private JavaMailSender javaMailSender;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private EmailServiceImpl emailService;

  @Test
  void test_send_email() {
    MimeMessage mimeMessage = mock(MimeMessage.class);
    when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    User user = new User();
    Task task = new Task();
    task.setStatus(Status.UNFINISHED);
    task.setName("test");
    user.setEmail("no-replay@test.com");
    user.setTask(Collections.singletonList(task));
    when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
    doNothing().when(javaMailSender).send(mimeMessage);
    setField(emailService, "mailHost", "test");
    emailService.sendMail();
    verify(javaMailSender, times(1)).send(mimeMessage);
  }
}
