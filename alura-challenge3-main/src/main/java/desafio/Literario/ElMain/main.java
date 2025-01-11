package desafio.Literario.ElMain;


// la importasisa
import desafio.Literario.models.*;
import desafio.Literario.repository.repoEscritor;
import desafio.Literario.repository.repoLibro;
import desafio.Literario.services.apiPeti;
import desafio.Literario.services.transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class main {
//la inicializacion en la secta oscura
    private Scanner scanner=new Scanner(System.in);
    private apiPeti api_request=new apiPeti();
    private transformer transformer =new transformer();
    private List<libro> buscaHistory =new ArrayList<>();

    @Autowired
    private repoLibro repolibriti;
    @Autowired
    private repoEscritor repoescritor;
//menu mostrar el menu cansado de tanto menu de texto
    public void muestaMenu()throws Exception {
        while (true){
            System.out.println("=".repeat(120));
            System.out.println("""
                Bienvenido a LiterAlura:");
            1. Buscar libro por título.");
            2. Historial de búsqueda.");
            3. Lista de autores.");
            4. Autores vivos en un año específico.");
            5. Libros por idiomas.");
            6. Top 10 de libros más descargados.");
            7. Buscar autor por nombre.");
            8. Buscar autor por año de nacimiento.");
            9. Buscar autor por año de fallecimiento.");
            [S]alir""");
            var preguntaMenu=scanner.nextLine();
            if(preguntaMenu.equalsIgnoreCase("q"))
                break;
            else if(preguntaMenu.equalsIgnoreCase("1"))
                EncuentraTitulo();
            else if(preguntaMenu.equalsIgnoreCase("2"))
                muestroHistorychanell();
            else if(preguntaMenu.equalsIgnoreCase("3"))
                muestroAutorList();
            else if(preguntaMenu.equalsIgnoreCase("4"))
                añoAutor();
            else if(preguntaMenu.equalsIgnoreCase("5"))
                libroLenguaje();
            else if(preguntaMenu.equalsIgnoreCase("6"))
                losPupiTop10();
            else if(preguntaMenu.equalsIgnoreCase("7"))
                autorNameUNKNOW();
            else if(preguntaMenu.equalsIgnoreCase("8"))
                elNacido();
            else if(preguntaMenu.equalsIgnoreCase("9"))
                elMuerto();
        }
    }
    private void EncuentraTitulo() throws Exception{

        System.out.print("Titulo: ");
        var filtro=scanner.nextLine();
        var geyson=api_request.request(
                "http://gutendex.com/books/?search="+filtro.replace(" ","%20"));
        var respuesta= transformer.obternerdato(geyson, respuestaPeti.class);

        try{
            if(respuesta.count()!=0){
                dataLibro data =respuesta.results().stream()
                        .filter(b->b.title().toLowerCase().contains(filtro.toLowerCase()))
                        .toList().get(0);
                libro libro =new libro(data);

                System.out.println(libro);
                System.out.println("=".repeat(120));
                buscaHistory.add(libro);
                List<Escritor> almacenEscritores =new ArrayList<>();

                for(Escritor escritor : libro.cogerAutores()){
                    var autor1= repoescritor.buscaName(escritor.cogeNameESCRITOR());
                    if(autor1!=null) almacenEscritores.add(autor1);
                }
                if(!almacenEscritores.isEmpty()){
                    Map<String,Boolean> stored_map= almacenEscritores.stream()
                            .collect(Collectors.toMap(Escritor::cogeNameESCRITOR, obj->true));
                    List<Escritor> reEscritorio = new ArrayList<>();
                    for(Escritor _escritor : libro.cogerAutores()){
                        if(!stored_map.get(_escritor.cogeNameESCRITOR())){
                            reEscritorio.add(_escritor);
                        }
                    }
                    libro.colocaEscritor(reEscritorio);
                    libro.cogerAutores().forEach(a-> repoescritor.save(a));
                    libro.montaEscritor(almacenEscritores);
                }
                else{
                    libro.cogerAutores().forEach(a-> repoescritor.save(a));
                }
                var libroAqui= repolibriti.libroSi(libro.cogeTitulo());
                if(!libroAqui)
                    repolibriti.save(libro);
                else System.out.println("libro esta en la base de Datos :D.");
                mensajeContinue();
            }
            else {
                System.out.println("=".repeat(120));
                System.out.println("libro no se pudo encontrar .");
                mensajeContinue();
            }
        }catch (Exception e){
            System.out.println("libro listo en la DataBase.");
            mensajeContinue();
        }
    }

    private void muestroHistorychanell() {
        System.out.println("=".repeat(120));
        System.out.println("buscando el History.");
        if(!buscaHistory.isEmpty())
            buscaHistory.forEach(System.out::println);
        else{
            System.out.println("nada encontrado.");
        }
        mensajeContinue();
    }

    private void muestroAutorList(){
        System.out.println("=".repeat(120));
        System.out.println("Escritores Lista");
        Map<String, Escritor> mapeoEscritores=new HashMap<>();
        //        search_history.forEach(b-> authors.addAll(b.cogerAutores()));
        // quizas la use mas luego con el codigo en una sola linea
        buscaHistory.forEach(b->b.cogerAutores().forEach(a->mapeoEscritores.put(a.cogeNameESCRITOR(),a)));

        if(!mapeoEscritores.isEmpty()){
            mapeoEscritores.forEach((key, escritor)-> System.out.println(escritor));
            System.out.println("=".repeat(120));
        }
        else{
            System.out.println("=".repeat(120));
            System.out.println("No encontre nada");
        }
        mensajeContinue();

    }

    private void muestraEscritorApi(){
        System.out.println("que año busca?");
        var buscaFiltro=scanner.nextLine();
        try {
            var filtroAno=Integer.valueOf(buscaFiltro);
            Map<String, Escritor> authors_map=new HashMap<>();
            buscaHistory.forEach(a->a.cogerAutores().stream()
                    .filter(a2-> a2.cogenacimiento()<=filtroAno && a2.get_death_year()>=filtroAno)
                    .forEach(a3->authors_map.put(a3.cogeNameESCRITOR(),a3)));
            if(!authors_map.isEmpty()){
                authors_map.forEach((key, escritor)-> System.out.println(escritor));
            }
        }catch (NumberFormatException e){
            System.out.println("año raro o invalido");
        }
    }

    private void libroLenguaje(){
        System.out.println("=".repeat(120));
        System.out.println("lenguaje del libro");
        List<idiomas> idiomas = repolibriti.buscaIdioma();
        for(int i = 0; i< idiomas.size(); i++){
            System.out.println((i+1)+". "+ idiomas.get(i));
        }
        if(idiomas.isEmpty()) System.out.println("No idiomas en la base de datos.");
        System.out.println("=".repeat(120));
        System.out.print("escogete un Idioma: ?");
        var preguntaIdioma=scanner.nextLine();
        try {
            var cuentaLibro= repolibriti.buscaIdiomaCuent(idiomas.get(Integer.valueOf(preguntaIdioma)-1));
            System.out.println("Aqui hay "+cuentaLibro+" libros en "+
                    idiomas.get(Integer.valueOf(preguntaIdioma)-1)+" Almacenados.");
        }catch (NumberFormatException | IndexOutOfBoundsException e){
            System.out.println("Seccion invalida.");
        }
        mensajeContinue();
    }

    private void muestraListEscritor(){
        System.out.println("Lista Escritores");
        List<Escritor> escritors = repoescritor.findAll();
        escritors.forEach(System.out::println);
    }

    private void añoAutor(){
        System.out.print("que año: ?");
        var buscaFiltro=scanner.nextLine();
        try{
            List<Escritor> escritors = repoescritor.buscaAño(Integer.valueOf(buscaFiltro));
            if(!escritors.isEmpty())
                escritors.forEach(System.out::println);
            else{
                System.out.println("=".repeat(120));
                System.out.println("No hay escritores vivos en este: "+buscaFiltro);
            }
        }catch (NumberFormatException e){
            System.out.println("se te fue el año");
            System.out.println("año invalido");
        }
        mensajeContinue();
    }

    private void losPupiTop10(){
        System.out.println("=".repeat(120));
        System.out.println("Top de los mejores libros.");
        var libros= repolibriti.encuentrapupis10();
        libros.forEach(System.out::println);
        mensajeContinue();
    }

    private void autorNameUNKNOW(){
        System.out.println("=".repeat(120));
        System.out.println("nombre del autor que busca?.");
        System.out.print("nombre: ");
        var buscaFiltro=scanner.nextLine();
        var escritor= repoescritor.buscanombreE(buscaFiltro);
        if(!escritor.isEmpty()){
            escritor.forEach(System.out::println);
        }
        else{
            System.out.println("=".repeat(120));
            System.out.println("No hubo concidencia para: "+buscaFiltro);
        }
        mensajeContinue();
    }

    private void elNacido(){
        System.out.println("=".repeat(120));
        System.out.println("Buscar autor por año de nacimiento");
        System.out.print("año: ");
        var bsucaFiltro=scanner.nextLine();
        try{
            var escritor= repoescritor.buscaano(Integer.valueOf(bsucaFiltro));
            if(!escritor.isEmpty())
                escritor.forEach(System.out::println);
            else{
                System.out.println("=".repeat(120));
                System.out.println("Ningún autor nació en " + bsucaFiltro);
            }
        }catch (NumberFormatException e){
            System.out.println("invalido.");
        }
        mensajeContinue();
    }

    private void elMuerto(){
        System.out.println("=".repeat(120));
        System.out.println("Buscar autor por año de fallecimiento");
        System.out.print("Año de fallecimiento: ");
        var buscaFiltro=scanner.nextLine();
        try{
            var escritor= repoescritor.buscamuerte(Integer.valueOf(buscaFiltro));
            if(!escritor.isEmpty())
                escritor.forEach(System.out::println);
            else {
                System.out.println("=".repeat(120));
                System.out.println("No existe muertos en: "+buscaFiltro);
            }
        }catch (NumberFormatException e){
            System.out.println("digitaste mal....");
        }
        mensajeContinue();
    }

    private void mensajeContinue(){
        System.out.print("presione enter para continuar.");
        scanner.nextLine();
    }

}
