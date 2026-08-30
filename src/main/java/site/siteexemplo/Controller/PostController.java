package site.siteexemplo.Controller;

import org.springframework.web.bind.annotation.RestController;
import site.siteexemplo.Model.Post;
import site.siteexemplo.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/criar")
    public ResponseEntity<String> criarPost(@RequestBody Post post) {
        boolean resultado = postService.salvarPost(post);

        if (resultado) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Post criado com sucesso");
        }

        return ResponseEntity.badRequest().body("Erro ao criar post");
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> deletarPost(@RequestParam String id) {
        boolean resultado = postService.deletePost(id);

        if (resultado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<Post> buscarPost(@RequestParam String id) {
        Post post = postService.buscarPost(id);

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(post);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Post>> listarPosts() {
        return ResponseEntity.ok(postService.listarPosts());
    }
}