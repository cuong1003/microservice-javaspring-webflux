package com.cwng.profileservice.repository;

import com.cwng.profileservice.data.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileRepository extends ReactiveCrudRepository<Profile,Long> {
    Mono<Profile> findByEmail(String email);
}
