package com.example.demo.form;

import java.util.Date;

import com.example.demo.model.Category;
import com.example.demo.model.Storage;

import lombok.Data;

@Data
public class ClotheDetailForm {
//	private byte[] clotheImage;
	private String clotheId;
	private String clotheName;
	private String categoryId;
	private String storageCode;
	private Date registeredDate;
	private Category category;
	private Storage storage;
}
