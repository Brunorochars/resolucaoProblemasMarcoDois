import model.PapelUsuario;
import model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.JsonDataManager;
import service.UsuarioService;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

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

    // REQUISITO: Visualizar todos os usuários cadastrados
    @Test
    void visualizarTodosDeveRetornarQuatroUsuariosIniciais() {
        // TODO: Testar a visualização de todos os usuários:
        // 1. Chamar visualizarTodos().
        // 2. Verificar se a lista retornada tem o tamanho esperado (4 nos dados iniciais).
    }

    // REQUISITO: Alterar níveis de acesso (Admin)
    @Test
    void alterarNivelAcessoDeveMudarOModeloDeUsuario() {
        // TODO: Testar a alteração do nível de acesso de um usuário existente (ex: "u3" para PROFESSOR):
        // 1. Chamar alterarNivelAcesso() e verificar se retorna 'true'.
        // 2. Recuperar o usuário na persistência (dataManager).
        // 3. Verificar se o PapelUsuario foi alterado corretamente para o novo papel.
    }

    @Test
    void alterarNivelAcessoInexistenteDeveFalhar() {
        // TODO: Testar a alteração do nível de acesso para um usuário inexistente:
        // 1. Chamar alterarNivelAcesso() com um ID inexistente (ex: "u999").
        // 2. Verificar se o retorno é 'false'.
    }

    // REQUISITO: Filtrar e buscar usuários por nome, email ou papel (Admin)
    @Test
    void buscarUsuariosDeveFiltrarPorNome() {
        // TODO: Testar a busca de usuários por parte do nome (ex: "ana"):
        // 1. Chamar buscarUsuarios("ana", null).
        // 2. Verificar se a lista retornada tem o tamanho correto (1) e o nome do usuário está correto.
    }

    @Test
    void buscarUsuariosDeveFiltrarPorEmail() {
        // TODO: Testar a busca de usuários por parte do email (ex: "@codefolio.com"):
        // 1. Chamar buscarUsuarios("@codefolio.com", null).
        // 2. Verificar se a lista retornada tem o tamanho correto (4).
    }

    @Test
    void buscarUsuariosDeveFiltrarPorPapel() {
        // TODO: Testar a busca de usuários por PapelUsuario (ex: PROFESSOR):
        // 1. Chamar buscarUsuarios(null, PapelUsuario.PROFESSOR).
        // 2. Verificar se a lista retornada tem o tamanho correto (1) e o papel do usuário está correto.
    }

    // REQUISITO: Ordenar usuários por diferentes critérios (Admin)
    @Test
    void ordenarUsuariosPorNomeDeveFuncionar() {
        // TODO: Testar a ordenação de usuários por nome:
        // 1. Chamar ordenarUsuarios("nome").
        // 2. Verificar se os primeiros elementos da lista estão na ordem alfabética esperada (ex: "Ana Admin", "Bruno Professor").
    }

    @Test
    void ordenarUsuariosPorPapelDeveFuncionar() {
        // TODO: Testar a ordenação de usuários por papel:
        // 1. Chamar ordenarUsuarios("papel").
        // 2. Verificar se os primeiros elementos da lista estão na ordem de papel esperada (ex: USUARIO_COMUM, ESTUDANTE).
    }

    // REQUISITO: Editar informações pessoais (Usuário Comum)
    @Test
    void editarPerfilDeveMudarONomeDoUsuario() {
        // TODO: Testar a edição de informações de perfil (ex: mudar o nome do "u4"):
        // 1. Chamar editarPerfil() e verificar se retorna 'true'.
        // 2. Recuperar o usuário na persistência (dataManager).
        // 3. Verificar se o nome do usuário foi atualizado corretamente.
    }
}