package example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
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



}
