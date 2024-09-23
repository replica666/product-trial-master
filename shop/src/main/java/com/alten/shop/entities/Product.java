package com.alten.shop.entities;

import com.alten.shop.entities.shared.SharedEntity;
import com.alten.shop.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Product extends SharedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "product_code", unique = true, nullable = false)
    String code;

    @Column(name = "product_name", nullable = false)
    String name;

    @Column(name = "product_description", nullable = false, length = 1000)
    String description;

    @Column(name = "product_image")
    String image;

    @Column(name = "product_category", nullable = false)
    String category;

    @Column(name = "product_price", nullable = false)
    double price;

    @Column(name = "product_quantity", nullable = false)
    int quantity;

    @Column(name = "internal_reference", nullable = false, unique = true)
    String internalReference;

    @Column(name = "shell_id")
    Long shellId;

    @Enumerated(EnumType.STRING)
    @Column(name = "inventory_status", nullable = false)
    InventoryStatus inventoryStatus;

    @Column(name = "product_rating")
    int rating;
}
