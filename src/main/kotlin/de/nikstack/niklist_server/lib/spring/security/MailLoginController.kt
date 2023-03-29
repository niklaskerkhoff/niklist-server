package de.nikstack.niklist_server.lib.spring.security

import de.nikstack.niklist_server.lib.spring.security.core.UserMailValidationData
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("login")
class MailLoginController(
    private val tokenService: TokenService,
    private val jwtDecoder: JwtDecoder,
    private val authManager: AuthenticationManager,
) {

    @PostMapping("validate-mail")
    fun validateMail(@RequestBody data: UserMailValidationData) {
        // Validate mail-code (jwt): isValid(jwt) && jwt.email == data.email
    }

    @PostMapping("validate-device")
    @Throws(AuthenticationException::class)
    fun login(@RequestBody data: UserMailValidationData): String {

        val authentication = try {
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    data.email,
                    data.code
                )
            )
        } catch (e: AuthenticationException) {
            // Probably better to handle this in security config
            // Change password after n attempts
            throw e
        }

        return tokenService.generateToken(authentication)
    }

    @PostMapping("request-login/{email}")
    fun requestLogin(@PathVariable email: String) {

    }
}
