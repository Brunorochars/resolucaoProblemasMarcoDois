import java.util.Optional;
import java.util.List;
import model.Curso;
import model.StatusCurso;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        int tamanhoAntes = dataManager.getCursos().size();
        Curso novoCurso = cursoService.criarCurso("Curso de Testes unitarios", "Testando", "prof1");
        assertNotNull(novoCurso);
        assertEquals(novoCurso.getTitulo(), "Curso de Testes unitarios");
        assertEquals(novoCurso.getDescricao(), "Testando");
        assertEquals(StatusCurso.PENDENTE_APROVACAO, novoCurso.getStatus());
        int tamanhoFinal = dataManager.getCursos().size();
        assertEquals(tamanhoAntes + 1, tamanhoFinal);
    }

    // REQUISITO: Editar cursos existentes (Professor/Admin) Gabriel Ortiz
    @Test
    void editarCursoDeveAtualizarTituloEDescricao() {
        boolean ok = cursoService.editarCurso("c1", "Edição", "Testando edição");
        assertTrue(ok);
        Optional<Curso> cursoEditado = dataManager.getCursos().stream()
                .filter(x -> x.getId().equals("c1"))
                .findFirst();
        assertTrue(cursoEditado.isPresent());
        Curso Atualizado = cursoEditado.get();
        assertEquals("Edição", Atualizado.getTitulo());
        assertEquals("Testando edição",  Atualizado.getDescricao());
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
        // TODO: Testar o ingresso em um curso com PIN (ex: "c2"):
        // 1. Garantir que o curso está ATIVO (configurar se necessário).
        // 2. Chamar ingressarCurso() com o PIN CORRETO.
        // 3. Verificar se o retorno é 'true'.
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
        boolean resultado = cursoService.ingressarCurso("c1", null);
        assertTrue(resultado);
    }
}