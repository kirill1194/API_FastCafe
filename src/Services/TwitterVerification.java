package Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

public class TwitterVerification {
	private static Logger log = LogManager.getLogger(TwitterVerification.class);

	private static final String TWITTER_KEY = "hxe8CSM6EpLtKHZ2La4jie2YQ";
	private static final String TWITTER_SECRET = "hd6M3aOOQqh5W4QMbxts24tCczEiF4poUZUOrdGXcdvB3PfYiN";

	public static boolean verify(String token, long userId) {
		if (!token.contains("secret"))
			throw new StringIndexOutOfBoundsException();
		//token=3071626935-YFOEfXy61jExpt82228HD02V5DQHhQ9euhKhwQv,secret=ECENg1Qo2WTg9hknTSuO6QKihRDbHJMzhngBhP8c9KaJc
		String authToken=token.substring("token=".length(), token.indexOf(",secret="));
		String secret = token.substring(token.indexOf(",secret=")+",secret=".length());

		//		log.debug("authToken: " + authToken);
		//		log.debug("secret: " + secret);

		Twitter t = TwitterFactory.getSingleton();
		try {
			t.setOAuthConsumer(TWITTER_KEY, TWITTER_SECRET);
		} catch (IllegalStateException e) {}
		AccessToken a = new AccessToken(authToken, secret);
		t.setOAuthAccessToken(a);
		User u;
		try {
			u = t.verifyCredentials();
			if (u.getId() == userId)
				return true;
			else
				return false;
		} catch (TwitterException e) {
			return false;
		}
	}
}