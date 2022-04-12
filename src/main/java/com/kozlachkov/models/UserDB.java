package com.kozlachkov.models;
import javax.persistence.*;
import java.util.Set;

@Table(name= "usr")
@Entity
public class UserDB {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean active;

    @ElementCollection (targetClass = Roles.class, fetch= FetchType.EAGER )
    @CollectionTable (name = "user_roles", joinColumns = @JoinColumn(name="user_id"))
    //говорит, что Сет будет храниться в таблице, для которого мы не описывали Mapping
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    public Long getId() { return id;    }
    public void setId(Long id) {  this.id = id;    }
    public String getUsername() { return username;    }
    public void setUsername(String username) { this.username = username;    }
    public String getPassword() { return password;    }
    public void setPassword(String password) { this.password = password;   }
    public boolean isActive() {  return active;   }
    public void setActive(boolean active) {   this.active = active;    }
    public Set<Roles> getRoles() {    return roles;    }
    public void setRoles(Set<Roles> roles) {   this.roles = roles;    }
}
