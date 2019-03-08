package com.example.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {
	
	private static List<Product> products = null;
	
	private static Object readFile(String file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Object ob = ois.readObject();
			ois.close();
			fis.close();
			return ob;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static boolean writeFile(String file, Object ob) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(ob);
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static List<Product> getAllProducts() {
		if(products != null) {
			return products;
		}
		
		Object ob = readFile(Constants.dataDir + Constants.productsData);
		if(ob == null) {
			return null;
		}
		
		return (List<Product>)ob;
	}
	
	public static boolean addProduct(Product prod) {
		if(products == null) {
			products = getAllProducts();
			if(products == null) {
				products = new ArrayList<Product>();
			}
		}
		
		prod.setId(products.size());
		products.add(prod);
		
		return writeFile(Constants.dataDir + Constants.productsData, products);
	}
	
	public static boolean addUser(User u) {
		return writeFile(Constants.userDataPrefix + u.getUsername() + Constants.userDataSuffix, u);
	}
	
	public static User getUser(String username) {
		Object ob = readFile(Constants.userDataPrefix + username + Constants.userDataSuffix);
		if(ob == null) {
			return null;
		}
		
		return (User)ob;
	}
	
}
