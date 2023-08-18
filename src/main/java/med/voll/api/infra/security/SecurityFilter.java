package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // é utilizado para que o Spring carregue uma classe/componente genérico
public class SecurityFilter extends OncePerRequestFilter { //herdando uma classe do spring que ja implementa a interface filter

    @Autowired //pedir pro Spring injetar essa classe
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        if(tokenJWT != null){ //ta vindo o token?
            var subject = tokenService.getSubject(tokenJWT); //recupera o token do cabeçalho e valida esse token se esta correto
            var usuario = repository.findByLogin(subject); //pega o usuario que está dentro do token

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); //carrega as informações no banco de dados
            SecurityContextHolder.getContext().setAuthentication(authentication); //força a autenticacao
        }
        filterChain.doFilter(request, response); // necessário para chamar os próximos filtros da aplicação
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
