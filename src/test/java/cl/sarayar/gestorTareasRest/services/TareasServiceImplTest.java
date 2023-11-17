package cl.sarayar.gestorTareasRest.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.repositories.TareasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TareasServiceImplTest {

    @Mock
    private TareasRepository tareasRepositoryMock;

    @InjectMocks
    private TareasServiceImpl tareasService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll_RetornaAllTareas() {
        // Simular el comportamiento del repositorio al llamar findAll()
        List<Tarea> tareas = new ArrayList<>();
        when(tareasRepositoryMock.findAll()).thenReturn(tareas);

        List<Tarea> tareasEncontradas = tareasService.findAll();

        assertEquals(tareas, tareasEncontradas); // Verificar que devuelve todas las tareas
    }

    @Test
    public void testSave_GuardaTarea() {
        // Simular el comportamiento del repositorio al guardar una tarea
        Tarea tarea = new Tarea(); // Crear una nueva tarea
        when(tareasRepositoryMock.save(any(Tarea.class))).thenReturn(tarea);

        Tarea tareaGuardada = tareasService.save(tarea);

        assertEquals(tarea, tareaGuardada); // Verificar que la tarea guardada es la misma que la original
    }

    @Test
    public void testRemove_ExisteId_RetornaTrue() {
        // Simular el comportamiento del repositorio al eliminar una tarea existente
        String idExistente = "idExistente";
        doNothing().when(tareasRepositoryMock).deleteById(idExistente);

        boolean resultado = tareasService.remove(idExistente);

        assertTrue(resultado); // Verificar que retorna true cuando se elimina una tarea existente
    }

    @Test
    public void testRemove_NoExisteId_RetornaFalse() {
        // Simular el comportamiento del repositorio al intentar eliminar una tarea con un ID no existente
        String idNoExistente = "idNoExistente";
        doThrow(IllegalArgumentException.class).when(tareasRepositoryMock).deleteById(idNoExistente);

        boolean resultado = tareasService.remove(idNoExistente);

        assertFalse(resultado); // Verificar que retorna false cuando se intenta eliminar una tarea con un ID no existente
    }

    @Test
    public void testFindById_ExisteId_RetornaTarea() {
        // Simular el comportamiento del repositorio al buscar una tarea existente por ID
        String idExistente = "idExistente";
        Tarea tarea = new Tarea(); // Crear una tarea simulada
        when(tareasRepositoryMock.findById(idExistente)).thenReturn(Optional.of(tarea));

        Tarea tareaEncontrada = tareasService.findById(idExistente);

        assertEquals(tarea, tareaEncontrada); // Verificar que devuelve la tarea esperada con un ID existente
    }

    @Test
    public void testFindById_NoExisteId_RetornaNull() {
        // Simular el comportamiento del repositorio al buscar una tarea con un ID no existente
        String idNoExistente = "idNoExistente";
        when(tareasRepositoryMock.findById(idNoExistente)).thenReturn(Optional.empty());

        Tarea tareaEncontrada = tareasService.findById(idNoExistente);

        assertNull(tareaEncontrada); // Verificar que devuelve null cuando se busca una tarea con un ID no existente
    }
}
