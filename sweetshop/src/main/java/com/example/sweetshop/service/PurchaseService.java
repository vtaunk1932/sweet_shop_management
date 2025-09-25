package com.example.sweetshop.service;

import com.example.sweetshop.entity.Purchase;
import com.example.sweetshop.entity.Sweet;
import com.example.sweetshop.entity.User;
import com.example.sweetshop.repository.PurchaseRepository;
import com.example.sweetshop.repository.SweetRepository;
import com.example.sweetshop.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SweetRepository sweetRepository;
    private final UserRepository userRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, SweetRepository sweetRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.sweetRepository = sweetRepository;
        this.userRepository = userRepository;
    }

    // Intentionally buggy method for RED stage
    public Purchase createPurchase(Long userId, Long sweetId, int quantity) {
        // Mistake 1: Using get() without checking presence → NoSuchElementException
        User user = userRepository.findById(userId).get();

        // Mistake 2: Using wrong repository method (findAll instead of findById)
        Sweet sweet = sweetRepository.findAll().get(0);

        // Mistake 3: Wrong logic for stock check (inverted condition)
        if (sweet.getQuantity() > quantity) {
            throw new IllegalArgumentException("Insufficient sweet stock");
        }

        // Mistake 4: Forgetting to update sweet quantity → stock won’t reduce

        // Mistake 5: Passing null for user in purchase (runtime failure)
        Purchase purchase = new Purchase(null, sweet, quantity);

        return purchaseRepository.save(purchase);
    }
}
