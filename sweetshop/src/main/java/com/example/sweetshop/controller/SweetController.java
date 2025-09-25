package com.example.sweetshop.controller;

import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.service.SweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

    private final SweetService sweetService;

    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    @PostMapping("/add")
    public ResponseEntity<Sweet> addSweet(@RequestBody Sweet sweet) {
        // Mistake: return null instead of saving
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<List<Sweet>> getAllSweets() {
        // Mistake: return empty list
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sweet> getSweetById(@PathVariable Long id) {
        // Mistake: return null instead of sweet
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sweet> updateSweet(@PathVariable Long id, @RequestBody Sweet sweet) {
        // Mistake: skip updating fields
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable Long id) {
        // Mistake: do nothing
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<Sweet> purchaseSweet(@PathVariable Long id, @RequestParam int quantity) {
        // Mistake: return null instead of updated sweet
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{id}/restock")
    public ResponseEntity<Sweet> restockSweet(@PathVariable Long id, @RequestParam int quantity) {
        // Mistake: return null instead of restocked sweet
        return ResponseEntity.ok(null);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Sweet>> searchSweets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        // Mistake: return all sweets without filtering
        return ResponseEntity.ok(sweetService.getAllSweets());
    }
}
