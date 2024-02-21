package br.com.alura.screenmusic.model;

import jakarta.persistence.*;


@Entity
@Table(name = "musicas")
public class Musica {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String musica;
    @ManyToOne
    private Artista artista;

    public Musica(){}

    public Musica(String musica) {
        this.musica = musica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMusica() {
        return musica;
    }

    public void setMusica(String musica) {
        this.musica = musica;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    @Override
    public String toString() {
        return "Música = " + musica +
                ", Artista = " + artista.getNome();
    }
}
