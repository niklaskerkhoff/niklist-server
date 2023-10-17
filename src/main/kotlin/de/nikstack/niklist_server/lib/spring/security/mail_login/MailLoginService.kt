package de.nikstack.niklist_server.lib.spring.security.mail_login

import de.nikstack.niklist_server.lib.common.getDigitString
import de.nikstack.niklist_server.lib.common.getSecureString
import de.nikstack.niklist_server.lib.spring.mails.SimpleMailService
import de.nikstack.niklist_server.lib.spring.security.TokenService
import de.nikstack.niklist_server.lib.spring.security.mail_login.core.MailLoginUserService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class MailLoginService(
    private val tokenService: TokenService,
    private val jwtDecoder: JwtDecoder,
    private val passwordEncoder: PasswordEncoder,
    private val authManager: AuthenticationManager,
    private val mailLoginUserService: MailLoginUserService,
    private val simpleMailService: SimpleMailService
) {
    private val securePasswordLength = 63
    private val loginCodeLength = 8

    fun validateMail(email: String, emailValidationJwt: String) {
        // TODO: isValid(jwt) && jwt.email == email
    }

    @Throws(AuthenticationException::class)
    fun login(email: String, code: String): String {

        val authentication = try {
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(email, code)
            )
        } catch (e: AuthenticationException) {
            // TODO: Change password after n attempts
            // TODO: Probably better to handle this in spring security
            throw e
        }

        mailLoginUserService.setCredentials(email, getSecurePassword())
        return tokenService.generateToken(authentication)
    }

    fun requestLogin(email: String) {
        val user = mailLoginUserService.getUserDetailsByEmail(email)
        if (user == null) {
            mailLoginUserService.handleUserNotFound(
                email,
                getSecurePassword()
            )
        }

        mailLoginUserService.getUserDetailsByEmail(email)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST)

        val code = getDigitString(loginCodeLength)
        val encodedCode = passwordEncoder.encode(code)

        mailLoginUserService.setCredentials(email, encodedCode)
        simpleMailService.sendMail(
            email,
            "Neue Anmeldung bei Nikstack",
            "Gib folgenden Code ein, um dich anzumelden: $code"
        )
    }

    private fun getSecurePassword(): String {
        val password = getSecureString(securePasswordLength)
        return passwordEncoder.encode(password)
    }
}
