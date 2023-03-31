package com.gestuser.webapp.models;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Ruoli implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = false, length = 45)
	private String name;

	public Ruoli() {
	}

	public Ruoli(String name) {
		this.name = name;
	}

	public Ruoli(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Ruoli(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
