package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {
	
	@RequestMapping(value = "/getProducts", method = RequestMethod.GET)
	public @ResponseBody List<Product> getProducts() {
		return FileOperations.getAllProducts();
	}

	@RequestMapping(value = "/getWishlist", method = RequestMethod.GET)
	public @ResponseBody List<Product> getWishlist(@RequestParam String username) {
		return FileOperations.getWishlist(username);
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public @ResponseBody boolean addProduct(@RequestBody Product prod) {
		return FileOperations.addProduct(prod);
	}
	
	@RequestMapping(value = "/markAsSold", method = RequestMethod.GET)
	public @ResponseBody boolean markAsSold(@RequestParam String id) {
		return FileOperations.markAsSold(id);
	}
	
	@RequestMapping(value = "/addToWishlist", method = RequestMethod.GET)
	public @ResponseBody boolean addToWishlist(@RequestParam("id") String id, @RequestParam("username") String username) {
		return FileOperations.addToWishlist(id, username);
	}
	
	@RequestMapping(value = "/validateUser", method = RequestMethod.POST)
	public @ResponseBody boolean validateUser(@RequestBody User user) {
		return FileOperations.getUser(user.getUsername()) != null;
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public @ResponseBody boolean addUser(@RequestBody User user) {
		return FileOperations.addUser(user);
	}
	
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public @ResponseBody User getUser(@RequestParam String username) {
		return FileOperations.getUser(username);
	}
	
}
