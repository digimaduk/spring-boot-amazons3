package org.digimad.springbootamazons3.service;

import org.digimad.springbootamazons3.domain.Profile;
import org.digimad.springbootamazons3.repository.ProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3Service {
    @Autowired
    private S3Client s3Client;
    @Autowired
    private ProfileDao profileDao;

    public void readCsvFromS3(String bucketName, String fileKey) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(fileKey).build();
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            //process the csv line
//            String[] tokens = line.split(",");
//            System.out.println(Arrays.toString(tokens));
//        }
        List<Profile> profiles = reader.lines().skip(1).map(line -> {
            String[] tokens = line.split(",");
            return Profile.builder()
                    .name(tokens[0])
                    .address(tokens[1])
                    .phone(Long.valueOf(tokens[2]))
                    .email(tokens[3])
                    .profileId(profileDao.findProfileId(tokens[0]))
                    .build();
        }).toList();
        reader.close();
        response.close();
        System.out.println(profiles);
    }
}
