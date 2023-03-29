package de.nikstack.niklist_server.modules.simple_user

import de.nikstack.niklist_server.lib.common.generateSecureString
import de.nikstack.niklist_server.lib.spring.getCurrentUserEmail
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val userRepo: UserRepo
) {

    fun getUserByEmail(email: String) = userRepo.findByEmail(email)

    fun getUserInfoByEmail(email: String): UserInfo {
        val user = getUserByEmailAuthorizedOrThrow(email)
        return user.toUserInfo()
    }

    fun addUser(email: String) {
        val name = email.split("@")[0]
        val user = AppUser(
            email,
            name,
            UserRole.USER,
            generateSecureString(63)
        )
        userRepo.save(user)
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
