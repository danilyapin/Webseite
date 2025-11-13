package abschlussprojekt.webseite.Controller;

import abschlussprojekt.webseite.Config.SecurityConfig;
import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Service.RegistrierungService;
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

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrierungController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class RegistrierungControllerTest {

    @MockitoBean
    private RegistrierungService registrierungService;

    @MockitoBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerUser() throws Exception {

        Benutzer benutzer = new Benutzer();
        benutzer.setEmail("test@gmail.com");
        benutzer.setPasswort("password");

        doNothing().when(registrierungService).registerUser(benutzer);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(benutzer)))
                .andExpect(status().isOk())
                .andExpect(content().string("Benutzer erfolgreich registriert!"));
    }
}