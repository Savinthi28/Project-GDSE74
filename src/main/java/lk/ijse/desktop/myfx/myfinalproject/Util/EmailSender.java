package lk.ijse.desktop.myfx.myfinalproject.Util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSender {

    // වැදගත්: මේ තැන් වලට ඔයාගේ නියම ඊමේල් ලිපිනයයි, "App password" එකයි දෙන්න.
    // Google App Password එකක් හදාගන්න ආකාරය පහලින් පැහැදිලි කරලා තියෙනවා.
    private static final String SENDER_EMAIL = "vithanagesavinthi@gmail.com"; // මෙතනට ඔයාගේ ඊමේල් ලිපිනය දෙන්න (උදා: savinthimavithanage@gmail.com)
    private static final String SENDER_PASSWORD = "xkxm icsy rxwq zqur"; // මෙතනට ඔයාගේ Google App Password එක දෙන්න

    public static boolean sendEmail(String recipientEmail, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Gmail සඳහා. වෙනත් email provider කෙනෙක් නම් වෙනස් කරන්න.
        properties.put("mail.smtp.port", "587"); // TLS port
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // TLS enable කරන්න
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // TLSv1.2 ප්‍රොටෝකෝලය භාවිතා කරන්න.
        // මෙය සමහර විට අවශ්‍ය වෙන්න පුළුවන් නවීන Java versions වලට.

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("ඊමේල් සාර්ථකව " + recipientEmail + " වෙත යවන ලදී.");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("ඊමේල් යැවීමේ දෝෂයක්: " + e.getMessage());
            return false;
        }
    }
}