package de.nikstack.niklist_server.modules.simple_user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("{email}")
    fun getById(@PathVariable email: String) = userService.getUserByEmail(email)
}
