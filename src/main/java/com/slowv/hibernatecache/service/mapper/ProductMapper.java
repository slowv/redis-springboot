package com.slowv.hibernatecache.service.mapper;

import com.slowv.hibernatecache.domain.Product;
import com.slowv.hibernatecache.service.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface ProductMapper extends EntityMapper<ProductDTO, Product>{
}
