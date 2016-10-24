package com.example.hitman.tcc2.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnvioEmailEmpresa {


    public EnvioEmailEmpresa() {
    }

    public void sendTarifa(String to1) {


        String to = to1;
        String from = "empresafic4001@gmail.com";
        final String username = "empresafic4001@gmail.com";
        final String password = "Magalhaes80";//change accordingly

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.debug", "true");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new
                                PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Fatura Empresa4001");

            MimeBodyPart messageBodyPart1 = new MimeBodyPart();

            messageBodyPart1.setText("Geração de Fatura \n" +
                    "Aguarde confirmação de pagamento do cliente \n " +
                    "Envie os itens para o endereço que consta no anexo \n" +
                    "Atenciosamente Empresa4001");
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            String fileName = "/data/data/com.example.hitman.tcc2/files/faturaEmpresa.txt";//change accordingly
            DataSource source = new FileDataSource(fileName);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(fileName);

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email Foi Enviado com Sucesso.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}