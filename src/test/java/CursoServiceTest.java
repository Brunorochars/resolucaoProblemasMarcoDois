import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Curso;
import model.StatusCurso;
import service.CursoService;
import service.JsonDataManager;

public class CursoServiceTest {

    private JsonDataManager dataManager;
    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        this.dataManager = new JsonDataManager();
        this.cursoService = new CursoService(dataManager);
    }

    // REQUISITO: Criar novos cursos (Professor) Gabriel Ortiz
    @Test
    void criarCursoDeveAdicionarNovoCursoAPersistencia() {
        // TODO: Testar a criação de um novo curso:
        // 1. Verificar se o novo curso não é nulo.
        // 2. Verificar se o título e a descrição estão corretos.
        // 3. Confirmar que o status inicial é PENDENTE_APROVACAO.
        // 4. Garantir que o tamanho da lista de cursos aumentou em 1.
    }

    // REQUISITO: Editar cursos existentes (Professor/Admin) Gabriel Ortiz
    @Test
    void editarCursoDeveAtualizarTituloEDescricao() {
        // TODO: Testar a edição de um curso existente (ex: "c1"):
        // 1. Chamar editarCurso() e verificar se o retorno é 'true'.
        // 2. Recuperar o curso na persistência (dataManager).
        // 3. Verificar se o título e a descrição foram atualizados corretamente.
    }

    // REQUISITO: Configurar proteção por PIN de acesso (Professor) Andreus
    @Test
    void configurarPinDeveAdicionarPinAoCurso() {
        boolean ok = cursoService.configurarPin("c1", "0000");
        assertTrue(ok);
        Curso c = dataManager.getCursos().stream()
                .filter(x -> x.getId().equals("c1"))
                .findFirst()
                .orElse(null);
        assertNotNull(c);
        assertEquals("0000", c.getPinAcesso());
    }

    // REQUISITO: Aprovar/rejeitar cursos (Administrador) andreus
    @Test
    void aprovarCursoDeveMudarStatusParaAtivo() {
        boolean ok = cursoService.aprovarCurso("c2");
        assertTrue(ok);
        Curso c = dataManager.getCursos().stream()
                .filter(x -> x.getId().equals("c2"))
                .findFirst()
                .orElse(null);
        assertNotNull(c);
        assertEquals(StatusCurso.ATIVO, c.getStatus());
    }

    @Test
    void rejeitarCursoDeveMudarStatusParaInativo() { //Andreus
        boolean ok = cursoService.rejeitarCurso("c1");
        assertTrue(ok);
        Curso c = dataManager.getCursos().stream()
                .filter(x -> x.getId().equals("c1"))
                .findFirst()
                .orElse(null);
        assertNotNull(c);
        assertEquals(StatusCurso.INATIVO, c.getStatus());
    }

    // REQUISITO: Visualizar catálogo de cursos disponíveis (Estudante/Comum) andreus
    @Test
    void visualizarCatalogoDeveRetornarApenasCursosAtivos() {

        List<Curso> catalogo = cursoService.visualizarCatalogo();
            assertTrue(catalogo.stream().allMatch(c -> c.getStatus() == StatusCurso.ATIVO));
            long esperado = dataManager.getCursos().stream().filter(c -> c.getStatus() == StatusCurso.ATIVO).count();
            assertEquals(esperado, catalogo.size());
        }
    

    // REQUISITO: Ingressar em cursos (com inserção de PIN quando necessário) Pietro
    @Test
    void ingressarCursoComPinDeveFuncionarComPinCorreto() {
        
        cursoService.aprovarCurso("c2");

        boolean ingressou = cursoService.ingressarCurso("c2", "1234");
        assertTrue(ingressou, "Deve ser possível ingressar em um curso ativo com o PIN correto.");
    }

    // REQUISITO: Ingressar em cursos (com inserção de PIN quando necessário) Gabriel Dornelles.
    @Test
    void ingressarCursoComPinDeveFalharComPinIncorreto() {
        Curso curso = cursoService.criarCurso("Curso de Java", "Descrição do curso", "professor1");
        curso.setStatus(StatusCurso.ATIVO);
        curso.setPinAcesso("1234");
        boolean resultado = cursoService.ingressarCurso(curso.getId(), "9999");
        assertFalse(resultado);
    }


    @Test
    void ingressarCursoSemPinDeveFuncionar() { //gabriel ortiz
        // TODO: Testar o ingresso em um curso SEM PIN (ex: "c1"):
        // 1. Chamar ingressarCurso() passando 'null' ou uma string vazia como PIN.
        // 2. Verificar se o retorno é 'true'.
    }
}