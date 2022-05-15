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
import com.example.demo.model.Storage;
import com.example.demo.service.ClotheService;
import com.example.demo.utility.ImageUtility;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {
	
	@Autowired
	private ClotheService clotheService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ImageUtility imageUtility;
	
	@GetMapping("/")
	public String getClotheList(@ModelAttribute ClotheSearchForm form, Model model, @ModelAttribute("updatedClotheId") String updatedClotheId) {
		
//		すべてのclothesの行を取得
		List<Clothe> clotheList = clotheService.getClothes(modelMapper.map(form, Clothe.class));
		model.addAttribute("clotheList", clotheList);
		
//		見つかったClotheの数
		String clotheNum = getFoundItemNumber(clotheList);
		model.addAttribute("clotheNum", clotheNum);

//		詳細画面(clothe profile画面)のdata取得
//		log.info(updatedClotheId + "updated");
		Clothe clothe;
		if(updatedClotheId.equals("")) {
			clothe = clotheService.getClotheOne(clotheService.getMinId()); 
//			clothe = clotheList.get(0);
		} else {
			clothe = clotheService.getClotheOne(updatedClotheId);/* update後 */
		}
//		updatedClotheId = "";
		
//		詳細画面(clothe profile画面)のdataを登録
		ClotheDetailForm clotheDetailForm = modelMapper.map(clothe, ClotheDetailForm.class);
		model.addAttribute("clotheDetailForm", clotheDetailForm);
		
		model.addAttribute("categoryList", getAllCategoriesExcept(clotheDetailForm.getCategoryId()));
		
//		addAttributeForDropDownMenu(model, clotheDetailForm.getCategoryId()); 
		
		model.addAttribute("clotheProfileImage", imageUtility.encodeClotheImage(clothe));
		
		model.addAttribute("allStorages", getAllStorages());
		
		return "layout/layout";
	}
		
		
//  リストの詳細ボタンを押して詳細画面を表示するためのメソッド
	@PostMapping(value = "/", params = "detail")
	public String getClotheDetail(@ModelAttribute ClotheSearchForm form, @RequestParam("id") String str, @RequestParam("searchWord") String searchWord, Model model) {
		
//		detail button　を押したリストの行の data を取得、登録
		Clothe clothe = clotheService.getClotheOne(str);
		ClotheDetailForm clotheDetailForm = modelMapper.map(clothe, ClotheDetailForm.class);
		model.addAttribute("clotheDetailForm", clotheDetailForm);
		
		model.addAttribute("clotheProfileImage", imageUtility.encodeClotheImage(clothe));
		
//		詳細画面の　drop down list　のためのすべての　category　取得、登録
		model.addAttribute("categoryList", getAllCategoriesExcept(clotheDetailForm.getCategoryId()));
//		addAttributeForDropDownMenu(model, clotheDetailForm.getCategoryId());
		
//      detail button 押下後も押下前の検索状態を維持するための処理
		form.setClotheName(searchWord);
		List<Clothe> clotheList = getClothes(modelMapper.map(form, Clothe.class));
		model.addAttribute("clotheList", clotheList);
		
//		検索で見つかった服の数取得、登録(list 上部に表示)
		model.addAttribute("clotheNum", getFoundItemNumber(clotheList));
		
		model.addAttribute("allStorages", getAllStorages());
		
//		search form の検索ワード消去
//		form.setClotheName(null);
		
		return "layout/layout";
	}
	
	
//	詳細画面のUPDATE BUTTON　押下後の更新処理のためのメソッド
	@PostMapping(value = "/", params = "update")
	public String updateClotheOne(ClotheDetailForm form, Model model, @RequestParam("drop") String drop, RedirectAttributes redirectAttributes) {
		
//		更新したrowのclotheIdをgetClotheList（）に渡して詳細画面に表示するため登録(UPDATE後も同じ詳細画面にとどまるため)
		redirectAttributes.addFlashAttribute("updatedClotheId", form.getClotheId());
		
//		category変更のための処理
		if (!drop.equals(form.getCategoryId())) {/* categoryを変更していた場合 */
			form.setCategoryId(drop);
		}
		
//		storageName変更のための処理
		String sentStorageName = form.getStorage().getStorageName();
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
			
		
		Clothe clothe = modelMapper.map(form, Clothe.class);
		clotheService.updateClotheOne(clothe);
		
		return "redirect:/";
	}
	
	
//	詳細画面の　DELETE BUTTON　押下後削除処理のためのメソッド
	@PostMapping(value = "/", params = "delete")
	public String deleteClothe(ClotheDetailForm form, Model model) {
		
		clotheService.deleteClotheOne(form.getClotheId());
		
		return "redirect:/";
	}
	
	
//	リストの検索処理のためのメソッド
	@PostMapping(value= "/", params = "search")
	public String searchClotheList(@ModelAttribute ClotheSearchForm form, Model model) {
		
//		
		Clothe clothe = modelMapper.map(form, Clothe.class);
		List<Clothe> clotheList = getClothes(clothe);
		model.addAttribute("clotheList", clotheList);
		
//		検索で見つかった服の数取得、登録
		String clotheNum = getFoundItemNumber(clotheList);
		model.addAttribute("clotheNum", clotheNum);
		
//		検索後、先頭の詳細画面取得
		Clothe clotheForDetail = clotheList.get(0);
		ClotheDetailForm clotheDetailForm = modelMapper.map(clotheForDetail, ClotheDetailForm.class);
		model.addAttribute("clotheDetailForm", clotheDetailForm);
		
//		詳細画面の　drop down list　のためのすべての　category　取得、登録
		model.addAttribute("categoryList", getAllCategoriesExcept(clotheDetailForm.getCategoryId()));
//		addAttributeForDropDownMenu(model, clotheDetailForm.getCategoryId());
		
//		image取得、エンコード(clotheListのClotheにはimageが含まれていないため取得する)
		Clothe clotheForEncode = clotheService.getClotheOne(clotheForDetail.getClotheId());

		model.addAttribute("clotheProfileImage", imageUtility.encodeClotheImage(clotheForEncode));
		
		model.addAttribute("allStorages", getAllStorages());
		
		return "layout/layout";
	}
	
	
////	詳細画面の　drop down list　のための　categoryName　取得、登録のためのメソッド
//	private void addAttributeForDropDownMenu(Model model, String detailCategoryId) {
//		
////		詳細画面に表示するprofile以外全てのcategoryのrowを取得してmodelに登録する
//		List<Category> allCategoriesExceptOne = clotheService.getAllCategoriesExceptOne(detailCategoryId);
//		model.addAttribute("categoryList", allCategoriesExceptOne);
//	}
	
	private List<Category> getAllCategoriesExcept(String categoryId) {
		return clotheService.getAllCategoriesExceptOne(categoryId);
	}
	
	
//	見つかったClotheの数を取得するためのメソッド	
	private String getFoundItemNumber(List<Clothe> clotheList) {
		
		if(clotheList.get(0).getClotheId().equals("0")) {
			return "0";
		} else {
			Integer clotheNum = clotheList.size();
			return clotheNum.toString();
		}
	}
	
	private List<Storage> getAllStorages() {
		return clotheService.getAllStorages();
	}
	
	private List<Clothe> getClothes(Clothe clothe) {
		return clotheService.getClothes(clothe);
	}
	
}