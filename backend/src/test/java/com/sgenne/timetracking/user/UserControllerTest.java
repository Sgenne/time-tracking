package com.sgenne.timetracking.user;

import com.sgenne.timetracking.security.JwtRequestFilter;
import com.sgenne.timetracking.security.accessToken.AccessTokenUtils;
import com.sgenne.timetracking.user.model.Role;
import com.sgenne.timetracking.user.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.sgenne.timetracking.user.UserController.USER_ROOT_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean private UserService userService;
    @MockBean private UserDetailsService userDetailsService;
    @MockBean private AccessTokenUtils accessTokenUtils;

    @Autowired private UserController userController;
    @Autowired private MockMvc mockMvc;

    @Test
    @WithMockUser
    void getUserById() throws Exception {
        Long userId = 1L;
        String username = "username";
        String password = "password";
        List<Role> roles = List.of(Role.USER);

        User user = new User(username, password, roles);
        user.setId(userId);

        when(userService.getUserById(userId))
                .thenReturn(user);

        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders
                                .get(USER_ROOT_URL + "/" + userId))
                        .andReturn()
                        .getResponse();

        assertEquals(response.getStatus(), 200);
    }
}