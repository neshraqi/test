package hami.hamibelit.BaseController;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class FailSafeStringTypeAdapter extends TypeAdapter<String> {

    @Override
    public void write(final JsonWriter writer, final String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String read(final JsonReader reader)
            throws IOException {
        final JsonToken peek = reader.peek();
        if (peek.ordinal() != JsonToken.STRING.ordinal()) {
            reader.skipValue();
            return null;
        }
        return reader.nextString();
    }

}