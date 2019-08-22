package ink.bitamin.base;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

//@Configuration
public class CustomConfig {
//    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object paramT, JsonGenerator paramJsonGenerator,
                                  SerializerProvider paramSerializerProvider) throws IOException {
                //设置返回null转为 空字符串""
                paramJsonGenerator.writeString("");
            }
        });
        return objectMapper;
    }
}
