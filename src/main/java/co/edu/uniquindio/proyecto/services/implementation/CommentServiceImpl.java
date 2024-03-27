package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.CalificationDTO;
import co.edu.uniquindio.proyecto.dto.CreateCommentDTO;
import co.edu.uniquindio.proyecto.dto.ResponseCommentDTO;
import co.edu.uniquindio.proyecto.model.documents.Comment;
import co.edu.uniquindio.proyecto.repositories.CommentRepo;
import co.edu.uniquindio.proyecto.services.interfaces.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;

    public CommentServiceImpl(CommentRepo commentRepo) {this.commentRepo = commentRepo;}

    @Override
    public void createComentary(CreateCommentDTO createCommentDTO) throws Exception{
        Optional<Comment> commentOptional = commentRepo.findCommentByIdAndIdClientAndIdBusiness(createCommentDTO.id(), createCommentDTO.idClient(), createCommentDTO.idBusiness());
        if(commentOptional.isPresent()){
            throw new Exception("El comentario ya existe");
        }
        Comment comment = new Comment();
        comment.setId(createCommentDTO.id());
        comment.setDate(createCommentDTO.date());
        comment.setMessage(createCommentDTO.message());
        comment.setIdBusiness(createCommentDTO.idBusiness());
        comment.setIdClient(createCommentDTO.idClient());
        commentRepo.save(comment);
    }

    @Override
    public void ResponseComentary(ResponseCommentDTO responseCommentDTO) throws Exception{
        Optional<Comment> commentOptional = commentRepo.findCommentByIdAndIdBusiness(responseCommentDTO.idComment(), responseCommentDTO.idBusiness());
        if(!commentOptional.isPresent()){
            throw new Exception("El Comentario no Existe");
        }
        Comment comment = commentOptional.get();
        Comment response = new Comment();
        response.setId(responseCommentDTO.idResponse());
        response.setDate(responseCommentDTO.date());
        response.setIdClient(responseCommentDTO.idClient());
        response.setIdBusiness(responseCommentDTO.idBusiness());
        response.setMessage(responseCommentDTO.message());

        comment.setAnswer(response);

        commentRepo.save(comment);

    }

    @Override
    public List<Comment> listComentary(String idBusiness) throws Exception{
        List<Comment> commentList = commentRepo.findCommentByIdBusiness(idBusiness);
        if(commentList.isEmpty()){
            throw new Exception("No hay Comentarios");
        }
        return commentList;
    }

    @Override
    public void calification(CalificationDTO calificationDTO) throws Exception {
        Optional<Comment> comment = commentRepo.findCommentByIdAndIdClientAndIdBusiness(calificationDTO.id(), calificationDTO.idCliente(), calificationDTO.idBusiness());
        if(!comment.isPresent()){
            throw new Exception("el comentario no esta disponible");
        }
        Comment comment1 = comment.get();
        comment1.setRating(calificationDTO.calification());
        commentRepo.save(comment1);
    }

    @Override
    public Comment getComment(String id, String idBusiness) throws Exception {
        Optional<Comment> commentOptional = commentRepo.findCommentByIdAndIdBusiness(id, idBusiness);
        if(commentOptional.isEmpty()){
            throw new Exception("El comentario no existe");
        }
        return commentOptional.get();
    }

}
