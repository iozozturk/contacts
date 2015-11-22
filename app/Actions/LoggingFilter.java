package Actions;

import play.Logger;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Created by tr1o5087 on 03.02.2015.
 */
public class LoggingFilter extends Action.Simple {

    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        long startTime = System.currentTimeMillis();
        F.Promise<Result> result = delegate.call(context);
        result = result.map((result1) -> {
            long endTime = System.currentTimeMillis();
            long requestTime = endTime - startTime;
            Logger.info(context.request().toString() +
                    " took " + requestTime + " ms and returned " + result1.toScala().header().status());
            context.response().setHeader("Request-Time", String.valueOf(requestTime));
            return result1;
        });
        return result;
    }

}


