package com.kozlachkov.models;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

/*@Table(name= "usr")*/
//@Entity
public class UserDB {
    /*@Id
    @GeneratedValue (strategy = GenerationType.AUTO)*/
    private int id;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max=15, message = "not less 2 and not more 15 symbols")
    private String username;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max=25, message = "not less 2 and not more 25 symbols")
    private String password;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max=25, message = "not less 2 and not more 25 symbols")
    private String check_pass;
    private Roles role;
    private boolean active;
/*
    @ElementCollection (targetClass = Roles.class, fetch= FetchType.EAGER )
    @CollectionTable (name = "user_roles", joinColumns = @JoinColumn(name="user_id"))
    //говорит, что Сет будет храниться в таблице, для которого мы не описывали Mapping
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;
*/
    public int getId() { return id;    }
    public void setId(int id) {  this.id = id;    }
    public String getUsername() { return username;    }
    public void setUsername(String username) { this.username = username;    }
    public String getPassword() { return password;    }
    public void setPassword(String password) { this.password = password;   }
    public boolean isActive() {  return active;   }
    public void setActive(boolean active) {   this.active = active;    }
/*    public Set<Roles> getRoles() {    return roles;    }
    public void setRoles(Set<Roles> roles) {   this.roles = roles;    }*/
    public String getCheck_pass() {  return check_pass;   }
    public void setCheck_pass(String check_pass) {  this.check_pass = check_pass; }
    public Roles getRole() {  return role; }
    public void setRole(Roles role) {   this.role = role;  }

}
