package de.nikstack.niklist_server.lib.spring.mails

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class SimpleMailService(val mailSender: JavaMailSender) {
    @Value("\${spring.mail.username}")
    private lateinit var username: String

    fun sendMail(to: String, subject: String, text: String) {
        val mail = SimpleMailMessage()
        mail.from = username
        mail.setTo(to)
        mail.subject = subject
        mail.text = text

        mailSender.send(mail)
    }
}
