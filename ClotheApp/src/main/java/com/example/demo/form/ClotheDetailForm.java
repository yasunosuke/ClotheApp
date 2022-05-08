package com.example.demo.form;

import java.util.Date;

import com.example.demo.model.Category;

import lombok.Data;

@Data
public class ClotheDetailForm {
	private byte[] clotheImage;
	private String clotheId;
	private String clotheName;
	private String categoryId;
	private String storage;
	private Date registeredDate;
}
