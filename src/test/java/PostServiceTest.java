import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Post;
import service.JsonDataManager;
import service.PostService;

public class PostServiceTest {

    private JsonDataManager dataManager;
    private PostService postService;

    @BeforeEach
    void setUp() {
        this.dataManager = new JsonDataManager();
        this.postService = new PostService(dataManager);
    }

    // REQUISITO: Visualizar posts/artigos (Usuário Comum)
    @Test
    void visualizarTodosDeveRetornarTodosOsPostsIniciais() {

        postService.visualizarTodos();
        List<Post> posts = postService.visualizarTodos();
        assertEquals(2, posts.size(), "Deve retornar exatamente 2 posts iniciais");
        
        // TODO: Testar a visualização de todos os posts:
        // 1. Chamar visualizarTodos().
        // 2. Verificar se a lista retornada tem o tamanho esperado (2 nos dados iniciais).
    }

    // REQUISITO: Remover posts inadequados (Administrador)
    @Test
    void removerPostDeveRemoverPostExistente() {
        int tamanhoInicial = postService.visualizarTodos().size();

        boolean removido = postService.removerPost("p1");

        assertTrue(removido, "A remoção de um post existente deve retornar true");
        assertEquals(tamanhoInicial - 1, postService.visualizarTodos().size(), "O tamanho da lista deve diminuir em 1");

        Optional<Post> postRemovido = dataManager.getPosts().stream()
                .filter(p -> p.getId().equals("p1"))
                .findFirst();
        assertFalse(postRemovido.isPresent(), "O post 'p1' não deve mais existir na persistência");
    }

    @Test
    void removerPostInexistenteDeveRetornarFalso() {
        boolean removido = postService.removerPost("p999");
        assertFalse(removido, "A remoção de um post que não existe deve retornar false");
    }

    // REQUISITO: Curtir/descurtir posts (Usuário Comum)
    @Test
    void curtirPostDeveIncrementarContadorDeCurtidas() {
        Optional<Post> postAntes = dataManager.getPosts().stream().filter(p -> p.getId().equals("p2")).findFirst();
        assumeTrue(postAntes.isPresent(), "O post 'p2' deve existir para este teste.");
        int curtidasIniciais = postAntes.get().getCurtidas();

        boolean curtiu = postService.curtirPost("p2");
        assertTrue(curtiu, "Curtir um post existente deve retornar true");

        int curtidasFinais = dataManager.getPosts().stream().filter(p -> p.getId().equals("p2")).findFirst().get().getCurtidas();
        assertEquals(curtidasIniciais + 1, curtidasFinais, "O número de curtidas deve ser incrementado em 1.");
    }

    // REQUISITO: Filtrar artigos por temas/tags (Usuário Comum) gabriel dornelles
    @Test
    void filtrarPorTagDeveRetornarPostsComTagCorrespondente() {
        List<Post> resultado = postService.filtrarPorTag("Java");
        assertEquals(1, resultado.size(), "Deve retornar exatamente 1 post com a tag 'Java'");
        assertEquals("Novidades do Java 21", resultado.get(0).getTitulo());
    }
    

    @Test
    void filtrarPorTagDeveIgnorarCaseSensitivity() { 
        List<Post> resultadoMinusculo = postService.filtrarPorTag("java");
        List<Post> resultadoMaiusculo = postService.filtrarPorTag("JAVA");
        assertEquals(1, resultadoMinusculo.size(), "Deve retornar 1 post usando 'java' minúsculo");
        assertEquals(1, resultadoMaiusculo.size(), "Deve retornar 1 post usando 'JAVA' maiúsculo");
        assertEquals("Novidades do Java 21", resultadoMinusculo.get(0).getTitulo());
        assertEquals("Novidades do Java 21", resultadoMaiusculo.get(0).getTitulo());
    }

    @Test
    void filtrarPorTagInexistenteDeveRetornarListaVazia() { 
        List<Post> resultado = postService.filtrarPorTag("TagInexistente");
        assertTrue(resultado.isEmpty(), "Deve retornar uma lista vazia quando a tag não existir");
    }

    // REQUISITO: Visualizar métricas de engajamento (Administrador)
    @Test
    void getCurtidasTotaisDeveRetornarSomaCorreta() {
        // TODO: Testar o cálculo do total de curtidas:
        // 1. Chamar getCurtidasTotais() e verificar se o total inicial é correto (2).
        // 2. Simular uma nova interação (ex: curtirPost("p2")).
        // 3. Chamar getCurtidasTotais() novamente e verificar se o novo total (3) está correto.
    }
}