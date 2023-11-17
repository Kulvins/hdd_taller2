package cl.sarayar.gestorTareasRest.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import cl.sarayar.gestorTareasRest.entities.Usuario;
import cl.sarayar.gestorTareasRest.repositories.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UsuariosServiceImplTest {

    @Mock
    private UsuariosRepository usRepoMock;

    @InjectMocks
    private UsuariosServiceImpl usuariosService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario();
        when(usRepoMock.save(usuario)).thenReturn(usuario);

        Usuario usuarioGuardado = usuariosService.save(usuario);

        assertEquals(usuario, usuarioGuardado);
    }

    @Test
    public void testGetAll() {
        List<Usuario> listaUsuarios = Arrays.asList(new Usuario(), new Usuario());

        when(usRepoMock.findAll()).thenReturn(listaUsuarios);

        List<Usuario> usuariosObtenidos = usuariosService.getAll();

        assertNotNull(usuariosObtenidos);
        assertEquals(listaUsuarios.size(), usuariosObtenidos.size());

    }

    @Test
    public void testFindByCorreo_Existe() {
        String correo = "correo@ejemplo.com";
        Usuario usuario = new Usuario();
        when(usRepoMock.findByCorreo(correo)).thenReturn(Optional.of(usuario));

        Usuario usuarioEncontrado = usuariosService.findByCorreo(correo);

        assertNotNull(usuarioEncontrado);
        assertEquals(usuario, usuarioEncontrado);
    }

    @Test
    public void testFindByCorreo_NoExiste() {
        String correoNoExistente = "correo@noexiste.com";
        when(usRepoMock.findByCorreo(correoNoExistente)).thenReturn(Optional.empty());

        Usuario usuarioEncontrado = usuariosService.findByCorreo(correoNoExistente);

        assertNull(usuarioEncontrado);
    }

    @Test
    public void testFindById_Existe() {
        String idExistente = "idExistente";
        Usuario usuario = new Usuario();
        when(usRepoMock.findById(idExistente)).thenReturn(Optional.of(usuario));

        Usuario usuarioEncontrado = usuariosService.findById(idExistente);

        assertNotNull(usuarioEncontrado);
        assertEquals(usuario, usuarioEncontrado);
    }

    @Test
    public void testFindById_NoExiste() {
        String idNoExistente = "idNoExistente";
        when(usRepoMock.findById(idNoExistente)).thenReturn(Optional.empty());

        Usuario usuarioEncontrado = usuariosService.findById(idNoExistente);

        assertNull(usuarioEncontrado);
    }

    @Test
    public void testLoadUserByUsername_Existe() {
        String username = "usuario@ejemplo.com";
        Usuario usuario = new Usuario();
        when(usRepoMock.findByCorreo(username)).thenReturn(Optional.of(usuario));

        UserDetails userDetails = usuariosService.loadUserByUsername(username);

        assertNotNull(userDetails);
    }

    @Test
    public void testExisteCorreo() {
        String correo = "correo@ejemplo.com";
        when(usRepoMock.existsByCorreo(correo)).thenReturn(true);

        assertTrue(usuariosService.existsByCorreo(correo));
    }

}