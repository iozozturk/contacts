import play.Application;
import play.GlobalSettings;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import services.ActorService;

import java.lang.reflect.Method;

/**
 * Created by ismet özöztürk on 21/11/15.
 */
public class Global extends GlobalSettings {
    @Override
    public void beforeStart(Application application) {
    }

    @Override
    public void onStart(Application application) {
        ActorService.initActors();
    }

    @Override
    public void onStop(Application application) {
        ActorService.stopActors();
    }

    @Override
    public F.Promise<Result> onError(Http.RequestHeader requestHeader, Throwable throwable) {
        return super.onError(requestHeader, throwable);
    }

    @Override
    public Action onRequest(Http.Request request, Method method) {
        return super.onRequest(request, method);
    }

    @Override
    public F.Promise<Result> onBadRequest(Http.RequestHeader requestHeader, String s) {
        return super.onBadRequest(requestHeader, s);
    }
}
