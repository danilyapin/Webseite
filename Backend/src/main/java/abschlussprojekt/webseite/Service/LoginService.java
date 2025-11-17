package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.DTO.Login.LoginRequest;
import abschlussprojekt.webseite.DTO.Login.LoginResponse;
import abschlussprojekt.webseite.Models.Benutzer;
import abschlussprojekt.webseite.Repository.BenutzerRepository;
import abschlussprojekt.webseite.Util.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final BenutzerRepository benutzerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginService(BenutzerRepository benutzerRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.benutzerRepository = benutzerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Benutzer benutzer = benutzerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Benutzer nicht gefunden"));

        boolean passwordCorrect = passwordEncoder.matches(loginRequest.getPasswort(), benutzer.getPasswort());
        if (!passwordCorrect) {
            throw new IllegalArgumentException("Ungültige Anmeldedaten – Passwort stimmt nicht");
        }

        String token = jwtTokenUtil.generateToken(benutzer.getEmail(), benutzer.getRole());
        return new LoginResponse(token);
    }
}