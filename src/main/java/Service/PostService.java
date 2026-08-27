package Service;

import Model.Post;
import Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public boolean salvarPost(Post post) {
        if (post.getId() == null || post.getId().isBlank()) return false;
        if (postRepository.existsById(post.getId())) return false;
        if (post.getCurtidas() < 0) return false;
        if (post.getAutor() == null) return false;


        postRepository.save(post);
        return true;
    }

    public boolean deletePost(String id) {
        if (!postRepository.existsById(id)) return false;

        postRepository.deleteById(id);
        return true;
    }

    public Post buscarPost(String id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> listarPosts() {
        return postRepository.findAll();
    }
}