package com.paw.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "dog")
@Data
@Builder
@AllArgsConstructor

public class Dog {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "colour")
    private String colour;

    @Column(name = "description")
    private String description;

    @Column(name = "location", nullable = true)
    private String location;
    
    @Column(name="type")
    private String type;
    @Column(name="file_path")
    private String filePath;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "breed_id")
    @JsonIgnore 
    private Breed breed;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    @JsonIgnore 
    private User user;

    @OneToOne(mappedBy = "dog", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnore 
    private Adoption adoption;

	public Dog() {

	}

	public Dog(String name, LocalDate birthDate, String gender, String colour, String description, String location,
			String type,String filePath) {
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
		this.colour = colour;
		this.description = description;
		this.location = location;
		this.type=type;
		this.filePath=filePath;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Breed getBreed() {
		return breed;
	}

	public void setBreed(Breed breed) {
		this.breed = breed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Adoption getAdoption() {
		return adoption;
	}

	public void setAdoption(Adoption adoption) {
		this.adoption = adoption;
	}


	
	
    
    

    
    
}
