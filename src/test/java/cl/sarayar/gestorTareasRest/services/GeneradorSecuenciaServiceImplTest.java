package cl.sarayar.gestorTareasRest.services;

import cl.sarayar.gestorTareasRest.entities.Secuencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeneradorSecuenciaServiceImplTest {
    @Mock
    private MongoOperations mongoOperationsMock;

    @InjectMocks
    private GeneradorSecuenciaServiceImpl generadorSecuenciaService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGeneradorSecuencia_SecuenciaNoExiste_RetornaDefault() {
        String nombre = "secuenciaNoExistente";
        when(mongoOperationsMock.findAndModify(any(), any(), any()))
                .thenReturn(null);

        long secuenciaGenerada = generadorSecuenciaService.generadorSecuencia(nombre);

        assertEquals(1, secuenciaGenerada);
    }

    @Test
    void testGeneradorSecuenciaOK(){
        Secuencia secuencia = new Secuencia();
        secuencia.setId("123");
        secuencia.setSeq(2);

        when(mongoOperationsMock.findAndModify(any(), any(), any())).thenReturn(secuencia);
        long resultado = generadorSecuenciaService.generadorSecuencia("asdzxc");


        assertEquals(1, resultado);
    }

}