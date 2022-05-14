package com.example.demo.model;

import java.sql.Blob;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Clothe {
	private byte[] clotheImage;
	private String clotheId;
	private String clotheName;
	private String categoryId;
	private String storageCode;
	private Date registeredDate;
	private Category category; 
	private Storage storage;
}
