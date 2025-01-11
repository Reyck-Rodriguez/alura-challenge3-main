package desafio.Literario.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
public class Escritor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String NombreEscritor;
    private Integer cumpleaños;
    private Integer muerte;
    @ManyToMany(mappedBy = "authors")
    private List<libro> libros;

    public Escritor(){}

    public Escritor(datoEscritor authors) {
        this.NombreEscritor = authors.escritorName();
        this.cumpleaños =authors.birth_year();
        this.muerte =authors.death_year();
        libros =new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder output_string= new StringBuilder("=".repeat(120)+"\n"+
                "name      : "+this.get_formated_author_name()+"\n"+
                "birth year: "+ cumpleaños +"\n"+
                "death year: "+ muerte);
        return output_string.toString();
    }

    public String get_formated_author_name(){
        var tmp= NombreEscritor.split(", ");
        if(tmp.length>1)return tmp[1]+" "+tmp[0];
        else return tmp[0];
    }

    public Long cogeID() {
        return id;
    }

    public void ponID(Long id) {
        this.id = id;
    }

    public String cogeNameESCRITOR() {
        return NombreEscritor;
    }

    public void ponNombre(String nameESCRITOR) {
        this.NombreEscritor = nameESCRITOR;
    }

    public Integer cogenacimiento() {
        return cumpleaños;
    }

    public void colocaNacimiento(Integer birth_year) {
        this.cumpleaños = birth_year;
    }

    public Integer get_death_year() {
        return muerte;
    }

    public void set_death_year(Integer death_year) {
        this.muerte = death_year;
    }

    public List<libro> get_books() {
        return libros;
    }

    public void set_books(libro libro) {
        this.libros.add(libro);
    }
}
