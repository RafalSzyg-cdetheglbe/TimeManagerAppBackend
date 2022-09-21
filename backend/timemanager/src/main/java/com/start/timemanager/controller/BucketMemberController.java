package com.start.timemanager.controller;

import com.start.timemanager.dto.BucketMemberDTO;
import com.start.timemanager.model.BucketMember;

import org.springframework.web.bind.annotation.*;
import com.start.timemanager.service.implementation.BucketMemberService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BucketMemberController {
    private final BucketMemberService bucketMemberService;

    @GetMapping("/buckets/bucket_members")
    public List<BucketMember> getBucketMembers(@RequestParam Long bucket_id) {
        return this.bucketMemberService.getBucketMembers(bucket_id);
    }

    @PostMapping("/buckets/bucket_members")
    public BucketMemberDTO addBucketMemeber(@RequestBody BucketMemberDTO bucketMemberDTO) {

        return this.bucketMemberService.addBucketMember(bucketMemberDTO);
    }

    @DeleteMapping("/buckets/bucket_members/{id}")
    public void deleteBucketMember(@PathVariable("id") Long id) {
        this.bucketMemberService.deleteBucketMember(id);
    }
}
