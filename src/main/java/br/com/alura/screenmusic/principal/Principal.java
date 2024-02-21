package br.com.alura.screenmusic.principal;

import br.com.alura.screenmusic.model.Artista;
import br.com.alura.screenmusic.model.Musica;
import br.com.alura.screenmusic.model.TipoArtista;
import br.com.alura.screenmusic.repository.ArtistaRepository;
import br.com.alura.screenmatch.service.ConsultaChatGPT;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private int opcao = -1;
    private ArtistaRepository reositorio;

    public Principal(ArtistaRepository repositorio) {
        this.reositorio = repositorio;
    }

    public void exibeMenu(){
        var menu = """
                **********| OPÇÕES |**********
                1 - Cadastrar artista
                2 - Cadastrar música
                3 - Listar músicas
                4 - Buscar músicas por artistas
                5 - Pesquisar dados sobre artista
                
                0 - sair
                """;
        while (opcao != 0){
            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusica();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    musicasPorArtistas();
                    break;
                case 5:
                    dadosSobreArtista();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarArtista() {
        var cadastrarNovo = "S";
        while (cadastrarNovo.equalsIgnoreCase("S")){
            scanner.nextLine();
            System.out.println("Digite o nome do artista que deseja cadastrar:");
            var nome = scanner.nextLine();
            System.out.println("Digite o tipo do artista (Solo, Dupla ou Banda):");
            var tipo = scanner.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);
            reositorio.save(artista);
            System.out.println("Deseja cadastrar outro artista? S ou N");
            cadastrarNovo = scanner.nextLine();
        }

    }

    private void cadastrarMusica() {
            System.out.println("Digite o nome do artista que deseja cadastrar a música:");
            var nome = scanner.nextLine();
            Optional<Artista> artista = reositorio.findByNomeContainingIgnoreCase(nome);
            if (artista.isPresent()){
                System.out.println("Digite o nome da música:");
                var nomeMusica = scanner.nextLine();
                Musica musica = new Musica(nomeMusica);
                musica.setArtista(artista.get());
                artista.get().getMusica().add(musica);
                reositorio.save(artista.get());
            }else {
                System.out.println("Artista não encontrado!");
            }
    }

    private void listarMusicas() {
        List<Artista> artistas = reositorio.findAll();
        artistas.forEach(System.out::println);
    }

    private void musicasPorArtistas() {
        System.out.println("De qual artista você quer listar as músicas?");
        var nome = scanner.nextLine();
        Optional<Artista> artistas = reositorio.findByNomeContainingIgnoreCase(nome);
        if (artistas.isPresent()){
            System.out.println(artistas.get());
        }else {
            System.out.println("Artista não encontrado!");
        }
    }

    private void dadosSobreArtista() {
        System.out.println("Qual artista você deseja consultar informações?");
        var nome = scanner.nextLine();
        var dados = ConsultaChatGPT.obterDados(nome);
        System.out.println(dados);
    }

}
