package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.DTO.Login.LoginRequest;
import abschlussprojekt.webseite.DTO.Login.LoginResponse;
import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Repository.BenutzerRepository;
import abschlussprojekt.webseite.Util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    private final BenutzerRepository benutzerRepository = mock(BenutzerRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);
    private final LoginService loginService = new LoginService(benutzerRepository, passwordEncoder, jwtTokenUtil);

    @Test
    void login() {

        LoginRequest request = new LoginRequest();
        request.setEmail("test@123.com");
        request.setPasswort("1234");

        Benutzer benutzer = new Benutzer();
        benutzer.setEmail("test@123.com");
        benutzer.setPasswort("encoded123");
        benutzer.setRole("USER");

        when(benutzerRepository.findByEmail("test@123.com")).thenReturn(Optional.of(benutzer));
        when(passwordEncoder.matches("1234", "encoded123")).thenReturn(true);
        when(jwtTokenUtil.generateToken("test@123.com", "USER")).thenReturn("mockToken");

        LoginResponse response = loginService.login(request);

        assertNotNull(response);
        assertEquals("mockToken", response.getToken());
        verify(jwtTokenUtil, times(1)).generateToken("test@123.com", "USER");
    }
}