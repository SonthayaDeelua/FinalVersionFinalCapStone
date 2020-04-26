package com.modelAndcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.modelAndcontroller.JDBCRegisterDao;
import com.modelAndcontroller.RegisterDao;
import com.modelAndcontroller.Register;



@Controller
public class RegisterController {
	
	@Autowired
	private RegisterDao registerDao; 
	
	
	
	@RequestMapping (path =  "/", method = RequestMethod.GET)
	public String showSignUpForm(Model modelHolder) {
		if (!modelHolder.containsAttribute("signUpAtt")) {
			modelHolder.addAttribute("signUpAtt", new Register());
		}
		
		return "index";
	}
	
	@RequestMapping (path = "/index", method = RequestMethod.POST)
	public String processSignUp (
			@Valid @ModelAttribute ("signUpAtt") Register register,
			BindingResult bindingResult,
			RedirectAttributes flash) {
				
		if (bindingResult.hasErrors()) {
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "signUpAtt",  bindingResult);
			flash.addFlashAttribute("signUpAtt", register);
			return "redirect:/";
		}
		registerDao.save(register);
		
		return "redirect:/confirmation";
	}
	
	@RequestMapping(path = "/confirmation", method = RequestMethod.GET)
	public String showConfirmationView() {
		return "confirmation";
	}

}
