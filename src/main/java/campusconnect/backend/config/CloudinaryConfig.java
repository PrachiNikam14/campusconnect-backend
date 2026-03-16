package campusconnect.backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {

        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", "dafjrlqph",
                        "api_key", "277839339532791",
                        "api_secret", "EptsH8HbyiFywNQxqpzcVFxP3As",
                        "secure", true
                )
        );

    }
}
//package campusconnect.backend.config;
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import org.springframework.context.annotation.Bean;
//
//public class CloudinaryConfig {
//
//    @Bean
//    public Cloudinary cloudinary() {
//        return new Cloudinary(ObjectUtils.asMap(
//            "cloud_name","dafjrlqph",
//                "api_key","277839339532791",
//                "api_secret", "EptsH8HbyiFywNQxqpzcVFxP3As"
//        ));
//    }
//}
