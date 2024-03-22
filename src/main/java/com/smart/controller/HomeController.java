package com.smart.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;	

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	/*
	 * @Autowired private UserRepository userRepository;
	 * 
	 * @GetMapping("/test")
	 * 
	 * @ResponseBody public String test() {
	 * 
	 * User user= new User(); user.setName("Shrijan Goswami");
	 * user.setEmail("shrijangiri1999@gamil.com");
	 * 
	 * userRepository.save(user); return "Working"; }
	 */
	
	/* Home Handler */    
	@RequestMapping("/")
	public String Home(Model model)
	{
		
		model.addAttribute("title","Home - Smart Contact Manager");
		return "home";
	}

	/* About Handler */
	 @RequestMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title","About-Smart Contact Manager");
		return "about";
	}

	/* Snap Handler */
	 @RequestMapping("/signup")
		public String signup(Model model)
		{
			model.addAttribute("title","Register-Smart Contact Manager");
			model.addAttribute("user",new User());
			return "signup";
		}
	 
	 
		/* This handler for registering user */
	 @PostMapping(value="/do_register")
//	 @RequestMapping(value = "/do_register",method = RequestMethod.POST)
	 public String registerUser(@ModelAttribute("user")User user, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {
		    try {
		        if (!agreement) {
		            System.out.println("You have not agreed to the terms and conditions");
		            throw new Exception("You have not agreed to the terms and conditions");
		        }
		        
		        user.setRole("ROLE_USER");
		        user.setEnabled(true);
		        user.setImageUrl("default.png");
		        
		        System.out.println("Agreement: " + agreement);
		        System.out.println("User: " + user);
		        
		        @SuppressWarnings("unused")
				User result = this.userRepository.save(user);
		        
		        model.addAttribute("user", new User());
		        session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
		        return "signup";
		        
		    } catch (Exception e) {
		        model.addAttribute("user", user);
		        session.setAttribute("message", new Message("Something went wrong !! " + e.getMessage(), "alert-danger"));
		        return "signup";
		    }            
		}
}
