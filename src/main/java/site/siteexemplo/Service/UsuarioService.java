package site.siteexemplo.Service;

import site.siteexemplo.Model.Usuario;
import site.siteexemplo.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    //Metodo salvar
    public boolean salvarUsu(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId().isBlank()) return false;
        if (usuario.getFotoPerfil() == null || usuario.getFotoPerfil().isBlank()) return false;
        if (usuario.getNome() == null || usuario.getNome().isBlank()) return false;
        if (usuario.getBio() == null || usuario.getBio().isBlank()) return false;

        usuarioRepository.save(usuario);
        return true;
    }

    //Metodo excluir Usuário
    public boolean excluirUsu(String id) {
        if (!usuarioRepository.existsById(id)) return false;

        usuarioRepository.deleteById(id);
        return true;
    }

    //Metodo buscar Usuário.
    public Usuario buscarUsu(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    //Metodo listar
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    //Metodo seguir usuario.
    public boolean seguir(String idSeguido, String idSeguindo) {
        if (idSeguido.equals(idSeguindo)) return false; //Usuario não pode seguir ele mesmo.

        Usuario seguido = usuarioRepository.findById(idSeguido) //Procura o seguido, caso não ache entrega null.
                .orElse(null);

        Usuario seguindo = usuarioRepository.findById(idSeguindo) //Procura o seguido, caso não ache entrega null.
                .orElse(null);

        if (seguido == null || seguindo == null) return false; //Seguido e Seguindo não podem ser null para realizar a ação.
        if (seguindo.getSeguindo().contains(seguido)) return false; //Não pode seguir a pessoa duas vezes.

        seguindo.getSeguindo().add(seguido);
        usuarioRepository.save(seguindo);

        return true;
    }
}