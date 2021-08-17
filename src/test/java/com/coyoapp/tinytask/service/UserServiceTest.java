package com.coyoapp.tinytask.service;

import com.coyoapp.tinytask.domain.User;
import com.coyoapp.tinytask.dto.UserRequest;
import com.coyoapp.tinytask.dto.UserResponse;
import com.coyoapp.tinytask.repository.UserRepository;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


/**
 * @author s.abdessalem on 10/9/2021
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private MapperFacade mapperFacade;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  @DisplayName("test create user")
  void test_create_user_return_200() {
    UserRequest userRequest = mock(UserRequest.class);
    User user = mock(User.class);
    User savedUser = mock(User.class);
    UserResponse userResponse = mock(UserResponse.class);
    doReturn(user).when(mapperFacade).map(userRequest, User.class);
    when(userRepository.save(user)).thenReturn(savedUser);
    doReturn(userResponse).when(mapperFacade).map(savedUser, UserResponse.class);
    UserResponse actualResponse = userService.createUser(userRequest);
    assertThat(actualResponse).isEqualTo(userResponse);
  }

  @Test
  @DisplayName("test delete user")
  void test_delete_user_return_200() {
    String id = "user-id";
    User user = mock(User.class);
    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    userService.deleteUser(id);
    verify(userRepository).delete(user);

  }

  @Test
  @DisplayName("test get list of users")
  void test_get_users_return_200() {
    User user = mock(User.class);
    UserResponse userResponse = mock(UserResponse.class);
    List<UserResponse> list = Collections.singletonList(userResponse);
    doReturn(userResponse).when(mapperFacade).map(user, UserResponse.class);
    when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
    List<UserResponse> actualList = userService.getUsers();
    assertThat(actualList).isEqualTo(list);
  }

}
