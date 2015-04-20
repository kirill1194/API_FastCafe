package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Exceptions.SQLWorkException;
import Functions.Functions;
import Functions.SMS;
import Items.CategoryItem;
import Items.MenuItem;
import Items.ProfileItem;
import Services.Consts;

public class SqlFunctions {

	private static Logger log = LogManager.getLogger(SqlFunctions.class);

	/**
	 * Проверка на свободность имени пользователя
	 * @param phone
	 * @return
	 */
	@Deprecated
	public static boolean isFreePhomeNumber(String phone) throws SQLWorkException {
		Connection connection = null;
		try {
			connection = SqlServices.getConnection();
			PreparedStatement  preparedStatement = connection.prepareStatement(
					"SELECT COUNT(*) FROM fastCafe.users where phone=\'?\'"
					);
			preparedStatement.setString(1, phone);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			int count = result.getInt(1);
			if (count == 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}
	}

	/**
	 * Создание нового пользователя
	 * @param String name
	 * @param String pass
	 * @return true - если удалось создать.
	 * @return false - если произошла ошибка
	 */
	@Deprecated
	public static boolean createNewUser(String phone, String pass, String name) throws SQLWorkException {
		Connection connection = null;
		try {
			connection = SqlServices.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO fastCafe.users (phone, pass, name, access_token, life_time)"
							+ " VALUES (?, ?, ?, NULL, NULL)"
					);
			preparedStatement.setString(1, phone);
			preparedStatement.setString(2, pass);
			preparedStatement.setString(3, name);

			int code = preparedStatement.executeUpdate();

			String newPass = Functions.getRandom(6);

			SMS.sendSMS(phone, "ваш пароль: "+newPass);

			return code == 1 ? true : false;
		}  catch (SQLException e) {
			if (e.getErrorCode() == 1062)
				return false;
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}

	}

	/**
	 * Аутентификация пользователя
	 * @param String name
	 * @param String pass
	 * @return Access Token
	 * @return NULL - если аутентификация не прошла
	 */
	@Deprecated
	public static String login(String phone, String pass) throws SQLWorkException{
		Connection connection = null;
		String newAccessToken = Functions.getAccessTokent(1234, pass);

		try {
			connection = SqlServices.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE fastCafe.users SET access_token=?, lifeTime=?" +
							" WHERE phon=? AND pass=?"
					);
			preparedStatement.setString(1, newAccessToken);
			preparedStatement.setLong(2, new java.util.Date ().getTime());
			preparedStatement.setString(3, phone);
			preparedStatement.setString(4, pass);

			int code = preparedStatement.executeUpdate();
			return code==1 ? newAccessToken : null;

		} catch (SQLException e) {
			//такого пользователя нет
			if (e.getErrorCode() == 1054)
				return null;
			if (e.getErrorCode() == 1062)
				log.error("collision in Db, USERS, access_token. access_token: " + newAccessToken);
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}
	}

	/**
	 * Проверка живости Access Token
	 * @param accessToken
	 * @return - количество секунд, сколько будет действителен access token
	 * @return - -1, если access token просрочен или его нет в базе
	 * @throws SQLWorkException: errorCode 401 - неверный Access Token - надо перелогиниться
	 */
	@Deprecated
	public static long lifeTimeAccessToken(String accessToken) throws SQLWorkException{
		Connection connection = null;
		try {
			connection = SqlServices.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT create_time FROM fastCafe.users where access_token=?"
					);
			preparedStatement.setString(1, accessToken);
			ResultSet resultSet = preparedStatement.executeQuery();

			Date create_time = null;
			if (resultSet.next()) {
				create_time = resultSet.getDate(1);

				if (resultSet.next()) {
					log.fatal("in DB exist 2 notes with equales access_token: " + accessToken);
				}
				long result = create_time.getTime() - new Date().getTime() + Consts.LIVE_TIME;
				result = result / 1000;

				if (result < 0)
					return -1;
				else
					return result;
			} else {
				return -1;
			}
		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}
	}

	/**
	 * Получение меню
	 * @return
	 * @throws SQLWorkException
	 */
	public static ArrayList<MenuItem> getMenu() throws SQLWorkException{
		Connection connection = SqlServices.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT * FROM menu inner join prices where menu.`ID` = prices.`menu_id`"
					);
			ResultSet resultSet = preparedStatement.executeQuery();

			HashMap<Integer, MenuItem> menu = new HashMap<Integer, MenuItem>();

			while(resultSet.next()) {
				Integer ID = resultSet.getInt("ID");
				if (menu.containsKey(ID)) {
					menu.get(ID).addPrice(resultSet.getInt("size"), resultSet.getDouble("price"));
				} else {
					MenuItem temp = new MenuItem(
							ID.toString(),
							resultSet.getString("name"),
							resultSet.getString("category"),
							resultSet.getString("img"),
							resultSet.getString("description")
							);
					temp.addPrice(resultSet.getInt("size"), resultSet.getDouble("price"));
					menu.put(ID, temp);
				}
			}

			//добавление к каждому элементу меню топпингов
			preparedStatement = connection.prepareStatement(
					"select topping_relationship.menu_id, topping_relationship.topping_id, toppings.name, toppings.price " +
							"FROM topping_relationship INNER JOIN toppings " +
							"WHERE topping_relationship.topping_id = toppings.id"
					);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Integer menuId = resultSet.getInt("menu_id");
				int toppingId = resultSet.getInt("topping_id");
				String name = resultSet.getString("name");
				double price = resultSet.getDouble("price");
				menu.get(menuId).addTopping(toppingId, name, price);

			}


			//кастиим Map to ArrayList
			//map -> values collection -> MunuItem[] -> List -> ArrayList
			ArrayList<MenuItem> result = new ArrayList<MenuItem>(Arrays.asList(menu.values().toArray(new MenuItem[menu.size()])));

			return result;
		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}

	}


	/**
	 * Получение версии меню
	 * @param accessToken
	 * @return int - версия меню
	 */
	@Deprecated
	public static int getMenuVersion() throws SQLWorkException{
		Connection connection = SqlServices.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select value from variables where name='menu_version'"
					);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("value");
			} else {
				log.fatal("in DB not exist menu_version");
				throw new SQLWorkException(-1);
			}
		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}
	}

	/**
	 * Получить часть меню по коду категории
	 * @param accessToken
	 * @return
	 */
	public static ArrayList<MenuItem> getMenuByCategoryOld(String category) throws SQLWorkException{

		Connection connection = SqlServices.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select * from menu where category=?"
					);
			preparedStatement.setString(1, category);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<MenuItem> result = new ArrayList<MenuItem>();
			while (resultSet.next()) {
				MenuItem temp = new MenuItem(
						resultSet.getString("ID"),
						resultSet.getString("name"),
						resultSet.getString("category"),
						resultSet.getString("img"),
						resultSet.getString("description"),
						resultSet.getDouble("price")
						);
				result.add(temp);
			}
			return result;
		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}

	}


	public static ArrayList<MenuItem> getMenuByCategory(int category) throws SQLWorkException{
		Connection connection = SqlServices.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT * FROM fastCafe.menu inner join fastCafe.prices where menu.`ID` = prices.`menu_id` and menu.`category` = ?"
					);
			preparedStatement.setInt(1, category);
			ResultSet resultSet = preparedStatement.executeQuery();

			HashMap<Integer, MenuItem> menu = new HashMap<Integer, MenuItem>();

			while(resultSet.next()) {
				Integer ID = resultSet.getInt("ID");
				if (menu.containsKey(ID)) {
					menu.get(ID).addPrice(resultSet.getInt("size"), resultSet.getDouble("price"));
				} else {
					MenuItem temp = new MenuItem(
							ID.toString(),
							resultSet.getString("name"),
							resultSet.getString("category"),
							resultSet.getString("img"),
							resultSet.getString("description")
							);
					temp.addPrice(resultSet.getInt("size"), resultSet.getDouble("price"));
					menu.put(ID, temp);
				}
			}

			//кастиим Map to ArrayList
			//map -> values collection -> MunuItem[] -> List -> ArrayList
			ArrayList<MenuItem> result = new ArrayList<MenuItem>(Arrays.asList(menu.values().toArray(new MenuItem[menu.size()])));

			return result;
		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}

	}




	/**
	 * Изменение имени пользователя
	 * @param accessToken
	 * @param phone
	 * @return
	 * @throws SQLWorkException: errorCode 401 - неверный Access Token - надо перелогиниться
	 */
	@Deprecated
	public static boolean setName(String accessToken, String name) throws SQLWorkException {
		Connection connection = null;
		try {
			connection = SqlServices.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE `fastCafe`.`users` SET `name`=? WHERE `access_token`=?"
					);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, accessToken);

			int code = preparedStatement.executeUpdate();

			return code==1 ? true : false;
		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}
	}

	/**
	 * Изменение пароля пользователя
	 * @param oldPass
	 * @param newPass
	 * @param accessToken
	 * @return
	 * @throws SQLWorkException: errorCode 401 - неверный Access Token - надо перелогиниться
	 */
	@Deprecated
	public static boolean changePass(String oldPass, String newPass, String accessToken) throws SQLWorkException {
		Connection connection = null;
		try {
			connection = SqlServices.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
					"UPDATE `fastCafe`.`users` SET `pass`=? WHERE `access_token`=? and `pass`=?"
					);
			preparedStatement.setString(1, newPass);
			preparedStatement.setString(2, accessToken);
			preparedStatement.setString(3, oldPass);
			int code = preparedStatement.executeUpdate();
			return code==1 ? true : false;
		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}
	}


	/**
	 * Восстановление пароля
	 * @param phone
	 * @return
	 * @throws SQLWorkException
	 */
	@Deprecated
	public static boolean resetPass(String phone) throws SQLWorkException {
		Connection connection = null;
		try {
			connection = SqlServices.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT pass from users where phone=?"
					);
			preparedStatement.setString(1, phone);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String pass = resultSet.getString("pass");
				SMS.sendSMS(phone, pass);
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}
	}

	/**
	 * Получение access token
	 * @param userID
	 * @param phone
	 * @return access_token если все хорошо и null, c если произошла какая-то ошибка
	 * @throws SQLWorkException
	 */
	public static String signIn(long userID, String phone) throws SQLWorkException {
		Connection connection = null;
		try {
			connection = SqlServices.getConnection();
			PreparedStatement preparedStatement =connection.prepareStatement(
					"select * from `users` where `userID` = ?"
					);
			System.out.println("1");
			preparedStatement.setLong(1, userID);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("2");
			if (resultSet.next()) {
				System.out.println(resultSet.getString(1));
				System.out.println(resultSet.getString(2));
				System.out.println(resultSet.getString(3));
				System.out.println("3");
				String newAccessToken = Functions.getAccessTokent(userID, phone);
				PreparedStatement updateAccess_token = connection.prepareStatement(
						"UPDATE `fastCafe`.`users` SET `access_token`=? WHERE `userID`=?;"
						);

				updateAccess_token.setString(1, newAccessToken);
				updateAccess_token.setLong(2, userID);

				int code = updateAccess_token.executeUpdate();
				if (code == 1) {
					return newAccessToken;
				}
				else {
					log.error("can't update db table users." +
							"\n userId: " + userID +
							"\n accessToken: " + newAccessToken +
							"\n phone: " + phone +
							"\n error code: " + code);
					return null;
				}
			} else {
				System.out.println("4");
				String newAccessToken = Functions.getAccessTokent(userID, phone);
				PreparedStatement insertNewUser = connection.prepareStatement(
						"INSERT INTO `fastCafe`.`users` (`userID`, `phone`, `access_token`) VALUES (?, ?, ?);"
						);
				System.out.println("5");
				insertNewUser.setLong(1, userID);
				insertNewUser.setString(2, phone);
				insertNewUser.setString(3, newAccessToken);
				System.out.println("6");
				int code = insertNewUser.executeUpdate();
				System.out.println("7");
				if (code == 1) {
					System.out.println(8);
					return newAccessToken;
				}
				else {
					log.error("can't insert in db in table users." +
							"\n userId: " + userID +
							"\n accessToken: " + newAccessToken +
							"\n phone: " + phone);
					return null;
				}
			}
		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}
	}


	public static ArrayList<CategoryItem> getCategories() throws SQLWorkException {
		Connection connection = SqlServices.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT * FROM fastCafe.categories;"
					);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<CategoryItem> result = new ArrayList<CategoryItem>();

			while(resultSet.next()) {
				CategoryItem item = new CategoryItem(resultSet.getInt("id"), resultSet.getString("name"));
				result.add(item);
			}

			return result;
		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}

	}


	public static ProfileItem getProfile(String access_token) throws SQLWorkException {
		Connection connection = SqlServices.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select `name`, `phone`, `sale` from `users` where `access_token`=?"
					);
			preparedStatement.setString(1, access_token);
			ResultSet resultSet = preparedStatement.executeQuery();
			ProfileItem profile = null;
			if (resultSet.next()) {
				profile = new ProfileItem(resultSet.getString("name"), resultSet.getString("phone"), resultSet.getDouble("sale"));
				if (resultSet.next())
					throw new SQLWorkException(500, "дублирующаяся запись в базе");
			}
			return profile;
		} catch (SQLException e) {
			throw new SQLWorkException(e);
		} finally {
			SqlServices.closeConnection(connection);
		}
	}
}

