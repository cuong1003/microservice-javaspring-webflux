package com.cwng.profileservice.service;

import com.cwng.profileservice.model.ProfileDTO;
import com.cwng.profileservice.repository.ProfileRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Flux<ProfileDTO> getAllProfiles() {
        return profileRepository.findAll().map( x -> ProfileDTO.entityToDto(x))
                .switchIfEmpty(Mono.error(new Exception("No Profile found")));
    }

    public Mono<Boolean> checkDuplicate(String email){
        return profileRepository.findByEmail(email)
                .flatMap( x -> Mono.just(true) )
                .switchIfEmpty(Mono.just(false));
    }

}
