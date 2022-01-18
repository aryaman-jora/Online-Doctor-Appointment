import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@SuppressWarnings("SameParameterValue")
class Mailer
    {
        static void send(String from, String password, String to, String sub, String msg)
            {
                //Get properties object
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");
                //get Session
                Session session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator()
                            {
                                protected PasswordAuthentication getPasswordAuthentication()
                                    {
                                        return new PasswordAuthentication(from, password);
                                    }
                            });
                //compose message
                try
                    {
                        // Create a default MimeMessage object.
                        Message message = new MimeMessage(session);

                        // Set From: header field of the header.
                        message.setFrom(new InternetAddress(from));


                        // message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                        // Set To: header field of the header.
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("devbaweja576@gmail.com"));

                        // Set Subject: header field
                        message.setSubject(sub);

                        // Create the message part
                        BodyPart messageBodyPart = new MimeBodyPart();
                        // Now set the actual message
                        messageBodyPart.setContent(msg, "text/html");

                        // Creating the image part
                        BodyPart imageBodyPart = new MimeBodyPart();
                        String filename = "F:\\Eclipse\\Projects\\Doctor-Appointment\\bin\\attach.png";
                        DataSource source = new FileDataSource(filename);
                        imageBodyPart.setDataHandler(new DataHandler(source));
                        imageBodyPart.setFileName("Prior Health");
                        imageBodyPart.setHeader("Health", "<image>");

                        // Create a multiple parts
                        Multipart multipart = new MimeMultipart();

                        // Set message part
                        multipart.addBodyPart(messageBodyPart);
                        // Set image part
                        multipart.addBodyPart(imageBodyPart);


                        // Send the complete message parts
                        message.setContent(multipart);


                        // send message
                        Transport.send(message);
                        System.out.println("Mail sent successfully");
                    } catch (MessagingException e)
                    {
                        throw new RuntimeException(e);
                    }
            }
    }

class SendMailSSL
    {
        // public static void main(String[] args)
        public SendMailSSL()
            {
                //from,password,to,subject,message
                String subject = "Welcome to Online Doctor Appointment App";
                String message = "<h1>Hello! Welcome!!! </h1>"
                        + "Ohhh my goodness, we're so thrilled you decided to join our family. Hats off making an execllent decision."
                        + "You are officially in the loop to hear all about our most experienced doctors all around globe."
                        + "<pre style='color:green'>It is health that is real wealth and not pieces of gold and silver.</pre>";

                Mailer.send("onlinedoct24@gmail.com", "0nline_d@ct24", "devbaweja576@gmail.com", subject, message);
                //change from, password and to
            }
    }