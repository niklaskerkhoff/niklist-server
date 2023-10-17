package de.nikstack.niklist_server.lib.common

fun getSecureString(length: Int): String {
    val realLength = if (length < 8) 8 else length
    val chars =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!%&/()=?+*-,.;:"
    var password: String
    do {
        password = ""
        repeat(realLength) { password += chars.random() }
    } while (!isSecureString(password))
    return password
}

fun getDigitString(length: Int): String {
    val digitChars = "1234567890"
    return (1..length)
        .map { digitChars.random() }
        .joinToString("")
}


fun isSecureString(password: String): Boolean {
    val allowedSpecialChars = "!%&/()=?+*-,.;:"
    fun Char.isValid() =
        this.isLetterOrDigit() || this in allowedSpecialChars

    return password.firstMatches { it.isLetter() }
            && password.any { it.isLowerCase() }
            && password.any { it.isUpperCase() }
            && password.any { it.isDigit() }
            && password.any { allowedSpecialChars.contains(it) }
            && password.length >= 8
            && password.all { it.isValid() }
}


fun String.firstMatches(predicate: (Char) -> Boolean) = predicate(this[0])
