package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    // ✅ TEST SIGNUP
    @Test
    public void testSignup() throws Exception {

        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("1234");

        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "email": "test@mail.com",
                          "password": "1234"
                        }
                        """))
                .andExpect(status().isOk());
    }

    // ✅ TEST LOGIN SUCCESS
    @Test
    public void testLoginSuccess() throws Exception {

        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("1234");

        when(userRepository.findByEmail("test@mail.com")).thenReturn(user);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "email": "test@mail.com",
                          "password": "1234"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS"));
    }

    // ❌ TEST LOGIN FAILURE
    @Test
    public void testLoginFail() throws Exception {

        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("1234");

        when(userRepository.findByEmail("test@mail.com")).thenReturn(user);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "email": "test@mail.com",
                          "password": "wrong"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().string("FAIL"));
    }
}