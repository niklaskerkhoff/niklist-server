package de.nikstack.niklist_server.lib.spring.security.mail_login

import de.nikstack.niklist_server.lib.common.getDigitString
import de.nikstack.niklist_server.lib.spring.security.TokenService
import de.nikstack.niklist_server.lib.spring.security.mail_login.core.MailLoginUserService
import de.nikstack.niklist_server.lib.spring.security.mail_login.core.UserMailValidationData
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("login")
class MailLoginController(
    private val mailLoginService: MailLoginService,
) {

    @PostMapping("request/{email}")
    fun requestLogin(@PathVariable email: String) =
        mailLoginService.requestLogin(email)

    @PostMapping("validate-mail")
    fun validateMail(@RequestBody data: UserMailValidationData) =
        mailLoginService.validateMail(data.email, data.code)

    @PostMapping("validate-device")
    @Throws(AuthenticationException::class)
    fun login(@RequestBody data: UserMailValidationData) =
        mailLoginService.login(data.email, data.code)
}
