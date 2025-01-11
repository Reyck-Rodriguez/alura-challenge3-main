package desafio.Literario.services;

public interface IDataConverter {

    <T> T obternerdato(String json, Class<T> _class);
}
