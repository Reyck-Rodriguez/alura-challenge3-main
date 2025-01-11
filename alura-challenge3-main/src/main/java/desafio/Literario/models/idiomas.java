package desafio.Literario.models;

public enum idiomas {
    ENGLISH("EN","ENGLISH"),
    SPANISH("ES","ESPAÑOL"),
    FRENCH("FR","FRENCH");

    private String azz;
    private String lengua;

    idiomas(String azz, String lengua){
        this.azz = azz;
        this.lengua = lengua;
    }
    public static idiomas fromString(String text){
        for(idiomas l: idiomas.values()){
            if(l.azz.equalsIgnoreCase(text) || l.lengua.equalsIgnoreCase(text))
                return l;
        }
        throw new IllegalArgumentException("idiomas no hablados o encontrados : "+text);
    }
}
