package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Category;
import com.example.demo.model.Clothe;

@Mapper
public interface ClotheMapper {
	
	public int insertOne(Clothe clothe);
	
	public List<Clothe> findMany(Clothe clothe);
	
	public Clothe findOne(String clotheId);
	
	public String getMaxId();
	
	public String getMinId();
	
	public void updateOne(Clothe clothe);
	
	public int deleteOne(@Param("clotheId") String clotheId);
	
	public List<Category> getAllCategories();
	
	public Category getOneCategory(@Param("categoryId") String categoryId);
	
	public List<Category> getAllCategoriesExceptOne(@Param("excludedId") String excludedId);
}
