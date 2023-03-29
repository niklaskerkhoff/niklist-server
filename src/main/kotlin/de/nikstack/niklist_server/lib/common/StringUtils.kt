package de.nikstack.niklist_server.lib.common

fun generateSecureString(length: Int): String {
    val realLength = if (length < 8) 8 else length
    val chars =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!%&/()=?+*-,.;:"
    var password: String
    do {
        password = ""
        repeat((1..realLength).count()) { password += chars.random() }
    } while (!isSecureString(password))
    return password
}

fun isSecureString(password: String): Boolean {
    val allowedSpecialChars = "!%&/()=?+*-,.;:"
    fun Char.isValid() =
        isLetterOrDigit() || allowedSpecialChars.contains(this)

    return password.firstMatches { it.isLetter() }
            && password.any { it.isLowerCase() }
            && password.any { it.isUpperCase() }
            && password.any { it.isDigit() }
            && password.any { allowedSpecialChars.contains(it) }
            && password.length >= 8
            && password.all { it.isValid() }
}


fun String.firstMatches(predicate: (Char) -> Boolean) = predicate(this[0])
