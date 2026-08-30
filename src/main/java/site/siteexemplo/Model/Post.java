package site.siteexemplo.Model;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;

@Node
@Getter
public class Post {

    @Id
    String id;

    @Relationship(type = "CRIOU", direction = Relationship.Direction.INCOMING)
    private Usuario autor;
    private int curtidas;
    private LocalDate data;


}
