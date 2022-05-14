package com.example.demo.form;

import java.sql.Blob;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.model.Storage;

import lombok.Data;

@Data
public class ClotheRegistrationForm {
	
	private byte[] clotheImage;
	
	private String clotheId;
	
	@NotBlank
	private String clotheName;
	
	private String categoryId;
	
	private String storageCode;

	@NotBlank
	private String storageName;

}
