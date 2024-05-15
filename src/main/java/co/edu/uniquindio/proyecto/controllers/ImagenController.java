package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.ImagenDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.services.interfaces.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/imagenes")
@RequiredArgsConstructor
public class ImagenController {

    private final ImageService imagenesServicio;
    @PostMapping("/update")
    public ResponseEntity<MensajeDTO<Map>> update(@RequestParam("file") MultipartFile imagen)
            throws Exception{
        Map  respuesta = imagenesServicio.saveImage(imagen);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, respuesta ));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MensajeDTO<Map>> delete(@Valid @RequestBody ImagenDTO imagenDTO) throws
            Exception{
        Map respuesta = imagenesServicio.deleteImage( imagenDTO.id() );
        return ResponseEntity.ok().body(new MensajeDTO<>(false, respuesta ));
    }

}