package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Repository.BenutzerRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final BenutzerRepository benutzerRepository = mock(BenutzerRepository.class);
    private final UserService userService = new UserService(benutzerRepository);

    @Test
    void getProfilByEmail() {

            Benutzer benutzer = new Benutzer();
            benutzer.setEmail("test@example.com");

            when(benutzerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(benutzer));

            Optional<Benutzer> result = userService.getProfilByEmail("test@example.com");

            assertTrue(result.isPresent());
            assertEquals("test@example.com", result.get().getEmail());
            verify(benutzerRepository, times(1)).findByEmail("test@example.com");
    }
}