package com.example.demo.controller;

import java.util.Base64;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.form.ClotheDetailForm;
import com.example.demo.form.ClotheSearchForm;
import com.example.demo.model.Category;
import com.example.demo.model.Clothe;
import com.example.demo.service.ClotheService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {
	
	@Autowired
	private ClotheService clotheService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/index")
	public String getClotheList(@ModelAttribute ClotheSearchForm form, Model model, @ModelAttribute("updatedClotheId") String updatedClotheId) {
		
//		すべてのclothesの行を取得
		Clothe c = modelMapper.map(form, Clothe.class);
		
		List<Clothe> clotheList = clotheService.getClothes(c);
		
		model.addAttribute("clotheList", clotheList);
		
//		見つかった服の数
		Integer clotheNum = clotheList.size();
		model.addAttribute("clotheNum", clotheNum.toString());
		
//		詳細画面の取得
		log.info(updatedClotheId + "updated");
		Clothe clothe;
		if(updatedClotheId.equals("")) {
			clothe = clotheService.getClotheOne(clotheService.getMinId());
		} else {
			clothe = clotheService.getClotheOne(updatedClotheId);
		}
		
		updatedClotheId = "";
		
//		Clothe clothe = clotheService.getClotheOne(clotheService.getMinId());
		
		byte[] imageBytes = clothe.getClotheImage();
		if(imageBytes != null) {
			String base64Data = Base64.getEncoder().encodeToString(imageBytes);
			model.addAttribute("clotheProfileImage", base64Data);
		}
		
		ClotheDetailForm clotheDetailForm = modelMapper.map(clothe, ClotheDetailForm.class);
		
		model.addAttribute("clotheDetailForm", clotheDetailForm);
		
		addAttributeForDropDownMenu(model, clotheDetailForm);
		
		
		
		
		
		return "layout/layout";
	}
		

//リストの詳細ボタンを押して詳細画面を表示する	
	@PostMapping("/index")
	public String getClotheDetail(@ModelAttribute ClotheSearchForm form, @RequestParam("id") String str, @RequestParam("searchWord") String searchWord, Model model) {
		
		Clothe clothe = clotheService.getClotheOne(str);
		
//      byteデータを変換して登録		
		byte[] imageBytes = clothe.getClotheImage();
		if(imageBytes != null) {
			String base64Data = Base64.getEncoder().encodeToString(imageBytes);
			model.addAttribute("clotheProfileImage", base64Data);
		}
		
		ClotheDetailForm clotheDetailForm = modelMapper.map(clothe, ClotheDetailForm.class);
		
		model.addAttribute("clotheDetailForm", clotheDetailForm);
		
		addAttributeForDropDownMenu(model, clotheDetailForm);
		
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
		
//		検索で見つかった服の数
		Integer clotheNum = clotheList.size();
		model.addAttribute("clotheNum", clotheNum.toString());

		model.addAttribute("clotheList", clotheList);
//		search form の検索ワード消去
//		form.setClotheName(null);
		
		return "layout/layout";
	}
	
	@PostMapping(value = "/index", params = "update")
	public String updateClotheOne(ClotheDetailForm form, Model model, @RequestParam("drop") String drop, RedirectAttributes redirectAttributes) {
		
//		log.info(form.toString());
//		log.info(drop + "drop");
		
//		まだよくわからない
		redirectAttributes.addFlashAttribute("updatedClotheId", form.getClotheId());
		
		if(!drop.equals(form.getCategoryId())) {
			form.setCategoryId(drop);
		}
		
		Clothe clothe = modelMapper.map(form, Clothe.class);
		
//		clotheService.updateClotheOne(form.getClotheId(), form.getClotheName(), form.getCategoryId(), form.);
		clotheService.updateClotheOne(clothe);
		
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
		
//		検索で見つかった服の数
		Integer clotheNum = clotheList.size();
		model.addAttribute("clotheNum", clotheNum.toString());
		
//		詳細画面のための取得
//		検索したあとの最小ID値の詳細画面取得
		Clothe clotheForDetail = clotheList.get(0);
		
		byte[] imageBytes = clotheForDetail.getClotheImage();
		if(imageBytes != null) {
			String base64Data = Base64.getEncoder().encodeToString(imageBytes);
			model.addAttribute("clotheProfileImage", base64Data);
		}
		
		/* Clothe c = clotheService.getClotheOne(clotheService.getMinId()); */

		ClotheDetailForm clotheDetailForm = modelMapper.map(clotheForDetail, ClotheDetailForm.class);

		model.addAttribute("clotheDetailForm", clotheDetailForm);
		
		addAttributeForDropDownMenu(model, clotheDetailForm);
		
		return "layout/layout";
	}

//drop down menu のための
	private void addAttributeForDropDownMenu(Model model, ClotheDetailForm clotheDetailForm) {

//		詳細画面に表示するprofileのcategoryIdとcategoryNameを取得してmodelに登録
		String profileCategoryId = clotheDetailForm.getCategoryId();
		Category profileCategory = clotheService.getOneCategory(profileCategoryId);
		model.addAttribute("profileCategory", profileCategory);

//		詳細画面に表示するprofile以外全てのcategoryのrowを取得してmodelに登録する
		List<Category> allCategories = clotheService.getAllCategoriesExceptOne(profileCategoryId);
		model.addAttribute("categoryList", allCategories);
	}
}
