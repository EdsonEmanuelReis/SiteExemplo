package Model;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;

@Getter
public class Post {

    @Id
    String ID;
    @Relationship(type = "CRIOU", direction = Relationship.Direction.INCOMING)
    private Usuario autor;
    private int curtidas;
    private LocalDate data;


}
