package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Clothe;
import com.example.demo.repository.ClotheMapper;
import com.example.demo.service.ClotheService;

@Service
public class ClotheServiceImpl implements ClotheService{
	
	@Autowired
	private ClotheMapper mapper;
	
	@Override
	public List<Clothe> getClothes() {
//		nullが帰ってきた場合は？
		List<Clothe> clothes = mapper.findMany();
		
		if(clothes.isEmpty()) {
			Clothe clothe = new Clothe();
			clothe.setClotheName("No Data");
			clothe.setClotheId("0");
			clothes.add(clothe);
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
	public void updateClotheOne(String clotheId, String clotheName) {
		mapper.updateOne(clotheId, clotheName);
		
	}

	@Override
	public void deleteClotheOne(String clotheId) {
		int count = mapper.deleteOne(clotheId);
		
	}
	
	
	
}
