package com.valtech.spring.security.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.io.ByteArrayInputStream;

import org.apache.juli.DateFormatCache;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.valtech.spring.security.entity.CartLine;
import com.valtech.spring.security.entity.Orders;
import com.valtech.spring.security.entity.Products;
import com.valtech.spring.security.entity.User;
import com.valtech.spring.security.model.RegisterUserModel;
import com.valtech.spring.security.repo.CartLineRepo;
import com.valtech.spring.security.repo.OrderRepository;
import com.valtech.spring.security.repo.UserReopsitory;
import com.valtech.spring.security.service.CartLineService;
import com.valtech.spring.security.service.OrderService;
import com.valtech.spring.security.service.ProductServiceImpl;
import com.valtech.spring.security.service.UserDetailsService;
import com.valtech.spring.security.util.BillDownload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@Controller
public class UserController {
	
	@Autowired
	private UserDetailsService service;

	@Autowired
	private ProductServiceImpl productservice;

	int uid;

	
	@Autowired
	private CartLineService cartLineService;

	@Autowired
	private OrderService orderService;
	
	

	@GetMapping("/user/feedback/{id}")
	public String feedbacksubmit(@PathVariable("id") int id, Model model) {
		model.addAttribute("user", id);
		return "user/feedback";
	}



	@RequestMapping(value = "/user/pdfreport/", method = RequestMethod.GET,
	produces = MediaType.APPLICATION_PDF_VALUE)	
	public ResponseEntity<InputStreamResource> cartBill() {

	var cartLines = (List<CartLine>) cartLineService.findAll();

	
	
	ByteArrayInputStream bis = BillDownload.cartBill(cartLines);


	var headers = new HttpHeaders();
	headers.add("Content-Disposition", "inline; filename=cartBill.pdf");

	return ResponseEntity
		.ok()
		.headers(headers)
		.contentType(MediaType.APPLICATION_PDF)
		.body(new InputStreamResource(bis));
}




	@GetMapping("user/userhome/{id}")
	public String userhome(@PathVariable("id") int id, ModelMap model) {

		System.out.println(id);

		User u = service.getUsername(id);
		System.out.println(service.getUsername(id));
		model.addAttribute("add", u.getName());
		model.addAttribute("user", u.getId());
//		List<Products> p1=productservice.getAllProducts();
//		for(Products p:p1){
//			model.addAttribute(p);
//		}
		model.addAttribute("Products", productservice.getAllProducts());

		model.addAttribute("users", service.getAlluser());

		return "user/userhome";
	}
	/*
	 * If buyer/user wants to update the profile Navigate to updateprofile page.
	 */

	@GetMapping("/user/updateprofile/{id}")
	public String userUpdate(@PathVariable("id") int id, Model model) {
		model.addAttribute("user", service.getuser(id));
		return "/user/updateprofile";
	}
	/*
	 * Buyer/User can Update the profile.
	 */

	@PostMapping("/user/updateprofile/{id}")
	public String userUpdateInsert(@PathVariable("id") int id, @ModelAttribute User user, Model model) {
		System.out.println("SUCCESS");
		model.addAttribute("user", service.getuser(id));
		service.updateUser(user);

		return "redirect:/user/userhome/{id}";
	}
	/*
	 * Buyer/User can add the required products to the cart.
	 */

	@GetMapping("user/userhome/{id}/{prod_id}")
	public String userhome1(@PathVariable("id") int id, @PathVariable("prod_id") int prod_id, ModelMap model,
			CartLine cartLine) throws Exception {

		try {
			System.out.println(id);

			User u = service.getUsername(id);
			System.out.println(service.getUsername(id));

			model.addAttribute("add", u.getName());
			model.addAttribute("user", u.getId());

			model.addAttribute("Products", productservice.getAllProducts());

			model.addAttribute("users", service.getAlluser());

			Products p = productservice.getProduct(prod_id);

			int check_User_id = cartLineService.findUserIdAndProdId_onlyUserId(id, prod_id);

			int check_Prod_id = cartLineService.findUserIdAndProdId_onlyProdId(id, prod_id);

			CartLine check = cartLineService.findUserIdAndProdId(id, prod_id);

			if (check_User_id == id & check_Prod_id == prod_id) {

				CartLine c = new CartLine(prod_id, p.getProductName(), p.getPrice(), p.getUserid(), id);

				
				
			
				
				
			if(p.getQuantity()!=0){
				check.setQuantity(check.getQuantity() + 1);
				p.setQuantity(p.getQuantity()-1);
			}
			else{
				System.out.println(p.getProductName());
			}
				int a= p.getQuantity()-1;
				System.out.println(a);
				cartLineService.createCartLine(check);

			}

			model.addAttribute("cartLine", cartLineService.findAll());

		} catch (DataIntegrityViolationException e)

		{
			Products p = productservice.getProduct(prod_id);
			CartLine cart = cartLineService.findByProId(prod_id);

			cartLine.setId(cart.getId());
			cartLine.setProdid(prod_id);
			cartLine.setProductName(p.getProductName());
			cartLine.setPrice(p.getPrice());
			cartLine.setQuantity(cart.getQuantity());
			System.out.println(cartLine.getQuantity());

			cartLineService.createCartLine(cartLine);

		}

		catch (NullPointerException n) {
			Products p = productservice.getProduct(prod_id);

			CartLine c = new CartLine(prod_id, p.getProductName(), p.getPrice(), p.getUserid(), id);

			cartLine.setAdminIds(p.getUserid());
			cartLine.setUserid(id);

			c.setUserid(id);

			cartLineService.createCartLine(c);
		}

		return "user/userhome";
	}
	/*
	 * Buyer/User can view the cart items.
	 */

	@GetMapping("/user/cart/{id}")
	public String cart(ModelMap model, @PathVariable("id") int user_id) {
		model.addAttribute("user", service.getuser(user_id));
		if (cartLineService.getAllordersByuserid(user_id).size() == 0) {
			model.addAttribute("error", "Please add Items to cart");
		}
		model.addAttribute("cartLine", cartLineService.getAllordersByuserid(user_id));

		return "user/cart";
	}
	/*
	 * Buyer/User can delete the items added to the cart.
	 */

	@PostMapping("/user/cart/{id}/{userid}")

	public String DeleteCartLine(Model model, @PathVariable("id") int id, @PathVariable("userid") int user_id) {

		System.out.println("DELETING");
		
	CartLine c= cartLineService.findById(id);
		
	Products p=productservice.getProduct(c.getProdid());
	
	p.setQuantity(p.getQuantity()+c.getQuantity()-1);
	
	
	System.out.println();
		cartLineService.deleteCartLine(id);
		
		
		

		return "redirect:/user/cart/" + user_id;

	}
	/*
	 * If Buyer/User wish to place the order, it will navigate to payment page.
	 */

	@GetMapping("/user/payment/{id}")
	public String payment(ModelMap model, @PathVariable("id") int id) {
		System.out.println("TIME>>>>>>>>>>>"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy / hh:mm")));
		if (cartLineService.getAllordersByuserid(id).size() == 0) {
			model.addAttribute("error", "Please add Items to cart");
			return "redirect:/user/cart/" + id;
		}
		model.addAttribute("user", service.getuser(id));
		return "user/payment";
	}
	/*
	 * Buyer/User should enter the payment details .
	 */

	@PostMapping("/user/payment/{id}")

	public String SaveOrders(@PathVariable("id") int id) {

		ArrayList<Integer> cart_ids = cartLineService.findAllId(id);
		ArrayList<Integer> admin_ids = cartLineService.findAllAdminId(id);

		User user =service.getByid(id);
		
		Orders o = new Orders();
		o.setUser_id(id);
		o.setCartIds(cart_ids);
		o.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy / hh:mm")));
		
		o.setArea(user.getArea());
		
		o.setAdminIds(admin_ids);

		orderService.saveOrder(o);

		System.out.println("PAYMENT DONE ");

		return "redirect:/user/orderTracking/" + id;
	}
	/*
	 * Once the order placed, buyer/seller can track the order.
	 */

	@GetMapping("/user/orderTracking/{id}")
	public String order(ModelMap model, @PathVariable("id") int id) {

		model.addAttribute("user", service.getuser(id));

		model.addAttribute("cartLine", cartLineService.getAllordersByuserid(id));

		return "user/orderTracking";
	}
	/*
	 * Once the order is placed cart will be empty.
	 */

	@PostMapping("/user/orderTracking/{id}")
	public String feedback(ModelMap model, @PathVariable("id") int id) {

		cartLineService.EmptyCart(id);

		return "redirect:/user/feedback/" + id;
	}
	/*
	 * After order recieved buyer/user should provide feedback.
	 */





}
