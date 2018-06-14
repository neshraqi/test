package hami.nasimbehesht724.BaseController;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class FailSafeNumberTypeAdapter extends TypeAdapter<Integer> {

    @Override
    public void write(final JsonWriter writer, final Integer value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer read(final JsonReader reader)
            throws IOException {
        final JsonToken peek = reader.peek();
        if (peek.ordinal() != JsonToken.NUMBER.ordinal()) {
            reader.skipValue();
            return null;
        }
        return reader.nextInt();
    }

}