package desafio.Literario.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
public class libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Escritor> escritors;
    @ElementCollection(targetClass = desafio.Literario.models.idiomas.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<desafio.Literario.models.idiomas> idiomas;
    private Integer downloads;


    public libro(){}

    public libro(dataLibro book_data){
        this.titulo =book_data.title();
        this.escritors =new ArrayList<>();
        for(datoEscritor author_data: book_data.authors()){
            this.escritors.add(new Escritor(author_data));
        }
        for(Escritor escritor :this.escritors){
            escritor.set_books(this);
        }
        this.idiomas =new ArrayList<>();
        for(String language:book_data.languages()){
            this.idiomas.add(desafio.Literario.models.idiomas.fromString(language));
        }
        this.downloads=book_data.downloads();
    }

    @Override
    public String toString() {
        StringBuilder output_string= new StringBuilder("=".repeat(120)+"\n"+
                "title:     " + titulo + "\n" +
                "escritors:   ");
        for(int i = 0; i< escritors.size(); i++){
            output_string.append(escritors.get(i).get_formated_author_name()).append("\n");
            if(i!= escritors.size()-1)output_string.append("           ");
        }
        output_string.append("idiomas: ");
        for(int i = 0; i< idiomas.size(); i++){
            output_string.append(idiomas.get(i)).append("\n");
            if(i!= idiomas.size()-1)output_string.append("           ");
        }
        output_string.append("downloads: ").append(downloads);
        return output_string.toString();
    }

    public Long get_id() {
        return id;
    }

    public void set_id(Long id) {
        this.id = id;
    }

    public String cogeTitulo() {
        return titulo;
    }

    public void set_title(String title) {
        this.titulo = title;
    }

    public List<Escritor> cogerAutores() {
        return escritors;
    }

    public void colocaEscritor(List<Escritor> escritors) {
        this.escritors = escritors;
    }

    public void actualizaEscritor(List<Escritor> escritors){
        List<Escritor> final_escritors = new ArrayList<>(escritors);

        Map<String,Boolean> Escritor1= final_escritors.stream()
                .collect(Collectors.toMap(Escritor::cogeNameESCRITOR, obj->true));

        for(Escritor escritor :this.escritors){
            if(!Escritor1.get(escritor.cogeNameESCRITOR()))
                final_escritors.add(escritor);
        }

        this.escritors = final_escritors;
    }

    public List<desafio.Literario.models.idiomas> get_languages() {
        return idiomas;
    }

    public void set_languages(List<desafio.Literario.models.idiomas> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer get_downloads() {
        return downloads;
    }

    public void set_downloads(Integer downloads) {
        this.downloads = downloads;
    }

    public void montaEscritor(List<Escritor> escritors) {
        this.escritors.addAll(escritors);
    }
}
