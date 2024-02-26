package com.vssoft.vspace.service.mapper;

import com.vssoft.vspace.domain.Product;
import com.vssoft.vspace.service.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface ProductMapper extends EntityMapper<ProductDTO, Product>{
}
