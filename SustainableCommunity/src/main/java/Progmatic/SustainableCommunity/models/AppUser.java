package Progmatic.SustainableCommunity.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter
@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 100, unique = true, nullable = false)
    private String username; //email
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime regTime;
    private String firstName;
    private String lastName;
    private Double userRating;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @OneToMany(mappedBy="owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Item> uploadItems = new ArrayList<>();
    @OneToMany(mappedBy="charterer",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Item> rentedItems = new ArrayList<>();

    private boolean enabled = false;
    private boolean locked = false;
    public AppUser() {

    this.userRole = UserRole.CUSTOMER;
    }

    public AppUser(String username, String email, String password) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // for testing, will be deleted -> test admin added
    public AppUser(String username, String email, String password, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getAuths();
        /*
          List<SimpleGrantedAuthority> list = new ArrayList<>();

        for (UserAuth auth : authority.AUTHS) {
            list.add(new SimpleGrantedAuthority(auth.toString()));
        }

        return list;
        * */
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}