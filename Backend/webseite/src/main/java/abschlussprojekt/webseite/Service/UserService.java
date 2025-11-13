package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Repository.BenutzerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final BenutzerRepository benutzerRepository;

    public UserService(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    public Optional<Benutzer> getProfilByEmail(String email) {
        return  benutzerRepository.findByEmail(email);
    }
}