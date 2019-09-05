package com.zh.java.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.apache.commons.codec.binary.Base64;

class ByteAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
    ByteAdapter() {
    }

    public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
        Base64 base64 = new Base64();
        String value = "";
        if (src != null) {
            value = new String(base64.encode(src));
        }

        return new JsonPrimitive(value);
    }

    public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Base64 base64 = new Base64();
        String base64Str = json.getAsString();
        return (byte[])base64.decode(base64Str.getBytes());
    }
}
