package co.edu.uniquindio.proyecto.repositories;

import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.documents.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends MongoRepository<Comment, String> {
    Optional<Comment> findCommentByIdAndIdClientAndIdBusiness(String id, String idCliente, String idBusiness);
    List<Comment> findCommentByIdBusiness(String idBusiness);
    Optional<Comment> findCommentByIdAndIdBusiness(String id, String idBusiness);

}
