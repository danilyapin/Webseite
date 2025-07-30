package abschlussprojekt.webseite.Service;

import abschlussprojekt.webseite.Models.Artikel;
import abschlussprojekt.webseite.Repository.ArtikelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArtikelService {

    @Autowired
    private ArtikelRepository artikelRepository;

    @Transactional
    public Artikel addArtikel(Artikel artikel){
        return artikelRepository.save(artikel);
    }

    public List<Artikel> getAllArtikel(){
        return artikelRepository.findAll();
    }

    @Transactional
    public void deleteArtikel(Long id) {
        artikelRepository.deleteById(id);
    }

    public Optional<Artikel> getArtikelById(Long id) {
        return artikelRepository.findById(id);
    }

    public Optional<Artikel> getArtikelByArtikelnummer(String artikelnummer) {
        return artikelRepository.findByArtikelnummer(artikelnummer);
    }
}
