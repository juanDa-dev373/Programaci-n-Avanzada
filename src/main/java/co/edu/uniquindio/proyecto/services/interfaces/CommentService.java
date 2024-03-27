package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.CalificationDTO;
import co.edu.uniquindio.proyecto.dto.CreateCommentDTO;
import co.edu.uniquindio.proyecto.dto.ResponseCommentDTO;
import co.edu.uniquindio.proyecto.model.documents.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    void createComentary(CreateCommentDTO createCommentDTO) throws Exception;
    void ResponseComentary(ResponseCommentDTO responseCommentDTO) throws Exception;
    List<Comment> listComentary(String idBusiness) throws Exception;
    void calification(CalificationDTO calificationDTO) throws Exception;
    Comment getComment(String id, String idBusiness) throws Exception;
}
