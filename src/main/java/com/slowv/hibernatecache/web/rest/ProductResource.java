package com.slowv.hibernatecache.web.rest;

import com.slowv.hibernatecache.domain.Product;
import com.slowv.hibernatecache.service.ProductService;
import com.slowv.hibernatecache.service.dto.ProductDTO;
import com.slowv.hibernatecache.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductResource {
    private final ProductService productService;
    @PostMapping
    public ResponseEntity<List<ProductDTO>> products(Pageable pageable) {
        log.debug("REST request to get a page of products");
        final var page = productService.products(pageable);
        final var headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/save")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        log.debug("REST request to save Product : {}", productDTO);

        final var result = productService.save(productDTO);
        return ResponseEntity
                .created(URI.create("/api/products/%s".formatted(result.getId())))
                .body(result);
    }
}
