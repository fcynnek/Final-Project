package com.fcynnek.finalproject.petmanagement.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String name;
	private String breed;
	private Integer age;
	private String color;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User user;
	@OneToMany(mappedBy = "animal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Medication> meds = new ArrayList<>();
	enum species {
		DOG,
		CAT,
		BIRD,
		REPTILE,
		FISH
	}
	
	
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Medication> getMeds() {
		return meds;
	}
	
	public void setMeds(List<Medication> meds) {
		this.meds = meds;
	}
	
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
	
	public String getBreed() {
		return breed;
	}
	
	public void setBreed(String breed) {
		this.breed = breed;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
}
