package br.com.demo.auth;

import br.com.demo.entities.User;
import br.com.demo.repositories.UserRepository;
import br.com.demo.services.TokenService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private DetailsService detailsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token != null){
            try{
                Jwts.parser().setSigningKey("ProjVehiclesSales").parseClaimsJws(token).getBody();
            }catch (IllegalArgumentException e){
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }

        }
        if(token != null){
            Optional<String> userId = tokenService.getUserId(token);
            User user = userRepository.findById(userId.get());

            UserDetails userDetails = detailsService.create(user);
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        filterChain.doFilter(request,response);
    }
}
