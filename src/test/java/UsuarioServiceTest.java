import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.PapelUsuario;
import model.Usuario;
import service.JsonDataManager;
import service.UsuarioService;


public class UsuarioServiceTest {

    private JsonDataManager dataManager;
    private UsuarioService usuarioService;

    // Configuração executada antes de cada teste
    @BeforeEach
    void setUp() {
        // Garante que o estado inicial (dados simulados) seja redefinido para cada teste
        this.dataManager = new JsonDataManager();
        this.usuarioService = new UsuarioService(dataManager);
    }

    @Test
    void visualizarTodosDeveRetornarListaDeUsuariosDoDataManager() {
        List<Usuario> usuariosMock = List.of(
                new Usuario("u1", "Test User 1", "test1@example.com", PapelUsuario.ESTUDANTE),
                new Usuario("u2", "Test User 2", "test2@example.com", PapelUsuario.PROFESSOR)
        );

        JsonDataManager mockDataManager = new JsonDataManager() {
            @Override
            public List<Usuario> getUsuarios() {
                return usuariosMock;
            }
        };

        UsuarioService usuarioService = new UsuarioService(mockDataManager);
        List<Usuario> resultado = usuarioService.visualizarTodos();

        assertEquals(2, resultado.size());
        assertSame(usuariosMock, resultado);
    }

    @Test
    void alterarNivelAcessoDeveMudarOModeloDeUsuario() {
        Usuario usuarioParaAlterar = new Usuario("u3", "Carla Estudante", "carla@codefolio.com", PapelUsuario.ESTUDANTE);
        List<Usuario> usuariosMock = List.of(usuarioParaAlterar);

        JsonDataManager mockDataManager = new JsonDataManager() {
            @Override
            public List<Usuario> getUsuarios() {
                return usuariosMock;
            }
        };

        UsuarioService usuarioService = new UsuarioService(mockDataManager);
        boolean resultado = usuarioService.alterarNivelAcesso("u3", PapelUsuario.PROFESSOR);

        assertTrue(resultado);
        assertEquals(PapelUsuario.PROFESSOR, usuarioParaAlterar.getPapel());
    }

    @Test
    void alterarNivelAcessoInexistenteDeveFalhar() {
        JsonDataManager mockDataManager = new JsonDataManager() {
            @Override
            public List<Usuario> getUsuarios() {
                return List.of();
            }
        };

        UsuarioService usuarioService = new UsuarioService(mockDataManager);
        boolean resultado = usuarioService.alterarNivelAcesso("u999", PapelUsuario.ADMINISTRADOR);

        assertFalse(resultado);
    }

    // Bernardo Dorneles
    @Test
    void buscarUsuariosDeveFiltrarPorNome() {
    List<Usuario> usuariosMock = List.of(
        new Usuario("u1", "Ana Admin", "ana@codefolio.com", PapelUsuario.ADMINISTRADOR),
        new Usuario("u2", "Bruno Professor", "bruno@codefolio.com", PapelUsuario.PROFESSOR),
        new Usuario("u3", "Carlos Estudante", "carlos@email.com", PapelUsuario.ESTUDANTE),
        new Usuario("u4", "Diana Codefolio", "diana@codefolio.com", PapelUsuario.USUARIO_COMUM)
    );

    JsonDataManager mockDataManager = new JsonDataManager() {
        @Override
        public List<Usuario> getUsuarios() {
            return usuariosMock;
        }
    };

    UsuarioService usuarioService = new UsuarioService(mockDataManager);
    List<Usuario> resultado = usuarioService.buscarUsuarios("ana", null);

    assertEquals(2, resultado.size());
    assertTrue(resultado.stream().anyMatch(usuario -> usuario.getNome().equals("Ana Admin")));
    assertTrue(resultado.stream().anyMatch(usuario -> usuario.getNome().equals("Diana Codefolio")));
}


    // Bernardo Dorneles
    @Test
    void buscarUsuariosDeveFiltrarPorEmail() {
        List<Usuario> usuariosMock = List.of(
        new Usuario("u1", "Ana Admin", "ana@codefolio.com", PapelUsuario.ADMINISTRADOR),
        new Usuario("u2", "Bruno Professor", "bruno@codefolio.com", PapelUsuario.PROFESSOR),
        new Usuario("u3", "Carlos Estudante", "carlos@email.com", PapelUsuario.ESTUDANTE),
        new Usuario("u4", "Diana Codefolio", "diana@codefolio.com", PapelUsuario.USUARIO_COMUM)
    );

    JsonDataManager mockDataManager = new JsonDataManager() {
        @Override
        public List<Usuario> getUsuarios() {
            return usuariosMock;
        }
    };

    UsuarioService usuarioService = new UsuarioService(mockDataManager);
    List<Usuario> resultado = usuarioService.buscarUsuarios("@codefolio.com", null);

    assertEquals(3, resultado.size());
    assertTrue(resultado.stream().allMatch(usuario -> usuario.getEmail().contains("@codefolio.com")));
    }
    // Bernardo Dorneles
    @Test
    void buscarUsuariosDeveFiltrarPorPapel() {
        List<Usuario> usuariosMock = List.of(
        new Usuario("u1", "Ana Admin", "ana@codefolio.com", PapelUsuario.ADMINISTRADOR),
        new Usuario("u2", "Bruno Professor", "bruno@codefolio.com", PapelUsuario.PROFESSOR),
        new Usuario("u3", "Carlos Estudante", "carlos@email.com", PapelUsuario.ESTUDANTE),
        new Usuario("u4", "Diana Codefolio", "diana@codefolio.com", PapelUsuario.USUARIO_COMUM)
    );

    JsonDataManager mockDataManager = new JsonDataManager() {
        @Override
        public List<Usuario> getUsuarios() {
            return usuariosMock;
        }
    };

    UsuarioService usuarioService = new UsuarioService(mockDataManager);
    List<Usuario> resultado = usuarioService.buscarUsuarios(null, PapelUsuario.PROFESSOR);

    assertEquals(1, resultado.size());
    assertEquals(PapelUsuario.PROFESSOR, resultado.get(0).getPapel());
    assertEquals("Bruno Professor", resultado.get(0).getNome());
    }

    // REQUISITO: Ordenar usuários por diferentes critérios (Admin)
    @Test
    void ordenarUsuariosPorNomeDeveFuncionar() {
        List<Usuario> usuariosIniciais = new ArrayList<>(List.of(
        new Usuario("u3", "Carlos Estudante", "carlos@email.com", PapelUsuario.ESTUDANTE),
        new Usuario("u1", "Ana Admin", "ana@codefolio.com", PapelUsuario.ADMINISTRADOR),
        new Usuario("u4", "Diana Codefolio", "diana@codefolio.com", PapelUsuario.USUARIO_COMUM),
        new Usuario("u2", "Bruno Professor", "bruno@codefolio.com", PapelUsuario.PROFESSOR)
    ));

    JsonDataManager mockDataManager = new JsonDataManager() {
        @Override
        public List<Usuario> getUsuarios() {
            return usuariosIniciais;
        }
    };

    UsuarioService usuarioService = new UsuarioService(mockDataManager);
    
    usuarioService.ordenarUsuarios("nome");
    List<Usuario> usuariosOrdenados = usuarioService.visualizarTodos();
    
    assertEquals(4, usuariosOrdenados.size());
    assertEquals("Carlos Estudante", usuariosOrdenados.get(0).getNome());
    assertEquals("Ana Admin", usuariosOrdenados.get(1).getNome());
    assertEquals("Diana Codefolio", usuariosOrdenados.get(2).getNome());
    assertEquals("Bruno Professor", usuariosOrdenados.get(3).getNome());
}

    @Test
    void ordenarUsuariosPorPapelDeveFuncionar() {

        List<Usuario> usuariosIniciais = new ArrayList<>(List.of(
                new Usuario("u1", "Test User 1", "test1@example.com", PapelUsuario.ESTUDANTE),
                new Usuario("u2", "Test User 2", "test2@example.com", PapelUsuario.PROFESSOR),
                new Usuario("u3", "Test User 3", "test3@example.com", PapelUsuario.ADMINISTRADOR),
                new Usuario("u4", "Test User 4", "test4@example.com", PapelUsuario.USUARIO_COMUM)
        ));

        JsonDataManager mockDataManager = new JsonDataManager() {
            @Override
            public List<Usuario> getUsuarios() {
                return usuariosIniciais;
            }
        };

        UsuarioService usuarioService = new UsuarioService(mockDataManager);
        

        usuarioService.ordenarUsuarios("papel");
        List<Usuario> usuariosOrdenados = usuarioService.visualizarTodos();
        assertEquals(4, usuariosOrdenados.size());

        assertEquals(PapelUsuario.ESTUDANTE, usuariosOrdenados.get(0).getPapel());
        assertEquals(PapelUsuario.PROFESSOR, usuariosOrdenados.get(1).getPapel());
        assertEquals(PapelUsuario.ADMINISTRADOR, usuariosOrdenados.get(2).getPapel());
        assertEquals(PapelUsuario.USUARIO_COMUM, usuariosOrdenados.get(3).getPapel());
        // TODO: Testar a ordenação de usuários por papel:
        // 1. Chamar ordenarUsuarios("papel").
        // 2. Verificar se os primeiros elementos da lista estão na ordem de papel esperada (ex: USUARIO_COMUM, ESTUDANTE).
    }

    // REQUISITO: Editar informações pessoais (Usuário Comum)
    @Test
    void editarPerfilDeveMudarONomeDoUsuario() {

        usuarioService.editarPerfil("u4", "Carlos Atualizado");
        Optional<Usuario> usuarioAtualizado = dataManager.getUsuarios().stream()
                .filter(u -> u.getId().equals("u4"))
                .findFirst();   
        assertTrue(usuarioAtualizado.isPresent());

        Usuario usuario = usuarioAtualizado.get();
        assertEquals("Carlos Atualizado", usuario.getNome());
        
        // TODO: Testar a edição de informações de perfil (ex: mudar o nome do "u4"):
        // 1. Chamar editarPerfil() e verificar se retorna 'true'.
        // 2. Recuperar o usuário na persistência (dataManager).
        // 3. Verificar se o nome do usuário foi atualizado corretamente.
    }
}