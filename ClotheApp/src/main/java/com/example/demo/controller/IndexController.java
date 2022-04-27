package com.example.demo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.ClotheDetailForm;
import com.example.demo.form.ClotheSearchForm;
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
	public String getClotheList(@ModelAttribute ClotheSearchForm form,Model model) {
		
//		リストの取得
		Clothe c = modelMapper.map(form, Clothe.class);
		
		List<Clothe> clotheList = clotheService.getClothes(c);
		
		model.addAttribute("clotheList", clotheList);
		
//		詳細画面の取得
		Clothe clothe = clotheService.getClotheOne(clotheService.getMinId());
		
		ClotheDetailForm clotheDetailForm = modelMapper.map(clothe, ClotheDetailForm.class);
		
		model.addAttribute("clotheDetailForm", clotheDetailForm);
		
		return "layout/layout";
	}
		

//リストの詳細ボタンを押して詳細画面を表示する	
	@PostMapping("/index")
	public String getClotheDetail(@ModelAttribute ClotheSearchForm form, @RequestParam("id") String str, @RequestParam("searchWord") String searchWord, Model model) {
		
		Clothe clothe = clotheService.getClotheOne(str);
		
		ClotheDetailForm clotheDetailForm = modelMapper.map(clothe, ClotheDetailForm.class);
		
		model.addAttribute("clotheDetailForm", clotheDetailForm);
		
//		リストを取得　ここがおかしい
//		Clothe c = new Clothe();
//
//		List<Clothe> clotheList = clotheService.getClothes(c);
//		
//		model.addAttribute("clotheList", clotheList);
//		
//		ClotheSearchForm csf = new ClotheSearchForm();
//		
//		model.addAttribute("clotheSearchForm", csf);
		
//		検索をかけた後に二回連続detailボタン押したとき検索結果がリセットされてしまう
		
		form.setClotheName(searchWord);
		
		Clothe c = modelMapper.map(form, Clothe.class);

		List<Clothe> clotheList = clotheService.getClothes(c);

		model.addAttribute("clotheList", clotheList);
//		search form の検索ワード消去
//		form.setClotheName(null);
		
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
	
//	Clothes検索処理
	@PostMapping(value= "/index", params = "search")
	public String searchClotheList(@ModelAttribute ClotheSearchForm form, Model model) {
		
		Clothe clothe = modelMapper.map(form, Clothe.class);
		
		List<Clothe> clotheList = clotheService.getClothes(clothe);
		
		model.addAttribute("clotheList", clotheList);
		
//		詳細画面のための取得
//		検索したあとの最小ID値の詳細画面取得実装していない
		
		Clothe clotheForDetail = clotheList.get(0);
		/* Clothe c = clotheService.getClotheOne(clotheService.getMinId()); */

		ClotheDetailForm clotheDetailForm = modelMapper.map(clotheForDetail, ClotheDetailForm.class);

		model.addAttribute("clotheDetailForm", clotheDetailForm);
		
		return "layout/layout";
	}
	
//	@PostMapping("/index")
//	public String postRequest(@RequestParam("") String str, Model model) {
//		
//		model.addAttribute("sample", str);
//		
//		return "layout/layout";
//	}
}
