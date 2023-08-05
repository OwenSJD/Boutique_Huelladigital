package com.example.boutiqueshop;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String email;
    private String subject;
    private String message;

    public EmailSender(Context context, String email, String subject, String message) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        sendEmail();
        return null;
    }

    private void sendEmail() {
        final String correoEmisor = "movilesequipou@gmail.com"; // Correo electrónico del remitente
        final String contraseñaEmisor = "movilesdos"; // Contraseña del remitente

        // Configuración del servidor de correo saliente (SMTP)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Cambia esto si utilizas otro servidor de correo
        props.put("mail.smtp.port", "587"); // Puerto SMTP para TLS

        // Crear una sesión de correo con autenticación
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoEmisor, contraseñaEmisor);
            }
        });

        try {
            // Crear el mensaje de correo
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(correoEmisor)); // Correo del remitente
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // Correo del destinatario
            emailMessage.setSubject(subject); // Asunto del correo
            emailMessage.setText(message); // Cuerpo del correo

            // Enviar el mensaje de correo
            Transport.send(emailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "Correo electrónico enviado", Toast.LENGTH_SHORT).show();
    }
}