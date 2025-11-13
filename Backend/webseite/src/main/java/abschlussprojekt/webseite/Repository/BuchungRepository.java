package abschlussprojekt.webseite.Repository;

import abschlussprojekt.webseite.Models.Buchung;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BuchungRepository extends JpaRepository<Buchung, Long> {
    List<Buchung> findByEmail(String email);
}
