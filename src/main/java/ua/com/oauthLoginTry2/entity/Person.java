package ua.com.oauthLoginTry2.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pId;
	
	private String name;
	
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="person")
	private CustomUserDetails userDetails;

	public Person() {
		super();
	}
	
	public void setRole(Role role) {
		this.roles.add(role);
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public CustomUserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(CustomUserDetails userDetails) {
		userDetails.setPerson(this);
		this.userDetails = userDetails;
	}

	@Override
	public String toString() {
		return "Person [pId=" + pId + ", name=" + name + ", email=" + email + ", password=" + password + ", roles="
				+ roles + "]";
	}
}
