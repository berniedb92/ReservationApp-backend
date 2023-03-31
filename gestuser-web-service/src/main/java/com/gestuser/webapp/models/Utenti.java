package com.gestuser.webapp.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class Utenti implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Size(min = 5, max = 80, message = "{Size.Utenti.userId.Validation}")
	@NotNull(message = "{NotNull.Articoli.userId.Validation}")
	@Column(name = "userid")
	private String userid;

	@Size(min = 8, max = 80, message = "{Size.Utenti.password.Validation}")
	@Column(name = "password")
	private String password;

	@Column(name = "attivo")
	private String attivo;

	@Column(name = "codicetessera")
	private Integer codicetessera;

	@NotNull(message = "{NotNull.Utenti.ruoli.Validation}")
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", 
		joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Ruoli> roles = new HashSet<>();

	public void addRole(Ruoli role) {
		this.roles.add(role);
	}

}