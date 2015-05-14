package com.janote.model.entities;

public class Exam {

	protected Integer id;
	protected String name;
	protected String description;
	protected float coefficient;
	protected Integer group_id;
	
	public Exam() {};
	
	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param coefficient
	 */
	public Exam(Integer id, String name, String description, float coefficient, Integer group_id) {
		super();
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		this.setCoefficient(coefficient);
		this.setGroup_id(group_id);
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the coefficient
	 */
	public float getCoefficient() {
		return coefficient;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param coefficient the coefficient to set
	 */
	public void setCoefficient(float coefficient) {
		this.coefficient = coefficient;
	}

	/**
	 * @return the group_id
	 */
	public Integer getGroup_id() {
		return group_id;
	}

	/**
	 * @param group_id the group_id to set
	 */
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	
	@Override
	public String toString() {
		final int maxLen = 10;
		return "Exam [id=" + id + ", name=" + name + ", description="
				+ description + ", coefficent=" + coefficient
				+ "] ";
	}
}
