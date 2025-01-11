package desafio.Literario.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record dataLibro(
        @JsonAlias("id") Integer book_id,
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<datoEscritor> authors,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") Integer downloads){}
