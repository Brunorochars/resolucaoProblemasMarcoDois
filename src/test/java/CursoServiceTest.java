import java.util.Optional;
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

    //andreus
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

    //andreus
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

    //andreus
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

    //andreus
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
        boolean resultado = cursoService.ingressarCurso("c1", null);
        assertTrue(resultado);
    }
}