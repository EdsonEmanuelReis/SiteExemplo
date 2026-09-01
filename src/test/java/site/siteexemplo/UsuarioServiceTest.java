package site.siteexemplo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.siteexemplo.Model.Usuario;
import site.siteexemplo.Repository.UsuarioRepository;
import site.siteexemplo.Service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    UsuarioService usuarioService;

    @Test
    void naoDevePermitirUsuarioSeguirASiMesmo() { //Teste para não permitir Usuario seguir si mesmo.
        boolean resultado = usuarioService.seguir("1", "1");
        assertFalse(resultado);
    }

    @Test
    void naoDevePermitirSeguirUsuarioQueNaoExiste() { //Teste para não permitir seguir um Usuario que não existe.
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(new Usuario("1","Teste","Ana","Bioteste",new ArrayList<>(),new ArrayList<>())));
        when(usuarioRepository.findById("2")).thenReturn(Optional.empty());

        boolean resultado = usuarioService.seguir("2", "1");

        assertFalse(resultado);
    }

    @Test
    void naodevePermitirSeguirDuasvezes (){ //Teste para não permitir seguir duas vezes.
        Usuario ana = new Usuario("1","Teste","Ana","Bioteste",new ArrayList<>(),new ArrayList<>());
        Usuario bruno = new Usuario("2","Teste","Bruno","Bioteste",new ArrayList<>(), List.of(ana));
        when(usuarioRepository.findById("2")).thenReturn(Optional.of(bruno));
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(ana));

        boolean resultado = usuarioService.seguir("1","2");
        assertFalse(resultado);
    }

    @Test
    void devePermitirQueUsuarioSigaOutroUsuario() { //Teste para Usuario seguir outro Usuario que existe.

        Usuario ana = new Usuario("1","Teste","Ana","Bioteste",new ArrayList<>(),new ArrayList<>());
        Usuario bruno = new Usuario("2","Teste","Bruno","Bioteste",new ArrayList<>(),new ArrayList<>());
        when(usuarioRepository.findById("2")).thenReturn(Optional.of(bruno));
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(ana));
        boolean resultado = usuarioService.seguir("2", "1");
        assertTrue(resultado);
        assertTrue(ana.getSeguindo().contains(bruno));
        verify(usuarioRepository).save(ana);
    }
}
