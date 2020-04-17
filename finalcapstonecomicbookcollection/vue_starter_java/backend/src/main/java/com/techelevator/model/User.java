package com.techelevator.model;

import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotBlank;

/**
 * User
 */
public class User {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Role is required")
    private String role;
    private long id;

    @NotBlank(message = "Password is required")
    private String password;
    private String confirmPassword;
    
    private String email;
    private int collectionCount;
    private int comicCount;
    private int rank;
    
    private boolean passwordMatching;
    
    private List<Collection> collections;


    @AssertTrue(message = "Passwords must match") 
    public boolean isPasswordMatching() {
        if (password != null) {
            return password.equals(confirmPassword);
        }
        return true;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(int collectionCount) {
		this.collectionCount = collectionCount;
	}

	public int getComicCount() {
		return comicCount;
	}

	public void setComicCount(int comicCount) {
		this.comicCount = comicCount;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
} 