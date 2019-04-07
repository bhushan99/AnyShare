package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

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
		
		products = (List<Product>)ob;
		return products;
	}
	
	public static boolean markAsSold(String id) {
		boolean found = false;
		for(Product prod : products) {
			if(prod.getId() == Integer.parseInt(id)) {
				prod.setStatus("SOLD");
				found = true;
				break;
			}
		}
		
		if(found)
			return writeFile(Constants.dataDir + Constants.productsData, products);
		return false;
	}
	
	public static boolean addProduct(Product prod) {
		if(products == null) {
			products = getAllProducts();
			if(products == null) {
				products = new ArrayList<Product>();
			}
		}
		
		prod.setId(products.size());
		prod.setStatus("UNSOLD");
		copyImages(prod);
		products.add(prod);
		
		return writeFile(Constants.dataDir + Constants.productsData, products);
	}
	
	public static boolean addToWishlist(String id, String username) {
		List<Product> currList = new ArrayList<Product>();
		Object ob = readFile(Constants.wishListDataPrefix + username + Constants.serializedFileExt);
		if(ob != null) {
			currList = (List<Product>)ob;
		}
		
		for(Product prod : products) {
			if(prod.getId() == Integer.parseInt(id)) {
				currList.add(prod);
				break;
			}
		}
		
		return writeFile(Constants.wishListDataPrefix + username + Constants.serializedFileExt, currList);	
	}
	
	public static List<Product> getWishlist(String username) {
		List<Product> currList = new ArrayList<Product>();
		Object ob = readFile(Constants.wishListDataPrefix + username + Constants.serializedFileExt);
		if(ob != null) {
			currList = (List<Product>)ob;
		}
		return currList;
	}
	
	private static void copyImages(Product prod) {
		List<String> images = new ArrayList<String>();
		int index = 0;
		
		for(String image : prod.getImages()) {
			OutputStream out = null;
			if(new File(image).exists()) {
				try {
					out = new FileOutputStream(new File(Constants.imageDir, 
							new Integer(prod.getId()).toString() + "_" + index + "." + FilenameUtils.getExtension(image)));
					Files.copy(new File(image).toPath(), out);
					
					images.add("images/" + new Integer(prod.getId()).toString() + "_" + index + "." + FilenameUtils.getExtension(image));
					index++;
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				finally {
					try {
						out.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		
		prod.setImages(images);
	}
	
	public static boolean addUser(User u) {
		return writeFile(Constants.userDataPrefix + u.getUsername() + Constants.serializedFileExt, u);
	}
	
	public static User getUser(String username, String password) {
		Object ob = readFile(Constants.userDataPrefix + username + Constants.serializedFileExt);
		if(ob == null) {
			return null;
		}
		
		User u = (User)ob;
		if(u.getPassword().equals(password))
			return u;
		return null;
	}
	
	public static User getUser(String username) {
		Object ob = readFile(Constants.userDataPrefix + username + Constants.serializedFileExt);
		if(ob == null) {
			return null;
		}
		
		return (User)ob;
	}
	
}
