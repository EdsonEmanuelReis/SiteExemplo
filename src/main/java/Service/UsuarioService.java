package Service;

import Model.Usuario;
import Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public boolean salvarUsu(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId().isBlank()) return false;
        if (usuario.getFotoPerfil() == null || usuario.getFotoPerfil().isBlank()) return false;
        if (usuario.getNome() == null || usuario.getNome().isBlank()) return false;
        if (usuario.getBio() == null || usuario.getBio().isBlank()) return false;

        usuarioRepository.save(usuario);
        return true;
    }

    public boolean excluirUsu(String id) {
        if (!usuarioRepository.existsById(id)) return false;

        usuarioRepository.deleteById(id);
        return true;
    }

    public Usuario buscarUsu(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public boolean seguir(String idSeguido, String idSeguindo) {
        if (idSeguido.equals(idSeguindo)) return false;

        Usuario seguido = usuarioRepository.findById(idSeguido)
                .orElseThrow();

        Usuario seguindo = usuarioRepository.findById(idSeguindo)
                .orElseThrow();

        if (seguindo.getSeguindo().contains(seguido)) return false;

        seguindo.getSeguindo().add(seguido);
        usuarioRepository.save(seguindo);

        return true;
    }
}