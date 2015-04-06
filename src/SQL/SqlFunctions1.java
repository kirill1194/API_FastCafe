//package SQL;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import Exceptions.SQLWorkException;
//import Functions.Functions;
//import Functions.SMS;
//import Items.MenuItem;
//import Items.ProfileItem;
//import Services.Consts;
//
//public class SqlFunctions1 {
//
//	private static Logger log = LogManager.getLogger(SqlFunctions1.class);
//
//	/**
//	 * Проверка на свободность имени пользователя
//	 * @param phone
//	 * @return
//	 */
//	public static boolean isFreePhomeNumber(String phone) throws SQLWorkException {
//		Connection connection = null;
//		try {
//			connection = SqlServices.getConnection();
//			PreparedStatement  preparedStatement = connection.prepareStatement(
//					"SELECT COUNT(*) FROM fastCafe.users where phone=\'?\'"
//					);
//			preparedStatement.setString(1, phone);
//			ResultSet result = preparedStatement.executeQuery();
//			result.next();
//			int count = result.getInt(1);
//			if (count == 0)
//				return true;
//			else
//				return false;
//
//		} catch (SQLException e) {
//			throw new SQLWorkException(e);
//		} finally {
//			SqlServices.closeConnection(connection);
//		}
//	}
//
//	/**
//	 * Создание нового пользователя
//	 * @param String name
//	 * @param String pass
//	 * @return true - если удалось создать.
//	 * @return false - если произошла ошибка
//	 */
//	public static boolean createNewUser(String phone, String pass, String name) throws SQLWorkException {
//		Connection connection = null;
//		try {
//			connection = SqlServices.getConnection();
//			PreparedStatement preparedStatement = connection.prepareStatement(
//					"INSERT INTO fastCafe.users (phone, pass, name, access_token, life_time)"
//							+ " VALUES (?, ?, ?, NULL, NULL)"
//					);
//			preparedStatement.setString(1, phone);
//			preparedStatement.setString(2, pass);
//			preparedStatement.setString(3, name);
//
//			preparedStatement.executeUpdate();
//
//			String newPass = Functions.getRandom(6);
//
//			SMS.sendSMS(phone, "ваш пароль: "+newPass);
//
//			return true;
//		}  catch (SQLException e) {
//			if (e.getErrorCode() == 1062)
//				return false;
//			throw new SQLWorkException(e);
//		} finally {
//			SqlServices.closeConnection(connection);
//		}
//
//	}
//
//	/**
//	 * Аутентификация пользователя
//	 * @param String name
//	 * @param String pass
//	 * @return Access Token
//	 * @return NULL - если аутентификация не прошла
//	 */
//	public static String login(String phone, String pass) throws SQLWorkException{
//		Connection connection = null;
//		String newAccessToken = Functions.getAccessTokent(1234, pass);
//
//		try {
//			connection = SqlServices.getConnection();
//			PreparedStatement preparedStatement = connection.prepareStatement(
//					"UPDATE fastCafe.users SET access_token=?, lifeTime=?" +
//							" WHERE phon=? AND pass=?"
//					);
//			preparedStatement.setString(1, newAccessToken);
//			preparedStatement.setLong(2, new java.util.Date ().getTime());
//			preparedStatement.setString(3, phone);
//			preparedStatement.setString(4, pass);
//
//			preparedStatement.executeUpdate();
//			return newAccessToken;
//		} catch (SQLException e) {
//			//такого пользователя нет
//			if (e.getErrorCode() == 1054)
//				return null;
//			if (e.getErrorCode() == 1062)
//				log.error("collision in Db, USERS, access_token. access_token: " + newAccessToken);
//			throw new SQLWorkException(e);
//		} finally {
//			SqlServices.closeConnection(connection);
//		}
//	}
//
//	/**
//	 * Проверка живости Access Token
//	 * @param accessToken
//	 * @return - количество секунд, сколько будет действителен access token
//	 * @return - -1, если access token просрочен или его нет в базе
//	 * @throws SQLWorkException: errorCode 401 - неверный Access Token - надо перелогиниться
//	 */
//	public static long lifeTimeAccessToken(String accessToken) throws SQLWorkException{
//		Connection connection = null;
//		try {
//			connection = SqlServices.getConnection();
//			PreparedStatement preparedStatement = connection.prepareStatement(
//					"SELECT life_time FROM fastCafe.users where access_token=?"
//					);
//			preparedStatement.setString(1, accessToken);
//			ResultSet resultSet = preparedStatement.executeQuery();
//			long lifeTime = -1;
//			if (resultSet.next()) {
//				lifeTime = resultSet.getLong(1);
//				if (resultSet.next()) {
//					log.fatal("in DB exist 2 notes with equales access_token: " + accessToken);
//				}
//				return lifeTime-(new Date().getTime())+Consts.LIVE_TIME;
//			} else {
//				return -1;
//			}
//		} catch (SQLException e) {
//			throw new SQLWorkException(e);
//		} finally {
//			SqlServices.closeConnection(connection);
//		}
//	}
//
//	/**
//	 * Получение меню
//	 * @return
//	 * @throws SQLWorkException
//	 */
//	public static ArrayList<MenuItem> getMenu() throws SQLWorkException{
//		Connection connection = SqlServices.getConnection();
//		try {
//			PreparedStatement preparedStatement = connection.prepareStatement("select * from menu");
//			ResultSet resultSet = preparedStatement.executeQuery();
//			ArrayList<MenuItem> result = new ArrayList<MenuItem>();
//			while(resultSet.next()) {
//				MenuItem temp = new MenuItem(resultSet.getString("ID"), resultSet.getString("name"),
//						resultSet.getString("category"), resultSet.getString("img"),
//						resultSet.getString("description"), resultSet.getDouble("price")
//						);
//				result.add(temp);
//			}
//			return result;
//		} catch (SQLException e) {
//			throw new SQLWorkException(e);
//		} finally {
//			SqlServices.closeConnection(connection);
//		}
//
//	}
//
//	/**
//	 * Получение версии меню
//	 * @param accessToken
//	 * @return int - версия меню
//	 */
//	public static int getMenuVersion() throws SQLWorkException{
//		Connection connection = SqlServices.getConnection();
//		try {
//			PreparedStatement preparedStatement = connection.prepareStatement(
//					"select value from variables where name='menu_version'"
//					);
//			ResultSet resultSet = preparedStatement.executeQuery();
//			if (resultSet.next()) {
//				return resultSet.getInt("value");
//			} else {
//				log.fatal("in DB not exist menu_version");
//				throw new SQLWorkException(-1);
//			}
//		} catch (SQLException e) {
//			throw new SQLWorkException(e);
//		} finally {
//			SqlServices.closeConnection(connection);
//		}
//	}
//
//	/**
//	 * Получить часть меню по коду категории
//	 * @param accessToken
//	 * @return
//	 */
//	public static ArrayList<MenuItem> getMenuByCategory(String category) throws SQLWorkException{
//
//		Connection connection = SqlServices.getConnection();
//		try {
//			PreparedStatement preparedStatement = connection.prepareStatement(
//					"select * from menu where category=?"
//					);
//			preparedStatement.setString(1, category);
//			ResultSet resultSet = preparedStatement.executeQuery();
//			ArrayList<MenuItem> result = new ArrayList<MenuItem>();
//			while (resultSet.next()) {
//				MenuItem temp = new MenuItem(
//						resultSet.getString("ID"),
//						resultSet.getString("name"),
//						resultSet.getString("category"),
//						resultSet.getString("img"),
//						resultSet.getString("description"),
//						resultSet.getDouble("price")
//						);
//				result.add(temp);
//			}
//			return result;
//		} catch (SQLException e) {
//			throw new SQLWorkException(e);
//		} finally {
//			SqlServices.closeConnection(connection);
//		}
//
//	}
//
//	/**
//	 * Получение профиля пользователя
//	 * @param accessToken - String
//	 * @return ProfileItem - профиль
//	 * @throws SQLWorkException: errorCode 401 - неверный Access Token - надо перелогиниться
//	 *
//	 */
//	public static ProfileItem getProfile(String accessToken) throws SQLWorkException {
//		Connection connection = SqlServices.getConnection();
//		try {
//			PreparedStatement preparedStatement = connection.prepareStatement(
//					"select name, phone from users where access_token=?"
//					);
//			ResultSet resultSet = preparedStatement.executeQuery();
//			ProfileItem profileItem = null;
//			if (resultSet.next()) {
//				profileItem = new ProfileItem(
//						resultSet.getString("name"),
//						resultSet.getString("phone"),
//						0.0 //ДОБАВИТЬ СКИДКУ!
//						);
//			}
//			if (resultSet.next()) {
//				log.fatal("in db in USERS exist 2 note with equals access_token = " + accessToken);
//			}
//			return profileItem;
//		} catch (SQLException e) {
//			throw new SQLWorkException(e);
//		} finally {
//			SqlServices.closeConnection(connection);
//		}
//	}
//
//	/**
//	 * Изменение имени пользователя
//	 * @param accessToken
//	 * @param phone
//	 * @return
//	 * @throws SQLWorkException: errorCode 401 - неверный Access Token - надо перелогиниться
//	 */
//	public static boolean setName(String accessToken, String name) throws SQLWorkException {
//		Connection connection = null;
//		try {
//			connection = SqlServices.getConnection();
//			PreparedStatement preparedStatement = connection.prepareStatement(
//					"UPDATE `fastCafe`.`users` SET `name`=? WHERE `access_token`=?"
//					);
//			preparedStatement.setString(1, name);
//			preparedStatement.setString(2, accessToken);
//
//			int code = preparedStatement.executeUpdate();
//
//			if (code == 1)
//				return true;
//			else
//				return false;
//		} catch (SQLException e) {
//			throw new SQLWorkException(e);
//		} finally {
//			SqlServices.closeConnection(connection);
//		}
//	}
//
//	/**
//	 * Изменение пароля пользователя
//	 * @param oldPass
//	 * @param newPass
//	 * @param accessToken
//	 * @return
//	 * @throws SQLWorkException: errorCode 401 - неверный Access Token - надо перелогиниться
//	 */
//	public static boolean changePass(String oldPass, String newPass, String accessToken) throws SQLWorkException {
//		Connection connection = null;
//		try {
//			connection = SqlServices.getConnection();
//			PreparedStatement preparedStatement = connection.prepareStatement(
//					"UPDATE `fastCafe`.`users` SET `pass`=? WHERE `access_token`=? and `pass`=?"
//					);
//			preparedStatement.setString(1, newPass);
//			preparedStatement.setString(2, accessToken);
//			preparedStatement.setString(3, oldPass);
//			int code = preparedStatement.executeUpdate();
//			return code==1 ? true : false;
//		} catch (SQLException e) {
//			throw new SQLWorkException(e);
//		} finally {
//			SqlServices.closeConnection(connection);
//		}
//	}
//
//
//	/**
//	 * Восстановление пароля
//	 * @param phone
//	 * @return
//	 * @throws SQLWorkException
//	 */
//	public static boolean resetPass(String phone) throws SQLWorkException {
//		Connection connection = null;
//		try {
//			connection = SqlServices.getConnection();
//			PreparedStatement preparedStatement = connection.prepareStatement(
//					"SELECT pass from users where phone=?"
//					);
//			preparedStatement.setString(1, phone);
//			ResultSet resultSet = preparedStatement.executeQuery();
//			if (resultSet.next()) {
//				String pass = resultSet.getString("pass");
//				SMS.sendSMS(phone, pass);
//				return true;
//			} else {
//				return false;
//			}
//		} catch (SQLException e) {
//			throw new SQLWorkException(e);
//		} finally {
//			SqlServices.closeConnection(connection);
//		}
//	}
//}
//
