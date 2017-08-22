package lt.valaitis.lib.facebook.components;

/**
 * @author Tadas Valaitis
 * @since 2016-11-28
 */
public interface Serializer {
    <T> String serialize(T entity);

    <T> T deserialize(String json, Class<T> clazz);
}
