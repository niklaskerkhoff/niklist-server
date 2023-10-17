package de.nikstack.niklist_server.config.security

import de.nikstack.niklist_server.lib.spring.security.mail_login.core.MailLoginUserService
import de.nikstack.niklist_server.modules.entry.Entry
import de.nikstack.niklist_server.modules.simple_user.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val userService: UserService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http
            .csrf(CsrfConfigurer<HttpSecurity>::disable)
            .anonymous().and()
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .anyRequest().permitAll()
            }
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity>::jwt)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()
    }

    @Bean
    fun mailLoginUserService() = object : MailLoginUserService {
        override fun getUserDetailsByEmail(email: String) =
            when (val user = userService.getUserByEmail(email)) {
                null -> null
                else -> SecurityUser(user)
            }

        override fun handleUserNotFound(email: String, encodedCode: String) {
            userService.addUser(email, encodedCode)
        }

        override fun setCredentials(email: String, encodedCode: String) {
            userService.setCredentials(email, encodedCode)
        }
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOrigins = listOf(
            "capacitor://localhost",
            "http://192.168.178.50:8100",
            "http://192.168.178.50:8101/",
            "http://192.168.178.50:8102/",
            "http://192.168.178.50:8103/",
            "http://192.168.178.50:8104/",
            "http://192.168.178.50:8105/",
            "http://192.168.178.50:8106/",
            "http://192.168.178.50:8107/",
            "http://192.168.178.50:8108/",
            "http://192.168.178.50:8109/",
            "http://127.0.0.1:5173",
            "http://localhost:3000",
            "http://localhost:8100",
            "https://niklist.nikstack.de",
            "https://niklist-app.nikstack.de",
        )
        config.allowedHeaders = listOf(
            "Origin", "Content-Type", "Accept", "Authorization",
            "Access-Control-Allow-Origin"
        )
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}
