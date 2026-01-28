package com.cwng.profileservice.controller;

import com.cwng.profileservice.model.ProfileDTO;
import com.cwng.profileservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<Flux<ProfileDTO>> getAllProfile(){
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @GetMapping("/checkDuplicate/{email}")
    public ResponseEntity<Mono<Boolean>> checkDuplicate(@PathVariable("email") String email){
        return ResponseEntity.ok(profileService.checkDuplicate(email));
    }
}
