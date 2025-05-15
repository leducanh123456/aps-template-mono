package io.github.leducanh123456.controller;

import io.github.leducanh123456.dto.TestDTO;
import io.github.leducanh123456.dto.base.ResponseDTO;
import io.github.leducanh123456.dto.base.ResponseDataDTO;
import io.github.leducanh123456.service.TestService;
import io.github.leducanh123456.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping
    public ResponseEntity<ResponseDataDTO<TestDTO>> createUser(@RequestBody TestDTO testDTO) {
        return ResponseEntity.ok(ResponseUtil.ok(testService.createTest(testDTO)));
    }

    @GetMapping
    public ResponseEntity<ResponseDataDTO<List<TestDTO>>> getAllUsers() {
        return ResponseEntity.ok(ResponseUtil.ok(testService.getAllTest()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataDTO<TestDTO>> getTestById(@PathVariable UUID id) {
        return ResponseEntity.ok(ResponseUtil.ok(testService.getTestById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataDTO<TestDTO>> updateUser(@PathVariable UUID id, @RequestBody TestDTO testDTO) {
        return ResponseEntity.ok(ResponseUtil.ok(testService.updateTest(id, testDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable UUID id) {
        testService.deleteTest(id);
        return ResponseEntity.ok(ResponseUtil.ok());
    }
}
