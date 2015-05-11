package cache.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Exceptions.IncorrectOrderException;
import Exceptions.SQLWorkException;
import Items.http.request.OrderItem;
import Items.http.response.MenuItem;
import Items.http.response.PriceItem;
import Items.http.response.ToppingItem;
import SQL.SqlFunctions;

public class CacheMenu {
	private static final Logger log = LogManager.getLogger(CacheMenu.class);

	private static HashMap<String, MenuItem> menuByArticle = null;
	private static ArrayList<MenuItem> menu = null;
	private static HashMap<Integer, LinkedList<MenuItem>> menuByCategory = null;

	public static void update() throws SQLWorkException {
		menu = SqlFunctions.getMenu();
		log.info("Cache menuByCategory is updated");

		menuByArticle = new HashMap<String, MenuItem>();
		for (MenuItem item : menu) {
			menuByArticle.put(item.article, item);
		}
		updateCategorys();
	}

	static {
		try {
			update();
		} catch (SQLWorkException e) {
			log.fatal("can't initialize menu in cach");
		}
	}

	public static ArrayList<MenuItem> getMenu() throws SQLWorkException {
		if (menu != null) {
			//log.info("Меню загружено из кеша");
		} else {
			update();
			//log.info("Меню загружена из БД");
		}
		return menu;
	}

	public static LinkedList<MenuItem> getMenuByCategory(int category) throws SQLWorkException {
		if (menuByCategory == null) {
			menu = SqlFunctions.getMenu();
			updateCategorys();
		}
		if (menuByCategory.containsKey(category)) {
			//log.info("Меню по категории " + category + " возвращено из кеша");
			return menuByCategory.get(category);
		} else {
			return new LinkedList<MenuItem>();
		}
	}

	//Запускать только после обновления переменной menu!
	private static void updateCategorys() {
		menuByCategory = new HashMap<Integer, LinkedList<MenuItem>>();
		for (MenuItem menuItem : menu) {
			if (menuByCategory.containsKey(menuItem.category)) {
				menuByCategory.get(menuItem.category).add(menuItem);
			} else {
				LinkedList<MenuItem> tempArr = new LinkedList<MenuItem>();
				tempArr.add(menuItem);
				menuByCategory.put(menuItem.category, tempArr);
			}
		}
		log.info("Cache menu is updated");
	}

	public static double getCost(ArrayList<OrderItem> orders) throws SQLWorkException, IncorrectOrderException {
		if (menuByArticle == null)
			update();
		double cost = 0.0;
		for (OrderItem orderItem : orders) {
			double orderItemCost = 0.0;
			String article = String.valueOf(orderItem.article);
			MenuItem menuItem = menuByArticle.get(article);

			//подсчет стоимости товара
			boolean foundFlag = false;
			for (PriceItem priceItem : menuItem.prices) {
				if (priceItem.size == orderItem.size) {
					orderItemCost +=priceItem.price;
					foundFlag = true;
					break;
				}
			}
			if (foundFlag == false) throw new IncorrectOrderException(
					"I don't now size \"" + orderItem.size + "\"",
					orderItem);


			//подсчет стоимости топпингов

			for (int orderToppingId : orderItem.toppingsIds) {
				foundFlag = false;
				for (ToppingItem menuToppingItem : menuItem.toppings) {
					if (menuToppingItem.ID == orderToppingId) {
						orderItemCost += menuToppingItem.price;
						foundFlag = true;
						break;
					}
					if (foundFlag == false) throw new IncorrectOrderException(
							"I don't new topping with id \"" + orderToppingId + "\"",
							orderItem);
				}
			}

			orderItemCost *= orderItem.count;
			cost += orderItemCost;
		}
		return cost;
	}
}
