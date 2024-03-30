package com.paw.model;



import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "breed")
public class Breed {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;
    
    @OneToMany(mappedBy = "breed", cascade = CascadeType.MERGE)
    @JsonIgnore 
    private Set<Dog> dog;

	public Breed() {
		
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

	public Set<Dog> getDog() {
		return dog;
	}

	public void setDog(Set<Dog> dog) {
		this.dog = dog;
	}
	
    
    
    

    
}
