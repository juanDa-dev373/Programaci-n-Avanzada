package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.services.interfaces.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Service
public class ImageServiceImpl implements ImageService {
    private final Cloudinary cloudinary;

    public ImageServiceImpl() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name","dybshhtw1");
        config.put("api_key","363912942717214");
        config.put("api_secret","2vjQBxJbEIuQm1fX3sTWrGDNwg0");
        cloudinary = new Cloudinary(config);
    }

    @Override
    public Map saveImage(MultipartFile image) throws Exception {
        File file = conver(image);
        return cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "unilocal"));
    }

    @Override
    public Map deleteImage(String idImage) throws Exception {
        return cloudinary.uploader().destroy(idImage, ObjectUtils.emptyMap());
    }
    private File conver(MultipartFile image) throws IOException {
        File file1 = File.createTempFile(image.getOriginalFilename(), null);
        FileOutputStream fos = new FileOutputStream(file1);
        fos.write(image.getBytes());
        fos.close();
        return file1;
    }
}
