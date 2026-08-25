package Repository;

import Model.Usuario;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface UsuarioRepository extends Neo4jRepository<Usuario, String> {

    @Query("MATCH (b {id: $idA})-[:SEGUE]->(c), (r {id: $idB})-[:SEGUE]->(c) RETURN c")
    List<Usuario> encontrarAmigosEmComum(@Param("idA") String idA, @Param("idB") String idB);

}
