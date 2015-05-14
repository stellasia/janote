package com.janote.model.entities;

public class Exam {

	protected Integer id;
	protected String name;
	protected String description;
	protected float coefficient;
	
	
	public Exam() {};
	
	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param coefficient
	 */
	public Exam(Integer id, String name, String description, float coefficient) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.coefficient = coefficient;
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
	
	
}
