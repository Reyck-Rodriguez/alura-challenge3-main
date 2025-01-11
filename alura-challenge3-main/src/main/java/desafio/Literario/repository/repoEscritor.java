package desafio.Literario.repository;

import desafio.Literario.models.Escritor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface repoEscritor extends JpaRepository<Escritor,Long> {
    @Query("select a from Escritor a where a.nombreEscritor=:nombreEscritor")
    Escritor buscaName(String nombreEscritor);
    @Query("select a from Escritor a where  a.birth_year<=:ano and a.death_year>=:ano")
    List<Escritor> buscaAño(Integer ano);
    @Query("select a from Escritor a where a.escritorNombre ilike %:escritorNombre%")
    List<Escritor> buscanombreE(String escritorNombre);
    @Query("select a from Escritor a where a.birth_year=:ano")
    List<Escritor> buscaano(Integer ano);
    @Query("select a from Escritor a where a.death_year=:ano")
    List<Escritor> buscamuerte(Integer ano);
}
