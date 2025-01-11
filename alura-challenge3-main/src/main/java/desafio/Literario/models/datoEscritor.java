package desafio.Literario.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record datoEscritor(
        @JsonAlias("name") String escritorName,
        @JsonAlias("birth_year") Integer año_Naciente,
        @JsonAlias("death_year") Integer año_Muerto) {}
