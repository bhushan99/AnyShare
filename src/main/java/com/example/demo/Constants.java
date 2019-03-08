package com.example.demo;

import java.io.File;

public class Constants {
	
	public static final String dataDir = System.getProperty("user.dir") + File.separator 
			+ "src" + File.separator
			+ "main" + File.separator
			+ "data" + File.separator;
	public static final String productsData = "products.ser";
	public static final String userDataPrefix = dataDir + "user_";
	public static final String userDataSuffix = ".ser";
	public static final int maxDaysToStoreProduct = 30;
	
}
