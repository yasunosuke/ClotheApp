package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Clothe;

@Mapper
public interface ClotheMapper {
	
	public int insertOne(Clothe clothe);
	
	public List<Clothe> findMany();
	
	public Clothe findOne(String clotheId);
	
	public String getMaxId();
	
	public String getMinId();
	
	public void updateOne(@Param("clotheId") String clotheId, @Param("clotheName") String clotheName);
	
	public int deleteOne(@Param("clotheId") String clotheId);
}
