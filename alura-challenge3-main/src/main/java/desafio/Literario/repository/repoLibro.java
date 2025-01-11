package desafio.Literario.repository;

import desafio.Literario.models.idiomas;
import desafio.Literario.models.libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface repoLibro extends JpaRepository<libro,Long> {
    @Query("select distinct l from libro b join b.languages l")
    List<idiomas> buscaIdioma();
    @Query("select count(l) from libro b join b.languages l where l=:idiomas")
    Object buscaIdiomaCuent(idiomas idiomas);
    @Query("select b from libro b order by b.downloads desc limit 10")
    List<libro> encuentrapupis10();
    @Query("select count(b) > 0 from libro b where b.titulo=:titulo")
    Boolean libroSi(String titulo);
}
