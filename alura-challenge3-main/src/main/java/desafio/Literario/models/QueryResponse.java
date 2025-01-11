package desafio.Literario.models;

import java.util.List;

public class QueryResponse {
    private Integer count;
    private String next;
    private String previous;
    private List<libro> results;

    public QueryResponse(){}

    public QueryResponse(respuestaPeti data){
        this.count=data.count();
        this.next= data.next();
        this.previous= data.previous();
//        this.results=data.results();
    }

}
