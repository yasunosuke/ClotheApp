package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.form.ClotheRegistrationForm;
import com.example.demo.model.Category;
import com.example.demo.model.Clothe;
import com.example.demo.model.Storage;
import com.example.demo.service.ClotheService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RegistrationController {
	
	@Autowired
	private ClotheService clotheService;
	
	@Autowired
	private ModelMapper modelMapper;
	
		
	@GetMapping("/registration")
	public String getRegistration(@ModelAttribute ClotheRegistrationForm form, Model model) {
		
		//	drop down list のために　category を取得する	
		List<Category> categoryList = clotheService.getAllCategories();
		model.addAttribute("categoryList", categoryList);
		
		List<Storage> storageList = clotheService.getAllStorages();
		model.addAttribute("allStorages", storageList);
		
		return "clothe/registration";
	}
	
//  registration form に入力した値 database　に登録するためのメソッド
	@PostMapping("/registration")
	public String postClotheRegistrationForm(Model model, @ModelAttribute @Validated ClotheRegistrationForm form, 
			BindingResult bindingResult, @RequestParam("upload-clothe-image") MultipartFile uploadedImage,
			@RequestParam("drop-down-category-registration") String dropDownItem) {
		
//		入力チェック時の処理
		if(bindingResult.hasErrors()) {
			log.info("入力チェック結果エラー");
			return getRegistration(form, model);
		}
		
//		form に入力された画像をdatabaseに保存出来るように変換
		byte[] image;
		try {
			image = uploadedImage.getBytes();
			log.info("byte抽出済み");
		} catch (IOException e) {
			e.printStackTrace();
			log.info("getting bytes from image failed");
			return getRegistration(form, model);
		}
		
//		登録するIDを取得
		String registrationClotheId = clotheService.getRegistrationId();
		
//		storageCode を　database　insert するための処理
		String sentStorageName = form.getStorageName();
		/* storages にデータがあるかの問い合わせ */
		Storage storage = clotheService.getStorageOne(sentStorageName);
		
		if (storage != null) { /* 新たに登録する必要がない場合 / 上でデータがあった場合 */
			form.setStorageCode(storage.getStorageCode());
		} else {/* storages に新たに storageName, storageCode を登録 */
			String storageCode = clotheService.getStorageCodeForRegistration();
			storage = new Storage();
			storage.setStorageCode(storageCode);
			storage.setStorageName(sentStorageName);
			clotheService.registerStorageOne(storage);
			form.setStorageCode(storageCode);
		}
			
//		data　をセット
		form.setClotheId(registrationClotheId);
		form.setClotheImage(image);
		form.setCategoryId(dropDownItem);/* drop down で選択されたdataをset */
		
//		ClotheRegistrationForm型 を　Clothe　型に変換
		Clothe clothe = modelMapper.map(form, Clothe.class);
//		databaseに登録
		clotheService.registerClotheOne(clothe);
		
//		登録成功した際のメッセージ
//		redirectAttributes.addFlashAttribute("registeredSuccessMessage",
//				"You successfully uploaded " + file.getOriginalFilename() + "!");
		
		return "redirect:/";
	}
}		
		