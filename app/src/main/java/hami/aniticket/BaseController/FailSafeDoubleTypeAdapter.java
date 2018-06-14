package hami.aniticket.BaseController;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class FailSafeDoubleTypeAdapter extends TypeAdapter<Double> {

    @Override
    public void write(final JsonWriter writer, final Double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Double read(final JsonReader reader)
            throws IOException {
        final JsonToken peek = reader.peek();
        if (!peek.name().contentEquals("DOUBLE")) {
            reader.skipValue();
            return null;
        }
        return reader.nextDouble();
    }

}