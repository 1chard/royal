
import com.royal.external.Mail;
import com.royal.model.TransferenciaUsuario;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class teste{
    public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("teste");
//		Mail.enviar("teste", "lenda no clash", "richardcutrim01@gmail.com").get();

	    var cal = new GregorianCalendar();

		cal.setTime(java.sql.Date.valueOf("2019-01-31"));

		cal.add(Calendar.MONTH, 1);

	    System.out.println(cal.getTime());


    }
}



