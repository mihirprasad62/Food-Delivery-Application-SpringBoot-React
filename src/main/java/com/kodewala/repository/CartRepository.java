package com.kodewala.repository;

import com.kodewala.entity.CartEntity;
import com.kodewala.io.CartRequest;
import com.kodewala.io.CartResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<CartEntity,String> {
    Optional<CartEntity> findByUserId(String userId);

    CartResponse deleteByUserId(CartRequest request);
}
