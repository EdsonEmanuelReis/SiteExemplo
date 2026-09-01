package site.siteexemplo.Service;

import site.siteexemplo.Model.Post;
import site.siteexemplo.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //Metodo Salvar.
    public boolean salvarPost(Post post) {
        if (post.getId() == null || post.getId().isBlank()) return false; //O ID de post não pode ser nulo.
        if (postRepository.existsById(post.getId())) return false; //Verifica se o post já existe.
        if (post.getCurtidas() < 0) return false; //As curtidas não devem ser menor que zero (negativas).
        if (post.getAutor() == null) return false; // Autor do Post não 
        postRepository.save(post);
        return true;
    }

    //Metodo de deletar post.
    public boolean deletePost(String id) {
        if (!postRepository.existsById(id)) return false;
        postRepository.deleteById(id);
        return true;
    }

    //Metodo buscar post.
    public Post buscarPost(String id) {
        return postRepository.findById(id).orElse(null);
    }

    //Metodo listar posts.
    public List<Post> listarPosts() {
        return postRepository.findAll();
    }
}