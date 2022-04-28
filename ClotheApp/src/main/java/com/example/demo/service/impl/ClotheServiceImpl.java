package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Category;
import com.example.demo.model.Clothe;
import com.example.demo.repository.ClotheMapper;
import com.example.demo.service.ClotheService;

@Service
public class ClotheServiceImpl implements ClotheService{
	
	@Autowired
	private ClotheMapper mapper;
	
	@Override
	public List<Clothe> getClothes(Clothe clothe) {
//		nullが帰ってきた場合は？
		List<Clothe> clothes = mapper.findMany(clothe);
		
		if(clothes.isEmpty()) {
			Clothe c = new Clothe();
			c.setClotheName("No Data");
			c.setClotheId("0");
			clothes.add(c);
			return clothes;
		} else {
			return clothes;
		}
		
	}
	
	@Override
	public Clothe getClotheOne(String clotheId) {
//		nullが帰ってきた場合は？
//		if()
//		unknownの場合は何が変える？
		Clothe temp = mapper.findOne(clotheId);
		
		if(temp == null) {
			Clothe clothe = new Clothe();
			clothe.setClotheName("No Data");
			clothe.setClotheId("0");
			return clothe;
		} else {
			return temp;
		}
	}

	@Override
	public void registerClotheOne(Clothe clothe) {
		mapper.insertOne(clothe);
	}

	@Override
	public String getRegistrationId() {
//		nullが帰ってきた場合は？
		String temp = mapper.getMaxId();
		
		if(temp == null) {
			return "1";
		} else {
			Integer intLatestClotheId = Integer.parseInt(temp) + 1;
			String stringLatestClotheId = intLatestClotheId.toString();
			return stringLatestClotheId;
		}
		
	}
	
	@Override
	public String getMinId() {
		return mapper.getMinId();
	}

	@Override
	public void updateClotheOne(Clothe clothe) {
		mapper.updateOne(clothe);
		
	}

	@Override
	public void deleteClotheOne(String clotheId) {
		int count = mapper.deleteOne(clotheId);
		
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return mapper.getAllCategories();
	}

	@Override
	public Category getOneCategory(String categoryId) {
		// TODO Auto-generated method stub
		return mapper.getOneCategory(categoryId);
	}

	@Override
	public List<Category> getAllCategoriesExceptOne(String excludedId) {
		// TODO Auto-generated method stub
		return mapper.getAllCategoriesExceptOne(excludedId);
	}
	
	
	
	
	
}
