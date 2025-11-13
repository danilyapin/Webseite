package abschlussprojekt.webseite.Repository;

import abschlussprojekt.webseite.Models.Artikel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ArtikelRepository extends JpaRepository<Artikel, Long> {
    Optional<Artikel> findById(Long id);
    Optional<Artikel> findByArtikelnummer(String artikelnummer);
}
