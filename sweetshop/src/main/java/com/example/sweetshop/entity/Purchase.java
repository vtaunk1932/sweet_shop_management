package com.example.sweetshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor // Keep this
@AllArgsConstructor // Keep this
@ToString
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "sweet_id")
    private Sweet sweet;

    private Integer quantity;
    public Purchase(User user, Sweet sweet, Integer quantity) {
        this.user = user;
        this.sweet = sweet;
        this.quantity = quantity;
    }
}