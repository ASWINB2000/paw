package com.paw.model;

import java.time.LocalDate;

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

@Entity
@Table(name = "fostering")
public class Fostering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "fosterer_name", nullable = false) 
    private String fostererName;
    
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "adopter_id", referencedColumnName = "id", nullable = false)
    private Adopter adopter;

    @OneToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "dog_id", nullable = false, unique = true)
    private Dog dog;

    public Fostering(LocalDate startDate, LocalDate endDate, Adopter adopter, Dog dog) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.adopter = adopter;
        this.dog = dog;
    }
    

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
    

    public String getFostererName() {
		return fostererName;
	}


	public void setFostererName(String fostererName) {
		this.fostererName = fostererName;
	}


	public Fostering() {

    }
    
}
