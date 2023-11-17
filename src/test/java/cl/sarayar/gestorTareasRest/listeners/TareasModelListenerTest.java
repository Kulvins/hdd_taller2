package cl.sarayar.gestorTareasRest.listeners;

import cl.sarayar.gestorTareasRest.entities.Tarea;
import cl.sarayar.gestorTareasRest.services.GeneradorSecuenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TareasModelListenerTest {

    @Mock
    private GeneradorSecuenciaService generadorSecuenciaService;

    @InjectMocks
    private TareasModelListener tareasModelListener;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnBeforeConvertMenor1() {
        Tarea tarea = new Tarea();
        tarea.setIdentificador(0); // Identificador menor que 1

        when(generadorSecuenciaService.generadorSecuencia(Tarea.NOMBRE_SECUENCIA)).thenReturn(2L);

        BeforeConvertEvent<Tarea> event = new BeforeConvertEvent<>(tarea,"nombreColecccion");
        tareasModelListener.onBeforeConvert(event);

        assertEquals(2L, tarea.getIdentificador());
    }

}