package actions;

import play.Logger;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by tr1o5087 on 03.02.2015.
 */
public class Exceptions extends Action<Exceptions.Handled> {

    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        try {
            return delegate.call(context);
        } catch (Exception e) {
            String errorMessage = e.getClass().getCanonicalName() + " at " + context.request().toString() + " telling: " + e.getMessage();
            Logger.error(errorMessage);
        }
        return F.Promise.pure(internalServerError("An error happened at server, please check logs for more information."));
    }

    @With(Exceptions.class)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)

    public @interface Handled {
    }
}


