package com.shop.goods.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * brand实体类
 * @author 黑马架构师2.5
 *
 */
@ApiModel(description = "Brand",value = "Brand")
@Table(name="tb_brand")
public class Brand implements Serializable {

	@Id
	@Column(name = "id")
	@ApiModelProperty(value = "品牌ID",required = false)
	private Integer id;//品牌id

	@Column(name = "name")
	@ApiModelProperty(value = "品牌名称",required = false)
	private String name;//品牌名称

	@Column(name = "image")
	@ApiModelProperty(value = "品牌图片地址",required = false)
	private String image;//品牌图片地址

	@Column(name = "letter")
	@ApiModelProperty(value = "品牌的首字母",required = false)
	private String letter;//品牌的首字母


	@Column(name = "seq")
	@ApiModelProperty(value = "排序",required = false)
	private Integer seq;//排序

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}



}
