package com.ynov.louis_de_lavenne_de_choulot_de_chabaudlatour.models;

import org.hibernate.annotations.ManyToAny;
import org.springframework.security.core.userdetails.UserDetails;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @Nullable
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;
    
    private String description;
    @Min(value = 1, message = "Price must be at least 1")
    private Double price;
    @Min(value = 0, message = "Quantity must be at least 0")
    private int quantity;
}
