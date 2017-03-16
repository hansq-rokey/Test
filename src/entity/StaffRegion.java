package entity;

import java.util.Date;

public class StaffRegion {
    private int id;

    private int orderTag;

    private int invalid;

    private Date createdDatetime;

    private Integer grade;

    private int parent;

    private String name;

    private String content;

    private String code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderTag() {
		return orderTag;
	}

	public void setOrderTag(int orderTag) {
		this.orderTag = orderTag;
	}

	public int getInvalid() {
		return invalid;
	}

	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}

	public Date getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    
}