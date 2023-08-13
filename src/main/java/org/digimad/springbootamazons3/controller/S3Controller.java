package org.digimad.springbootamazons3.controller;

import org.digimad.springbootamazons3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class S3Controller {

    @Value("${aws.bucketName}")
    private String s3BucketName;

    @Value("${aws.fileName}")
    private String fileName;
    @Autowired
    private S3Service s3Service;

    @GetMapping("/read")
    public void readFromAmazonS3() throws IOException {
        s3Service.readCsvFromS3(s3BucketName, fileName);
    }
}
