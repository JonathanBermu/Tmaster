package com.example.Users.Services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.Users.Types.Interfaces.AWSServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class AWSService implements AWSServiceInterface {
    //Deactivated credentials
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
    public String getObject(String key) throws IOException {
        try{
            /*Date today = new Date();
            Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
            String object = String.valueOf(s3client.generatePresignedUrl(
                    "thisisgoingtobemybucketformyapi",
                    key,
                    tomorrow));
            return object;*/
            return "https://thisisgoingtobemybucketformyapi.s3.amazonaws.com/4e1efcf9-dadd-448f-843a-fa7ab33153ac.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221009T000559Z&X-Amz-SignedHeaders=host&X-Amz-Expires=86400&X-Amz-Credential=AKIAX2LFZLO6VEG7YSM4%2F20221009%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=6aa19e44c2bde7b0e3f3f403e51f4914e8574314ecdeed1a1397301b9a584dbe";
        } catch(Exception e) {
            return null;
        }

    }


}
