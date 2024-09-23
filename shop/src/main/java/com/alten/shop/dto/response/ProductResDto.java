package com.alten.shop.dto.response;

import com.alten.shop.enums.InventoryStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResDto {

    Long id;
    String code;
    String name;
    String description;
    String image;
    String category;
    double price;
    int quantity;
    String internalReference;
    Long shellId;
    InventoryStatus inventoryStatus;
    int rating;
    Instant createdAt;
    Instant updatedAt;
}
