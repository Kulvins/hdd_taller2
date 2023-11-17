package cl.sarayar.gestorTareasRest.controllers;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.services.TareasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.management.OperationsException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TareasControllerTest {

    @Mock
    private TareasService tareasService;
    @InjectMocks
    private TareasController tareasController;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRemoveOk() throws OperationsException {
        when(tareasService.remove(anyString())).thenReturn(true);
        ResponseEntity<Boolean> resp = tareasController.delete("anyId");
        assertNotNull(resp);
        assertTrue(resp.getBody());
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    void testRemoveFail() throws OperationsException{
        when(tareasService.remove(anyString())).thenThrow(new NullPointerException());
        ResponseEntity<Boolean> response = tareasController.delete("anyId");
        assertNotNull(response);
        assertFalse(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testUpdateOk() throws OperationsException{
        Tarea tarea = new Tarea();
        tarea.setId("ASD123");
        tarea.setDescripcion("hjasgfjhasdkjfh");
        tarea.setVigente(true);
        when(tareasService.findById(tarea.getId())).thenReturn(tarea);
        when(tareasService.save(any(Tarea.class))).thenReturn(tarea);

        ResponseEntity<Tarea> response = tareasController.update(tarea);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
        assertEquals(tarea, response.getBody());
    }

    @Test
    void testUpdateFail() throws OperationsException{
        when(tareasService.findById("asd")).thenReturn(null);
        ResponseEntity<Tarea> response = tareasController.update(new Tarea());

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

    }
    @Test
    void testSaveOK() throws OperationsException{
        Tarea tarea = new Tarea();
        tarea.setFechaCreacion(null);
        tarea.setIdentificador(1);

        when(tareasService.save(any(Tarea.class))).thenReturn(tarea);
        ResponseEntity<Tarea> response = tareasController.save(tarea);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getFechaCreacion());
        assertEquals(1,response.getBody().getIdentificador());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
@Test
    void testGetAll() throws OperationsException{
        List<Tarea> listaTareas = Arrays.asList(new Tarea(), new Tarea(), new Tarea());
        when(tareasService.findAll()).thenReturn(listaTareas);
        List<Tarea> resultado = tareasController.getAll();

        assertNotNull(resultado);
        assertEquals(listaTareas.size(), resultado.size());
}

}