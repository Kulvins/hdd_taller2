package cl.sarayar.gestorTareasRest.controllers;

import cl.sarayar.gestorTareasRest.config.auth.dto.MessageResponse;
import cl.sarayar.gestorTareasRest.entities.Usuario;
import cl.sarayar.gestorTareasRest.services.UsuariosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuariosControllerTest {
    @Mock
    private UsuariosService usuariosService;
    @InjectMocks
    private UsuariosController usuariosController;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll(){
        List<Usuario> usuariosFake = Arrays.asList(new Usuario(), new Usuario());
        when(usuariosService.getAll()).thenReturn(usuariosFake);
        List<Usuario> response = usuariosController.getAll();

        assertEquals(usuariosFake, response);
    }

    @Test
    void authenticateUser(){
        Usuario usuario = new Usuario("1", "usuario","usuario@example.com","123",1);
        ResponseEntity<?> response = usuariosController.authenticateUser(usuario);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void testRegisterUser_UsuarioExiste() {
        Usuario usuario = new Usuario("1", "usuario","usuario@example.com","123",1);
        when(usuariosService.existsByCorreo(usuario.getCorreo())).thenReturn(true);
        ResponseEntity<?> response = usuariosController.registerUser(usuario);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof MessageResponse);
        assertEquals("Error: Usuario ya existe!", ((MessageResponse) response.getBody()).getMensaje());
    }

    @Test
    void testRegisterUserOK() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("usuario@example.com");
        when(usuariosService.existsByCorreo(usuario.getCorreo())).thenReturn(false);
        when(usuariosService.save(usuario)).thenReturn(usuario);
        ResponseEntity<?> response = usuariosController.registerUser(usuario);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void testActualizarUsuarioOK(){
        Usuario usuarioOriginal = new Usuario("1", "usuario1","usuario@example.com","123",1);
        Usuario usuarioActualizado = new Usuario("2", "usuario2","usuario@example.cl","1234",1);

        when(usuariosService.findById(usuarioActualizado.getId())).thenReturn(usuarioOriginal);
        when(usuariosService.findByCorreo(usuarioActualizado.getCorreo())).thenReturn(null);
        when(usuariosService.save(usuarioOriginal)).thenReturn(usuarioOriginal);

        ResponseEntity<?> response = usuariosController.actualizarUsuario(usuarioActualizado);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioOriginal, response.getBody());
    }

    @Test
    void testActualizarUsuarioFail(){
        Usuario usuarioOriginal = new Usuario("1", "usuario@example.com", "NombreOriginal","", 1);
        Usuario usuarioConMismoCorreo = new Usuario("2", "usuario@example.com", "NuevoNombre","", 1);

        when(usuariosService.findById(usuarioConMismoCorreo.getId())).thenReturn(usuarioOriginal);
        when(usuariosService.findByCorreo(usuarioConMismoCorreo.getCorreo())).thenReturn(usuarioOriginal);

        ResponseEntity<?> response = usuariosController.actualizarUsuario(usuarioConMismoCorreo);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof MessageResponse);
        assertEquals("Error: Correo se encuentra utilizado!", ((MessageResponse) response.getBody()).getMensaje());
    }


}