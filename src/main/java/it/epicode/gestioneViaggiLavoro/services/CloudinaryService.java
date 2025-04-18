package it.epicode.gestioneViaggiLavoro.services;

import com.cloudinary.Cloudinary;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String uploadImmagine(MultipartFile file) throws FileUploadException {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("folder", "impostazioni_dipendenti");
            File tempFile = convertToFile(file);
            Map<String, Object> uploadResult = cloudinary.uploader().upload(tempFile, params);
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new FileUploadException("Errore nel caricamento dell'immagine", e);
        }
    }

    private File convertToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }
}
