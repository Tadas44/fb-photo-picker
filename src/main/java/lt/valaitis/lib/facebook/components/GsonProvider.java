package lt.valaitis.lib.facebook.components;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Tadas Valaitis
 * @since 2017-03-03
 */
class GsonProvider {
    private static final Gson gson = new GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public static Gson getGson() {
        return gson;
    }
}
