package example;


import example.Pojo.log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;


@Component
public class EsTemplate {

    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired()
    @SuppressWarnings("all")
    public EsTemplate(ElasticsearchRestTemplate elasticsearchRestTemplate){
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    public void save(Object data) {
        elasticsearchRestTemplate.save(data, IndexCoordinates.of("log"));
    }


    public SearchHits<log> getData() {
        return elasticsearchRestTemplate.search(Query.findAll(), log.class);
    }


}
