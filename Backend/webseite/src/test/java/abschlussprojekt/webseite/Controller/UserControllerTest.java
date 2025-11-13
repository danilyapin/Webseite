package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Config.SecurityConfig;
import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Service.UserService;
import abschlussprojekt.webseite.Util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test@123.de") // Spring Security Mock User
    void getProfil_whenAuthenticated() throws Exception {

        Benutzer benutzer = new Benutzer();
        benutzer.setEmail("test@123.de");

        when(userService.getProfilByEmail("test@123.de")).thenReturn(Optional.of(benutzer));

        mockMvc.perform(get("/api/profil")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@123.de"));

        verify(userService, times(1)).getProfilByEmail("test@123.de");
    }

    @Test
    @WithMockUser(username = "notfound@123.de")
    void getProfil_whenNotFound() throws Exception {

        when(userService.getProfilByEmail("notfound@123.de")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/profil")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getProfilByEmail("notfound@123.de");
    }
}