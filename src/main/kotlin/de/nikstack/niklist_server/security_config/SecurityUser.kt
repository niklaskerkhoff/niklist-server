package de.nikstack.niklist_server.security_config

import de.nikstack.niklist_server.modules.simple_user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class SecurityUser(private val user: User) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        val authority = SimpleGrantedAuthority(user.role.name)
        return listOf(authority)
    }

    override fun getPassword() = user.password

    override fun getUsername() = user.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
