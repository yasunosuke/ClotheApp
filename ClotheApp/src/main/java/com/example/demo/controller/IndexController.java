package com.example.demo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.ClotheDetailForm;
import com.example.demo.model.Clothe;
import com.example.demo.service.ClotheService;

@Controller
public class IndexController {
	
	@Autowired
	private ClotheService clotheService;
	
	@Autowired
	private ModelMapper modelMapper;
	
//	@GetMapping("/index")
//	public String getIndex() {
//		return "layout/layout";
//	}
	
	@GetMapping("/index")
	public String getClotheList(Model model) {
		
		List<Clothe> clotheList = clotheService.getClothes();
		
		model.addAttribute("clotheList", clotheList);
		
		Clothe clothe = clotheService.getClotheOne(clotheService.getMinId());
		
		ClotheDetailForm clotheDetailForm = modelMapper.map(clothe, ClotheDetailForm.class);
		
		model.addAttribute("clotheDetailForm", clotheDetailForm);
		
		return "layout/layout";
	}
		

	
	@PostMapping("/index")
	public String getClotheDetail(@RequestParam("id") String str, Model model) {
		
		Clothe clothe = clotheService.getClotheOne(str);
		
		ClotheDetailForm clotheDetailForm = modelMapper.map(clothe, ClotheDetailForm.class);
		
		model.addAttribute("clotheDetailForm", clotheDetailForm);
		
		List<Clothe> clotheList = clotheService.getClothes();
		
		model.addAttribute("clotheList", clotheList);
		
		return "layout/layout";
	}
	
	@PostMapping(value = "/index", params = "update")
	public String updateClotheOne(ClotheDetailForm form, Model model) {
		
		clotheService.updateClotheOne(form.getClotheId(), form.getClotheName());
		
		return "redirect:/index";
	}
	
	@PostMapping(value = "/index", params = "delete")
	public String deleteClothe(ClotheDetailForm form, Model model) {
		
		clotheService.deleteClotheOne(form.getClotheId());
		
		return "redirect:/index";
	}
	
//	@PostMapping("/index")
//	public String postRequest(@RequestParam("") String str, Model model) {
//		
//		model.addAttribute("sample", str);
//		
//		return "layout/layout";
//	}
}
