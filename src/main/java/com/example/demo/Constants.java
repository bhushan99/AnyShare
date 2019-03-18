package com.example.demo;

import java.io.File;

public class Constants {
	
	public static final String dataDir = System.getProperty("user.dir") + File.separator 
			+ "src" + File.separator
			+ "main" + File.separator
			+ "data" + File.separator;
	public static final String productsData = "products.ser";
	public static final String userDataPrefix = dataDir + "user_";
	public static final String serializedFileExt = ".ser";
	public static final String wishListDataPrefix = dataDir + "wishlist_";
	public static final int maxDaysToStoreProduct = 30;
	
	public static final String imageDir = System.getProperty("user.dir") + File.separator 
			+ "src" + File.separator
			+ "main" + File.separator
			+ "resources" + File.separator
			+ "static" + File.separator
			+ "images" + File.separator;
}
