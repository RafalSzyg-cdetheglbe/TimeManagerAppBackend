package com.start.timemanager.controller;

import java.util.List;

import com.start.timemanager.dto.BucketDTO;

import java.security.Principal;

import org.springframework.web.bind.annotation.*;

import com.start.timemanager.service.implementation.BucketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BucketController {
    private final BucketService bucketService;

    @GetMapping("/buckets")
    public List<BucketDTO> getBuckets(Principal principal) {
        return this.bucketService.getBuckets(principal.getName());
    }

    @GetMapping("/buckets/{id}")
    public BucketDTO getBucket(@PathVariable("id") Long bucketId, Principal principal) {
        return this.bucketService.getBucket(bucketId, principal.getName());
    }

    @PostMapping("/buckets")
    public BucketDTO addBucket(@RequestBody BucketDTO bucket, Principal principal) {
       return this.bucketService.addBucket(bucket, principal.getName());
    }

    @PutMapping("/buckets/{id}")
    public BucketDTO updateBucket(@RequestBody BucketDTO bucket, Principal principal, @PathVariable("id")Long id) {
      return  this.bucketService.updateBucket(bucket,id, principal.getName());
    }

    @DeleteMapping("/buckets/{id}")
    public Long deleteBucket(@PathVariable("id") Long id, Principal principal) {
        return this.bucketService.deleteBucket(id, principal.getName());
    }

    // @GetMapping("/mapBuckets")
    // @ResponseBody
    // public List<BucketDTO> getAllBuckets(Principal principal) {
    //     List<BucketDTO> buckets = bucketService.getAllBuckets(principal.getName());
    //     return buckets;
    // }
}
