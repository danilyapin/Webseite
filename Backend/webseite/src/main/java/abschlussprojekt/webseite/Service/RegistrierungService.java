package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Repository.BenutzerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrierungService {

    private final BenutzerRepository benutzerRepository;
    private final PasswordEncoder passwordEncoder;


    public RegistrierungService(BenutzerRepository benutzerRepository, PasswordEncoder passwordEncoder) {
        this.benutzerRepository = benutzerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(Benutzer benutzer) {
        if(benutzerRepository.findByEmail(benutzer.getEmail()).isPresent()){
            throw new IllegalArgumentException("E-Mail ist bereits registriert!");
        }

        if (benutzer.getRole() == null || !benutzer.getRole().isEmpty()) {
            benutzer.setRole("USER");
        }

        if (benutzer.getName() == null || !benutzer.getName().isEmpty()) {
            benutzer.setName("Benutzer");
        }

        benutzer.setPasswort(passwordEncoder.encode(benutzer.getPasswort()));
        benutzerRepository.save(benutzer);
    }
}