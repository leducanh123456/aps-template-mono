package io.github.leducanh123456.mapper;

import io.github.leducanh123456.dto.TestDTO;
import io.github.leducanh123456.entity.TestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TestMapper {

    TestDTO toDto(TestEntity testEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    TestEntity toEntityCreate(TestDTO testDTO);

    List<TestDTO> toDtoList(List<TestEntity> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateTest(TestDTO testDTO, @MappingTarget TestEntity testEntity);
}
