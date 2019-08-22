package ink.bitamin.base;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

import java.io.IOException;

public class CustomerObjectMapper extends ObjectMapper {

    {
        DefaultSerializerProvider.Impl sp = new DefaultSerializerProvider.Impl();
        sp.setNullValueSerializer(new NullSerializer());
        this.setSerializerProvider(sp);
    }

    public class NullSerializer extends JsonSerializer<Object> {
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
                JsonProcessingException {

            jgen.writeString("");
        }
    }

}
