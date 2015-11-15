package utils;

import org.apache.commons.io.FilenameUtils;
import play.mvc.Http;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by ismet on 15/11/15.
 */
public class FileUtils {

    public static File moveFilePartToDir(String dir, Http.MultipartFormData.FilePart filePart) throws IOException {
        java.io.File file = filePart.getFile();

        String filePartFullName = filePart.getFilename();
        String extension = FilenameUtils.getExtension(filePartFullName);
        String filePartName = FilenameUtils.removeExtension(filePartFullName);

        String newFileName = filePartName + UUID.randomUUID() + extension;
        File destination = new File(dir, newFileName).getAbsoluteFile();
        org.apache.commons.io.FileUtils.moveFile(file, destination);

        return destination;
    }
}
