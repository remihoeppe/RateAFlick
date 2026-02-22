package com.example.demo.controllers;

import com.example.demo.dto.common.PageResponse;
import com.example.demo.dto.director.DirectorListResponse;
import com.example.demo.dto.director.DirectorResponse;
import com.example.demo.services.DirectorService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/directors")
@Validated
public class DirectorController {
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<DirectorListResponse>> getAllDirectors(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(directorService.findAllDirectors(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponse> getDirectorById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(directorService.findDirectorById(id));
    }
}
