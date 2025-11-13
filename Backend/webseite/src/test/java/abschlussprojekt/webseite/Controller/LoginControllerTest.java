package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Config.SecurityConfig;
import abschlussprojekt.webseite.DTO.Login.LoginRequest;
import abschlussprojekt.webseite.DTO.Login.LoginResponse;
import abschlussprojekt.webseite.Service.LoginService;
import abschlussprojekt.webseite.Util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class LoginControllerTest {

    @MockitoBean
    private LoginService loginService;

    @MockitoBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loginUser() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPasswort("1234");

        LoginResponse expectedResponse = new LoginResponse("mockToken");

        when(loginService.login(any(LoginRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("mockToken"));

        verify(loginService, times(1)).login(any(LoginRequest.class));
    }
}