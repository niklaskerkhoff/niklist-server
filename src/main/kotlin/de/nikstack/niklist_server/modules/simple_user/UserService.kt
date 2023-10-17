package de.nikstack.niklist_server.modules.simple_user

import de.nikstack.niklist_server.lib.spring.getCurrentUserEmail
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val userRepo: UserRepo
) {
    fun getUserByEmail(email: String) = userRepo.findByEmail(email)

    fun addUser(email: String, encodedCode: String) {
        val name = email.split("@")[0]
        val user = AppUser(
            email,
            name,
            UserRole.USER,
            encodedCode
        )
        userRepo.save(user)
    }

    fun setCredentials(email: String, encodedCode: String) {
        val user = getUserByEmail(email)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        user.password = encodedCode
        userRepo.save(user)
    }

    fun getUserInfoByEmail(email: String): UserInfo {
        val user = getUserByEmailAuthorizedOrThrow(email)
        return user.toUserInfo()
    }

    fun updateName(email: String, name: String): UserInfo {
        val user = getUserByEmailAuthorizedOrThrow(email)
        user.name = name
        return user.toUserInfo()
    }

    private fun getUserByEmailAuthorizedOrThrow(email: String): AppUser {
        val user = getUserByEmail(email)

        if (user == null || user.email != getCurrentUserEmail())
            throw ResponseStatusException(HttpStatus.NOT_FOUND)

        return user
    }

    private fun AppUser.toUserInfo() = UserInfo(email, name)
}
