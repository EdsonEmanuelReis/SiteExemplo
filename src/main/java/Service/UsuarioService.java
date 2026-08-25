package Service;

import Model.Usuario;
import Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    UsuarioRepository usuarioRepository;

    public boolean salvarUsu (Usuario usuario){
    if (usuarioRepository.existsById(usuario.getId())) return false;
    if (usuario.getFotoPerfil().isBlank()) return false;
    if (usuario.getNome().isBlank()) return false;
    if (usuario.getBio().isBlank()) return false;

    usuarioRepository.save(usuario);
    return true;
    }

    public boolean excluirUsu (Usuario usuario){
    if (!usuarioRepository.existsById(usuario.getId())) return false;
    usuarioRepository.delete(usuario);
    return true;
    }

    public Usuario buscarUsu (String id){
    return  (usuarioRepository.findById(id)).orElse(null);
    }

    public List<Usuario> listarUsuarios (){
        return usuarioRepository.findAll();
    }

}
