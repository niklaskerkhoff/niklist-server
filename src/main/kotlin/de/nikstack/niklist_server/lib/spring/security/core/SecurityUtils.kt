package de.nikstack.niklist_server.lib.spring.security.core

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.userdetails.UserDetails

interface UserBasicService {
    fun getUserDetailsByEmail(email: String): UserDetails

    fun addUser(email: String)
}

typealias CookieTokenExtractor = (request: HttpServletRequest, name: String) -> String?

/*interface CookieTokenExtractor {
    fun extract(request: HttpServletRequest, name: String): String?
}*/
