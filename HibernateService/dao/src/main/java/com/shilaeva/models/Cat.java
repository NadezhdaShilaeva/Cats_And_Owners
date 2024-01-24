package com.shilaeva.models;

import java.util.*;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate", nullable = false)
    private Date birthdate;

    @Column(name = "breed")
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false)
    private CatColor color;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "cat_friends",
            joinColumns = @JoinColumn(name = "first_cat_id"),
            inverseJoinColumns = @JoinColumn(name = "second_cat_id"))
    private Set<Cat> friends = new HashSet<Cat>();

    public Cat(String name, Date birthdate, CatColor color) {
        this.name = name;
        this.birthdate = birthdate;
        this.color = color;
    }

    @PreRemove
    public void beforeRemoveHandler() {
        if (owner != null) {
            owner.getCats().remove(this);
        }

        friends.forEach(f -> f.getFriends().remove(this));
    }
}
