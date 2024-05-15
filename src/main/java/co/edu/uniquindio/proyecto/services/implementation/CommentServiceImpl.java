package co.edu.uniquindio.proyecto.services.implementation;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.model.documents.Client;
import co.edu.uniquindio.proyecto.model.documents.Comment;
import co.edu.uniquindio.proyecto.repositories.CommentRepo;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessService;
import co.edu.uniquindio.proyecto.services.interfaces.ClientService;
import co.edu.uniquindio.proyecto.services.interfaces.CommentService;
import co.edu.uniquindio.proyecto.services.interfaces.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final MailService mailService;
    private final ClientService clientService;
    private final BusinessService businessService;

    @Override
    public void createComentary(CreateCommentDTO createCommentDTO) throws Exception{
        Optional<Comment> commentOptional = commentRepo.findCommentByIdAndIdClientAndIdBusiness(createCommentDTO.id(), createCommentDTO.idClient(), createCommentDTO.idBusiness());
        if(commentOptional.isPresent()){
            throw new Exception("El comentario ya existe");
        }
        Comment comment = new Comment();
        comment.setDate(createCommentDTO.date());
        comment.setMessage(createCommentDTO.message());
        comment.setIdBusiness(createCommentDTO.idBusiness());
        comment.setIdClient(createCommentDTO.idClient());
        Business business= businessService.search(createCommentDTO.idBusiness());
        // se le envia email al creador del negocio
        Client clientOwner = clientService.getClientId(business.getIdClient());
        Client client = clientService.getClientId(createCommentDTO.idClient());
        mailService.sendMail(new EmailDTO(
                "Creacion de comentario en mi negocio",
                "        <h1>Notificación de Comentario</h1>\n" +
                        "        <p>se ha registrado un nuevo comentario en el negocio<br>"+business.getName()+"</p>\n" +
                        "        <p>por parte del cliente "+client.getNickname()+"</p>",
                clientOwner.getEmail()

        ));
        //se le envia mensaje por email al creador del comentario
        String comentary="No asignaste una clalificación";
        if(comment.getRating()!=0) comentary=""+comment.getRating();
        commentRepo.save(comment);
        mailService.sendMail(new EmailDTO(
                "Notificación de Comentario y Calificación",
                "        <h1>Notificación de Comentario y Calificación</h1>\n" +
                        "        <p>¡Su comentario ha sido recibido!</p>\n" +
                        "        <p>Detalles:</p>\n" +
                        "        <ul>\n" +
                        "            <li>Negocio: "+(business.getName())+"</li>\n" +
                        "            <li>Calificación: "+comentary+"</li>\n" +
                        "            <li>Comentario: "+createCommentDTO.message()+"</li>\n" +
                        "        </ul>\n" +
                        "        <p>¡Gracias por compartir su experiencia!</p>\n",
                        client.getEmail()
        ));
    }

    @Override
    public void ResponseComentary(ResponseCommentDTO responseCommentDTO) throws Exception{
        Optional<Comment> commentOptional = commentRepo.findCommentByIdAndIdBusiness(responseCommentDTO.idComment(), responseCommentDTO.idBusiness());
        if(commentOptional.isEmpty()){
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

        Business business= businessService.search(responseCommentDTO.idBusiness());
        Client client = clientService.getClientId(responseCommentDTO.idClient());
        mailService.sendMail(new EmailDTO(
                "Notificación de Comentario y Calificación",
                "        <h1>Notificación de Comentario y Calificación</h1>\n" +
                        "        <p>¡Su comentario ha sido respondido!</p>\n" +
                        "        <p>Detalles:</p>\n" +
                        "        <ul>\n" +
                        "            <li>Negocio: "+business.getName()+"</li>\n" +
                        "            <li>Comentario: "+comment.getMessage()+"</li>\n" +
                        "            <li>Respuesta del usuario: "+client.getNickname()+"</li>\n" +
                        "            <li>Respuesta: "+response.getMessage()+"</li>\n" +
                        "        </ul>\n" +
                        "        <p>¡Gracias por compartir su experiencia!</p>\n",
                client.getEmail()

        ));

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
        if(comment.isEmpty()){
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

    @Override
    public void deleteComment(DeleteCommentDTO deleteCommentDTO) throws Exception {
        Optional<Comment> commentOptional = commentRepo.findCommentByIdAndIdClientAndIdBusiness(deleteCommentDTO.id(), deleteCommentDTO.idCliente(), deleteCommentDTO.business());
        if(commentOptional.isEmpty()){
            throw  new Exception("El comentario no sirve");
        }
        Client clientOwner = clientService.getClientId(deleteCommentDTO.idClientOwnerBusiness());
        if(clientOwner.getId().equals(deleteCommentDTO.idClientOwnerBusiness())){
            commentRepo.deleteByIdAndIdBusinessAndIdClient(deleteCommentDTO.id(), deleteCommentDTO.business(), deleteCommentDTO.idCliente());
        }else{
            throw new Exception("No se puede borrar el comentario");
        }
    }

}
