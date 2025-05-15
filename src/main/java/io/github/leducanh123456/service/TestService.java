package io.github.leducanh123456.service;

import io.github.leducanh123456.dto.TestDTO;

import java.util.List;
import java.util.UUID;

public interface TestService {
    TestDTO createTest(TestDTO testDTO);

    List<TestDTO> getAllTest();

    TestDTO getTestById(UUID id);

    TestDTO updateTest(UUID id, TestDTO testDTO);

    void deleteTest(UUID id);
}
