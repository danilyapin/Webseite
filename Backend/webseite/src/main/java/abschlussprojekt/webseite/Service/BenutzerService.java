package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Repository.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BenutzerService implements UserDetailsService {

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Benutzer benutzer = benutzerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Überprüfe, ob die Rolle das Präfix "ROLE_" enthält, und füge es nur hinzu, wenn es fehlt
        String role = benutzer.getRole();
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role; // Rolle mit "ROLE_" Präfix hinzufügen, falls nicht vorhanden
        }

        return User.builder()
                .username(benutzer.getEmail())
                .password(benutzer.getPasswort())
                .roles(role)  // Rolle mit dem "ROLE_" Präfix
                .build();
    }
}
