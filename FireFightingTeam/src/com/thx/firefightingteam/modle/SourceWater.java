package com.thx.firefightingteam.modle;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "source_water")
public class SourceWater {

	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String name;// 名字
	@DatabaseField
	private double lon;// 经度
	@DatabaseField
	private double lat;// 纬度
	@DatabaseField
	private String description;// 描述
	@DatabaseField
	private String imagePath;// 图片路径
	@DatabaseField
	private int statu;// 水源状态
	@DatabaseField
	private Date lastUpdateTime;// 最后更新时间
	@DatabaseField
	private String badDescription;// 如果是坏的，怎么个坏法
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getStatu() {
		return statu;
	}

	public void setStatu(int statu) {
		this.statu = statu;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getBadDescription() {
		return badDescription;
	}

	public void setBadDescription(String badDescription) {
		this.badDescription = badDescription;
	}

}
