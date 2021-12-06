package Progmatic.SustainableCommunity.configurations;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
    // this comes from the aws dependency
    @Bean
    public AmazonS3 s3() {
        //interface
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                // this comes from the downloaded file from the aws account credentials
                System.getenv("ACCESSKEY"),
                System.getenv("SECRETKEY")
        );

        return AmazonS3ClientBuilder
                .standard()
                .withRegion("eu-central-1")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
