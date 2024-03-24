package com.paw.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name="adoption")
public class Adoption {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message="Adoption Date is required")
	@Column(name="adoption_on",nullable=false)
	private LocalDateTime adoptionDate;
	
	@ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "adopter_id", referencedColumnName = "id", nullable = false)
	private Adopter adopter;
	
	@OneToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "dog_id", nullable = false, unique = true)
    private Dog dog;

	 public Adoption(LocalDateTime adoptionDate, Adopter adopter, Dog dog) {
         this.adoptionDate = adoptionDate;
         this.adopter = adopter;
         this.dog = dog;
	 }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getAdoptionDate() {
		return adoptionDate;
	}

	public void setAdoptionDate(LocalDateTime adoptionDate) {
		this.adoptionDate = adoptionDate;
	}

	public Adopter getAdopter() {
		return adopter;
	}

	public void setAdopter(Adopter adopter) {
		this.adopter = adopter;
	}

	public Dog getDog() {
		return dog;
	}

	public void setDog(Dog dog) {
		this.dog = dog;
	}
	
	
}
