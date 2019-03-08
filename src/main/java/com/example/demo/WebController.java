package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {
	
	@RequestMapping(value = "/getProducts", method = RequestMethod.GET)
	public @ResponseBody List<Product> getProducts() {
		return FileOperations.getAllProducts();
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public @ResponseBody boolean addProduct(@RequestBody Product prod) {
		System.out.println(prod.toString());
		return FileOperations.addProduct(prod);
	}
	
	@RequestMapping(value = "/validateUser", method = RequestMethod.POST)
	public @ResponseBody boolean validateUser(@RequestBody User user) {
		System.out.println(user.toString());
		return FileOperations.getUser(user.getUsername()) != null;
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public @ResponseBody boolean addUser(@RequestBody User user) {
		System.out.println(user.toString());
		return FileOperations.addUser(user);
	}
	
}
