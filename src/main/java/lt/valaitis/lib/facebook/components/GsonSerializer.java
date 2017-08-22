package lt.valaitis.lib.facebook.components;

import com.google.gson.Gson;

/**
 * @author Tadas Valaitis
 * @since 2016-11-28
 */
class GsonSerializer implements Serializer {
    private final Gson gson;

    GsonSerializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> String serialize(T entity) {
        return gson.toJson(entity);
    }

    @Override
    public <T> T deserialize(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
