
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

public class teste{
    public static void main(String[] args) {
		System.out.println("teste");
//		Mail.enviar("teste", "lenda no clash", "richardcutrim01@gmail.com").get();

	    var cal = new GregorianCalendar();

		cal.setTime(java.sql.Date.valueOf("2019-01-31"));

		cal.add(Calendar.MONTH, 1);

	    System.out.println(cal.getTime());


    }
}



