package site.siteexemplo.Controller;

import site.siteexemplo.Model.Usuario;
import site.siteexemplo.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")

public class UsuarioController {
private final UsuarioService usuarioService;

@PostMapping("/criarConta")
public ResponseEntity<String> cadastrarUsuario (@RequestBody Usuario usuario){
    boolean resultado = usuarioService.salvarUsu(usuario);
    if (resultado) return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro feito");
    return ResponseEntity.badRequest().body("Erro ao encontrar usuario");
}

@DeleteMapping("/deletarConta")
public ResponseEntity<Void> deletarUsuario (@RequestParam String id){
    boolean resultado = usuarioService.excluirUsu(id);
    if (resultado) return ResponseEntity.noContent().build();
    return ResponseEntity.notFound().build();
}

@GetMapping("/buscarConta")
public ResponseEntity<Usuario> buscarConta (@RequestParam String id){
    Usuario usuario = usuarioService.buscarUsu(id);
    if (usuario == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(usuario);
}

@GetMapping("/listarUsuarios")
public ResponseEntity <List<Usuario>> listarUsuarios () {
    return ResponseEntity.ok(usuarioService.listarUsuarios());
}

@PostMapping("/seguir")
public ResponseEntity<String> seguir(@RequestParam String idSeguido, @RequestParam String idSeguindo) {
    try {
          boolean resultado = usuarioService.seguir(idSeguido, idSeguindo);
           if (resultado) {
            return ResponseEntity.ok("Usuário seguido com sucesso");
            }

            return ResponseEntity.badRequest()
                    .body("Não é possível seguir a si mesmo ou seguir o mesmo usuário duas vezes");

        } catch (java.util.NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
