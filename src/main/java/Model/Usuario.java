package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@Getter
@Setter
@AllArgsConstructor
public class Usuario {

    @Id
    private String id;
    private String fotoPerfil;
    private String nome;
    private String bio;
    @Relationship(type = "CRIOU", direction = Relationship.Direction.OUTGOING)
    private List <Post> post;
    @Relationship(type = "SEGUE", direction = Relationship.Direction.OUTGOING)
    private List<Usuario> seguindo = new ArrayList<>();
}
