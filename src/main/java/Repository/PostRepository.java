package Repository;

import Model.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PostRepository extends Neo4jRepository<Post, String> {
}
