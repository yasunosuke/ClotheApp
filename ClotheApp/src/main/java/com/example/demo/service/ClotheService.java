package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Clothe;

public interface ClotheService {
	
	public List<Clothe> getClothes();
	
	public Clothe getClotheOne(String clotheId);
	
	public void registerClotheOne(Clothe clothe);
	
	public String getRegistrationId();/* name変更 */
	
	public String getMinId();
	
	public void updateClotheOne(String clotheId, String clotheName);
	
	public void deleteClotheOne(String clotheId);
}
