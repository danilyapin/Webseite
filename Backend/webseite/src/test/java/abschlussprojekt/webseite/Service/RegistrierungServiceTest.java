package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Repository.BenutzerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RegistrierungServiceTest {

    private final BenutzerRepository benutzerRepository = mock(BenutzerRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final RegistrierungService service = new RegistrierungService(benutzerRepository, passwordEncoder);

    @Test
    void registerUser() {

        Benutzer benutzer = new Benutzer();
        benutzer.setEmail("test@123.com");
        benutzer.setPasswort("password");

        when(benutzerRepository.findByEmail(benutzer.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(benutzerRepository.save(any(Benutzer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.registerUser(benutzer);

        assertEquals("encodedPassword", benutzer.getPasswort());
        assertEquals("USER", benutzer.getRole());

        verify(benutzerRepository, times(1)).findByEmail("test@123.com");
        verify(passwordEncoder, times(1)).encode("password");
        verify(benutzerRepository, times(1)).save(benutzer);
    }
}