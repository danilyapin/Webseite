package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Repository.BenutzerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BenutzerServiceTest {

    private final BenutzerRepository repo = Mockito.mock(BenutzerRepository.class);
    private final BenutzerService service = new BenutzerService(repo);

    @Test
    void loadUserByUsername() {

        Benutzer benutzer = new Benutzer();
        benutzer.setEmail("test@gmail.com");
        benutzer.setPasswort("passwort");
        benutzer.setRole("USER");

        when(repo.findByEmail("test@gmail.com")).thenReturn(Optional.of(benutzer));

        UserDetails user = service.loadUserByUsername("test@gmail.com");
        assertEquals("test@gmail.com", user.getUsername());
        assertEquals("passwort", user.getPassword());
        assertTrue(user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority()
                        .equals("ROLE_USER")));

        verify(repo, times(1)).findByEmail(benutzer.getEmail());
    }
}