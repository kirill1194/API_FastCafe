package Functions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SMS {

	private static Logger log = LogManager.getLogger(SMS.class);
	public static int sendSMS(String number, String text) {
		//		try {
		//			return sendSMSRU(number, text);
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//			return 0;
		//		}
		log.info("sms. phone: " + number + " text: " + text);
		return 200;

	}

	private static int sendSMSRU(String number, String text) throws IOException {
		String USER_AGENT = "Mozilla/5.0";
		URL url = new URL("http://sms.ru/sms/send?api_id=003b47d7-19d8-c5c4-95e7-3faedd13e845&to=" + number + "&text=" + text);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		return responseCode;
	}

	public static void main(String... args) {
		System.out.println(sendSMS("79126696789","сообщение"));

	}
}
