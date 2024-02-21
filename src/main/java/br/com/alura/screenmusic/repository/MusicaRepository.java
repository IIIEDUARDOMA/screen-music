package br.com.alura.screenmusic.repository;

import br.com.alura.screenmusic.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicaRepository extends JpaRepository<Musica,Long> {
}
