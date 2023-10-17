package de.nikstack.niklist_server.lib.spring.security.mail_login.core

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.userdetails.UserDetails

interface MailLoginUserService {
    fun getUserDetailsByEmail(email: String): UserDetails?

    fun handleUserNotFound(email: String, encodedCode: String)

    fun setCredentials(email: String, encodedCode: String)
}

typealias CookieTokenExtractor =
            (request: HttpServletRequest, name: String) -> String?

/*interface CookieTokenExtractor {
    fun extract(request: HttpServletRequest, name: String): String?
}*/
