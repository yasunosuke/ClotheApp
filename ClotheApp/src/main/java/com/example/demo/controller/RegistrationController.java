package com.example.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.ClotheRegistrationForm;
import com.example.demo.model.Clothe;
import com.example.demo.service.ClotheService;

@Controller
public class RegistrationController {
	
	@Autowired
	private ClotheService clotheService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/index/registration")
	public String getRegistration(@ModelAttribute ClotheRegistrationForm form) {
		return "clothe/registration";
	}
	
	@PostMapping("/index/registration")
	public String postClotheRegistrationForm(Model model, @ModelAttribute ClotheRegistrationForm form) {
		
		String registrationClotheId = clotheService.getRegistrationId();
		
		form.setClotheId(registrationClotheId);
		
		Clothe clothe = modelMapper.map(form, Clothe.class);
		
		clotheService.registerClotheOne(clothe);
		
		return "redirect:/index";
	}
}
