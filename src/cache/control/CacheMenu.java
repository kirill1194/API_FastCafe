package cache.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Items.MenuItem;
import SQL.SqlFunctions;

public class CacheMenu {
	private static final Logger log = LogManager.getLogger(CacheMenu.class);

	private static ArrayList<MenuItem> menu = null;
	private static HashMap<Integer, LinkedList<MenuItem>> menuByCategory = null;

	public static ArrayList<MenuItem> getMenu() {
		if (menu != null) {
			log.info("Меню загружено из кеша");
		} else {
			update();
			log.info("Меню загружена из БД");
		}
		return menu;
	}

	public static LinkedList<MenuItem> getMenuByCategory(int category) {
		if (menuByCategory == null) {
			menu = SqlFunctions.getMenu();
			updateCategorys();
		}
		if (menuByCategory.containsKey(category)) {
			log.info("Меню по категории " + category + " возвращено из кеша");
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
		log.info("Загружен новый кеш меню по категориям");
	}

	public static void update() {
		log.info("Загружен новый кеш меню");
		menu = SqlFunctions.getMenu();
		updateCategorys();
	}
}
