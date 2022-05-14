package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Category;
import com.example.demo.model.Clothe;
import com.example.demo.model.Storage;

public interface ClotheService {
	
	public List<Clothe> getClothes(Clothe clothe);
	
	public Clothe getClotheOne(String clotheId);
	
	public void registerClotheOne(Clothe clothe);
	
	public String getRegistrationId();/* name変更 */
	
	public String getMinId();
	
	public void updateClotheOne(Clothe clothe);
	
	public void deleteClotheOne(String clotheId);
	
	public List<Category> getAllCategories();
	
	public Category getOneCategory(String profileCategoryId);
	
	public List<Category> getAllCategoriesExceptOne(String excludedId);
	
	public List<Storage> getAllStorages();
	
	public Storage getStorageOne(String storageName);
	
	public String getStorageCodeForRegistration();
	
	public void registerStorageOne(Storage storage);
}
