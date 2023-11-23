package cl.sarayar.gestorTareasRest.config.auth.dto;

import cl.sarayar.gestorTareasRest.entities.Usuario;
import lombok.*;

@Generated
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

	private String token;
	private Usuario usuario;
}
