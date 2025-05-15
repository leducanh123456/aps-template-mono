package io.github.leducanh123456.service.impl;

import io.github.leducanh123456.dto.TestDTO;
import io.github.leducanh123456.mapper.TestMapper;
import io.github.leducanh123456.repository.TestRepository;
import io.github.leducanh123456.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestMapper testMapper;

    @Override
    public TestDTO createTest(TestDTO testDTO) {
        var testEntity = testMapper.toEntityCreate(testDTO);
        testDTO.setId(testRepository.save(testEntity).getId());
        return testDTO;
    }

    @Override
    public List<TestDTO> getAllTest() {
        var testEntities = testRepository.findAll();
        return testMapper.toDtoList(testEntities);
    }

    @Override
    public TestDTO getTestById(UUID id) {
        var testEntity = testRepository.findById(id).orElseThrow();
        return testMapper.toDto(testEntity);
    }

    @Override
    public TestDTO updateTest(UUID id, TestDTO testDTO) {
        var testEntity = testRepository.findById(id).orElseThrow();
        testMapper.updateTest(testDTO, testEntity);
        testRepository.save(testEntity);
        return testMapper.toDto(testEntity);
    }

    @Override
    public void deleteTest(UUID id) {
        var user = testRepository.findById(id).orElseThrow();
        testRepository.delete(user);
    }
}
