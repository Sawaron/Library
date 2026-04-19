package com.codeandpray.library.controller;

import com.codeandpray.library.entity.Fine;
import com.codeandpray.library.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fines")
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;

    @GetMapping
    public List<Fine> getAllFines() {
        return fineService.findAll();
    }

    @PostMapping
    public ResponseEntity<Fine> createFine(@RequestBody Fine fine) {
        return new ResponseEntity<>(fineService.save(fine), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fine> getFineById(@PathVariable Long id) {
        return ResponseEntity.ok(fineService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fine> updateFine(@PathVariable Long id, @RequestBody Fine fineDetails) {
        Fine fine = fineService.findById(id);

        fine.setAmount(fineDetails.getAmount());
        fine.setReason(fineDetails.getReason());
        fine.setStatus(fineDetails.getStatus());

        return ResponseEntity.ok(fineService.save(fine));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFine(@PathVariable Long id) {
        fineService.deleteById(id);
    }
    
}