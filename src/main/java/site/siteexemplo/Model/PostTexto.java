package site.siteexemplo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@AllArgsConstructor
@Node
public class PostTexto extends Post {

    private String texto;

}
