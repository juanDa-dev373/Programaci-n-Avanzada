package co.edu.uniquindio.proyecto.services.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
public interface ImageService {
    Map saveImage(MultipartFile image) throws Exception;
    Map deleteImage(String idImage) throws  Exception;
}
