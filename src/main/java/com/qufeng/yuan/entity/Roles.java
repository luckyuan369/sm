package com.qufeng.yuan.entity;

public class Roles implements java.io.Serializable {
	
	private static final long serialVersionUID = 7554196238197170672L;
	
	private Integer id;
	private Integer enable;//是否禁用角色　1　表示禁用　2　表示不禁用
	private String name;
	private String roleKey;
	private String description;
	
	public Roles() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEnable() {
		return this.enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

}