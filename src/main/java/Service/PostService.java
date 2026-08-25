package Service;

import Model.Post;
import Repository.PostRepository;

import java.util.List;

public class PostService {

    PostRepository postRepository;

    public boolean salvarPost (Post post){
        if (postRepository.existsById(post.getId())) return false;
        if (post.getCurtidas()<0) return false;
        if (post.getAutor().getNome().isBlank()) return false;

        postRepository.save(post);
        return true;
    }

    public boolean deletePost (Post post){
        if (!postRepository.existsById(post.getId())) return false;
        postRepository.delete(post);
        return true;
    }

    public Post buscarPost (String id){
     return postRepository.findById(id).orElse(null);
    }

    public List<Post> listarPosts (){
       return postRepository.findAll();
    }
}
