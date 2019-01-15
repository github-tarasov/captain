package com.example.captain.repository;

import com.example.captain.domain.Captain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CaptainRepository extends MongoRepository<Captain, Long> {

}
