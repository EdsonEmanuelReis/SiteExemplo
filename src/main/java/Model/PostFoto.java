package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@AllArgsConstructor
@Node
public class PostFoto extends Post {

    private String imagem;
}
