package com.alten.shop.service.Impl;

import com.alten.shop.dto.request.ProductReqDto;
import com.alten.shop.dto.response.ProductResDto;
import com.alten.shop.entities.Product;
import com.alten.shop.exceptions.EntityAlreadyExistException;
import com.alten.shop.exceptions.FieldNotFoundException;
import com.alten.shop.mappers.request.ProductReqMapper;
import com.alten.shop.mappers.response.ProductResMapper;
import com.alten.shop.repository.ProductRepository;
import com.alten.shop.service.ProductService;
import com.alten.shop.utils.ProductUpdateUtil;
import com.alten.shop.validators.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class ProductServiceImpl implements ProductService {

    static Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    ProductRepository productRepository;
    ProductReqMapper productReqMapper;
    ProductResMapper productResMapper;
    ObjectValidator<ProductReqDto> productValidator;


    /**
     * Retrieve all products.
     * @return List of ProductResDto representing all products.
     */
    @Override
    public List<ProductResDto> getAllProducts() {
        LOGGER.info("Retrieving all products");
        List<Product> products = productRepository.findAll();
        LOGGER.debug("Found {} products", products.size());
        return products.stream()
                .map(productResMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a product by ID.
     * @param id The ID of the product to retrieve.
     * @return ProductResDto representing the retrieved product.
     * @throws EntityNotFoundException if the product with the specified ID is not found.
     */
    @Override
    public ProductResDto getProductById(Long id) {
        LOGGER.info("Retrieving product with ID: {}", id);
        Product product = getProductOrThrowNotFound(id);
        LOGGER.debug("Found product: {}", product);
        return productResMapper.toDto(product);
    }

    /**
     * Create a new product.
     * @param requestDto ProductReqDto containing information for the new product.
     * @return ProductResDto representing the created product.
     */
    @Override
    public ProductResDto createProduct(ProductReqDto requestDto) {
        LOGGER.info("Creating a new product");
        productValidator.validate(requestDto);

        productRepository.findByCode(requestDto.getCode()).ifPresent(product -> {
            LOGGER.warn("Product with code {} already exists", requestDto.getCode());
            throw new EntityAlreadyExistException("Product already exists");
        });

        Product product = productReqMapper.toEntity(requestDto);
        LOGGER.debug("Saving product: {}", product);
        Product savedProduct = productRepository.save(product);
        LOGGER.info("Product created successfully");
        return productResMapper.toDto(savedProduct);
    }

    /**
     * Updates a Product entity with the specified non-null fields from the provided map.
     *
     * @param id     The identifier of the product to be updated.
     * @param fields A map containing field names and their corresponding values for update.
     * @return A ProductResDto representing the updated product.
     * @throws EntityNotFoundException If the product with the specified ID is not found.
     * @throws FieldNotFoundException  If any of the provided fields are not valid for the Product entity.
     */
    public ProductResDto updateProduct(Long id, Map<String, Object> fields) {
        // Retrieve the existing product or throw an exception if not found
        Product existingProduct = getProductOrThrowNotFound(id);

        // Validate that the provided fields are valid for the Product entity
        ProductUpdateUtil.validateFields(fields);

        // Update non-null fields using custom method
        ProductUpdateUtil.updateNonNullFields(existingProduct, fields);

        // Validate the mapped ProductReqDto
        productValidator.validate(productReqMapper.toDto(existingProduct));

        // Save the updated product to the database and map it to a ProductResDto
        LOGGER.debug("Saving updated product: {}", existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        LOGGER.info("Product updated successfully");
        return productResMapper.toDto(updatedProduct);
    }

    /**
     * Delete a product by ID.
     * @param id The ID of the product to delete.
     * @throws EntityNotFoundException if the product with the specified ID is not found.
     */
    @Override
    public void deleteProduct(Long id) {
        LOGGER.info("Deleting product with ID: {}", id);
        Product product = getProductOrThrowNotFound(id);
        LOGGER.debug("Deleting product: {}", product);
        productRepository.delete(product);
        LOGGER.info("Product deleted successfully");
    }

    /**
     * Helper method to retrieve a product by ID or throw an exception if not found.
     * @param id The ID of the product to retrieve.
     * @return Product representing the retrieved product.
     * @throws EntityNotFoundException if the product with the specified ID is not found.
     */
    private Product getProductOrThrowNotFound(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Product not found with ID: {}", id);
                    return new EntityNotFoundException("Product not found with id: " + id);
                });
    }
}

