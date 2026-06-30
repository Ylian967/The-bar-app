package fr.barapp.barapp.config;

import fr.barapp.barapp.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/** Configuration de la sécurité : routes publiques vs réservées au barmaker, JWT, CORS. */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public
                        .requestMatchers("/api/auth/**", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/categories/**", "/api/cocktails/**", "/api/ingredients/**").permitAll()
                        // Barmaker uniquement (avant les règles client plus larges sur /commandes)
                        .requestMatchers(HttpMethod.GET, "/api/commandes/a-traiter", "/api/commandes/terminees").hasRole("BARMAKER")
                        .requestMatchers(HttpMethod.PATCH, "/api/commandes/**", "/api/cocktails/**", "/api/ingredients/**").hasRole("BARMAKER")
                        // Client : passer une commande et la suivre
                        .requestMatchers(HttpMethod.POST, "/api/commandes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/commandes/**").permitAll()
                        // Gestion de la carte = barmaker
                        .requestMatchers(HttpMethod.POST,
                                "/api/cocktails/**", "/api/categories/**", "/api/ingredients/**").hasRole("BARMAKER")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/cocktails/**", "/api/categories/**", "/api/ingredients/**").hasRole("BARMAKER")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/cocktails/**", "/api/categories/**", "/api/ingredients/**").hasRole("BARMAKER")
                        // Catalogue externe (recherche + import) = barmaker
                        .requestMatchers("/api/catalogue-externe/**").hasRole("BARMAKER")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Toutes origines autorisées : l'app est servie même origine via nginx, et accessible
        // depuis n'importe quel appareil du réseau (téléphone). Sûr ici car l'auth est par
        // jeton JWT dans l'en-tête Authorization (pas de cookie de session).
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
