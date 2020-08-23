package com.example.res.server.mapper;

import com.example.res.server.dto.AbstractDto;
import com.example.res.server.entity.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);
}