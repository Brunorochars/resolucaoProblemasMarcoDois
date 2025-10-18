import model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.JsonDataManager;
import service.PostService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

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
        // TODO: Testar a remoção de um post existente (ex: "p1"):
        // 1. Armazenar o tamanho inicial da lista de posts.
        // 2. Chamar removerPost("p1") e verificar se retorna 'true'.
        // 3. Verificar se o tamanho da lista diminuiu em 1.
        // 4. Garantir que o post "p1" não existe mais na persistência (dataManager).
    }

    @Test
    void removerPostInexistenteDeveRetornarFalso() {
        // TODO: Testar a remoção de um post que não existe:
        // 1. Chamar removerPost() com um ID inexistente (ex: "p999").
        // 2. Verificar se o retorno é 'false'.
    }

    // REQUISITO: Curtir/descurtir posts (Usuário Comum)
    @Test
    void curtirPostDeveIncrementarContadorDeCurtidas() {
        // TODO: Testar a função de curtir post (ex: "p2"):
        // 1. Obter o número de curtidas iniciais do post.
        // 2. Chamar curtirPost("p2") e verificar se retorna 'true'.
        // 3. Verificar se o contador de curtidas do post na persistência (dataManager) aumentou em 1.
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