package de.nikstack.niklist_server.lib.spring.security

import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.proc.SecurityContext
import de.nikstack.niklist_server.lib.spring.security.mail_login.core.CookieTokenExtractor
import de.nikstack.niklist_server.lib.spring.security.mail_login.core.MailLoginUserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.web.util.WebUtils
import javax.crypto.spec.SecretKeySpec


@Configuration
class SecurityBeansConfig(
    private val mailLoginUserService: MailLoginUserService
) {
    @Value("\${app.jwtSecret}")
    private lateinit var jwtSecret: String

    @Bean
    fun authManager(
        userDetailsService: UserDetailsService,
        passwordEncoder: PasswordEncoder
    ): AuthenticationManager {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder)
        return ProviderManager(authProvider)
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { email ->
            mailLoginUserService.getUserDetailsByEmail(email)
                ?: throw UsernameNotFoundException("User not found")
        }
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withSecretKey(
            SecretKeySpec(
                jwtSecret.toByteArray(),
                "HmacSHA256"
            )
        ).build()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val key = SecretKeySpec(jwtSecret.toByteArray(), "HmacSHA256")
        val immutableSecret = ImmutableSecret<SecurityContext>(key)
        return NimbusJwtEncoder(immutableSecret)
    }

    @Bean
    fun cookieTokenExtractor(): CookieTokenExtractor = { request, name ->
        val cookie = WebUtils.getCookie(request, name)?.value
        cookie?.drop(7)
    }


    /*@Bean
    fun cookieTokenExtractor(): CookieTokenExtractor {
        return object : CookieTokenExtractor {
            override fun extract(
                request: HttpServletRequest,
                name: String
            ): String? {
                val cookie = WebUtils.getCookie(request, name)?.value
                return cookie?.drop(7)
            }
        }
    }*/
}
