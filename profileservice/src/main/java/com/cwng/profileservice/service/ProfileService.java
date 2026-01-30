package com.cwng.profileservice.service;

import com.cwng.commonservice.common.CommonException;
import com.cwng.profileservice.model.ProfileDTO;
import com.cwng.profileservice.repository.ProfileRepository;
import com.cwng.profileservice.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
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

    public Mono<ProfileDTO> createNewProfile(ProfileDTO profileDTO){
        return checkDuplicate(profileDTO.getEmail())
                .flatMap( x -> {
                    if (Boolean.TRUE.equals(x)) {
                        return Mono.error(new CommonException("1001", "Profile already exists", HttpStatus.CONFLICT));
                    } else {
                        profileDTO.setStatus(Constant.STATUS_PROFILE_PENDING);
                        return Mono.just(profileDTO)
                                .map(ProfileDTO::dtoToEntity)
                                .flatMap( profileRepository::save)
                                .map(ProfileDTO::entityToDto)
                                .doOnError(throwable -> log.error(throwable.getMessage()))
                                .doOnSuccess( dto -> log.info("Profile created successfully"));
                    }
                });
    }

}
