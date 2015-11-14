package controllers;

import org.apache.commons.io.FileUtils;
import play.*;
import play.mvc.*;

import views.html.*;

import java.io.File;
import java.io.IOException;

public class Application extends Controller {

    public static final String UPLOAD_DIR = Play.application().configuration().getString("store.file.dir");

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static play.mvc.Result upload() throws IOException {
        play.mvc.Http.MultipartFormData body = request().body().asMultipartFormData();
        play.mvc.Http.MultipartFormData.FilePart filePart = body.getFile("picture");
        if (filePart != null) {
            String fileName = filePart.getFilename();
            String contentType = filePart.getContentType();
            java.io.File file = filePart.getFile();
            Logger.info("PATH:" + file.getPath());

            String baseDir = UPLOAD_DIR;
            File destination = new File(baseDir, filePart.getFilename()).getAbsoluteFile();
            String filePath = destination.getPath();
            FileUtils.moveFile(file, destination);
            return ok("File uploaded");
        } else {
            flash("error", "Missing file");
            return badRequest();
        }
    }

}
