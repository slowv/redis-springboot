package com.slowv.hibernatecache.service;

import com.slowv.hibernatecache.domain.Product;
import com.slowv.hibernatecache.service.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<ProductDTO> products(Pageable pageable);
    ProductDTO save(final ProductDTO productDTO);
    Optional<ProductDTO> findOne(final String id);
    void delete(final String id);
    Optional<ProductDTO> partialUpdate(final ProductDTO productDTO);
    ProductDTO update(final ProductDTO productDTO);
}
