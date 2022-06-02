
import com.royal.model.TransferenciaUsuario;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class teste{
    public static void main(String[] args) {
		//1 ano = 31536000000L
		//1 dia = 86400000L
		
		System.out.println(new Date().getTime() / 86400000l);
    }
}



