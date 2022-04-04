package com.royal.external;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    private final static Session SESSION;
    private final static String EMAIL = "totesfokes@gmail.com";
    private final static String SENHA = "qweruiop";
    private final static InternetAddress INTERNET_ADDRESS;

    static {

	try {
	    INTERNET_ADDRESS = new InternetAddress(EMAIL);
	} catch (AddressException ex) {
	    throw new RuntimeException(ex);
	}

	Properties props = new Properties();
	/**
	 * Parâmetros de conexão com servidor Gmail
	 */
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class",
		"javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", "465");

	SESSION = Session.getDefaultInstance(props,
		new javax.mail.Authenticator() {
	    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(EMAIL, SENHA);
	    }
	});
    }

    public static boolean enviar(String titulo, String mensagem, String destinatarios) {
	try {

	    Message message = new MimeMessage(SESSION);
	    message.setFrom(INTERNET_ADDRESS);
	    //Remetente

	    Address[] toUser = InternetAddress.parse(destinatarios);
	    
	    message.setRecipients(Message.RecipientType.TO, toUser);
	    message.setSubject(titulo);//Assunto
	    message.setText(mensagem);
	    /**
	     * Método para enviar a mensagem criada
	     */
	    Transport.send(message);

	} catch (MessagingException e) {
	    throw new RuntimeException(e);
	}

	return true;
    }
}
