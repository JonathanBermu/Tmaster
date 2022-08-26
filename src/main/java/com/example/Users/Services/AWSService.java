package com.example.Users.Services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesisanalytics.model.Input;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class AWSService {
    AWSCredentials credentials = new BasicAWSCredentials("AKIAX2LFZLO6VEG7YSM4", "aZIrlLzbs5k22IOs7sEA7k5lCRal2zJBrkctayYy");
    AmazonS3 s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();

    public String addFile(String base64Data, String imgType) throws IOException {
        UUID uuid = UUID.randomUUID();
        String imgName = uuid.toString();
        byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64((base64Data.substring(base64Data.indexOf(",")+1)).getBytes());
        InputStream fis = new ByteArrayInputStream(bI);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bI.length);
        metadata.setContentType("image/jpg");
        metadata.setCacheControl("public, max-age=31536000");
        s3client.putObject("thisisgoingtobemybucketformyapi",  imgName + imgType, fis, metadata);
        return imgName;
    }

}
