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
        AccountDetailDTO client= clientService.getClientById(createCommentDTO.idClient());
        commentRepo.save(comment);
        mailService.sendMail(new EmailDTO(
                "Notificación de Comentario y Calificación",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"es\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Notificación de Comentario y Calificación</title>\n" +
                        "    <style>\n" +
                        "        body {\n" +
                        "            font-family: Arial, sans-serif;\n" +
                        "            background-color: #f4f4f4;\n" +
                        "            margin: 0;\n" +
                        "            padding: 20px;\n" +
                        "        }\n" +
                        "        .container {\n" +
                        "            max-width: 600px;\n" +
                        "            margin: auto;\n" +
                        "            background: #fff;\n" +
                        "            padding: 20px;\n" +
                        "            border-radius: 10px;\n" +
                        "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                        "        }\n" +
                        "        h1 {\n" +
                        "            color: #333;\n" +
                        "        }\n" +
                        "        p {\n" +
                        "            color: #666;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <div class=\"container\">\n" +
                        "        <h1>Notificación de Comentario y Calificación</h1>\n" +
                        "        <p>¡Su comentario ha sido recibido!</p>\n" +
                        "        <p>Detalles:</p>\n" +
                        "        <ul>\n" +
                        "            <li>Negocio: "+(business.getName())+"</li>\n" +
                        "            <li>Calificación: "+"</li>\n" +
                        "            <li>Comentario: "+createCommentDTO.message()+"</li>\n" +
                        "        </ul>\n" +
                        "        <p>¡Gracias por compartir su experiencia!</p>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>\n",
                        client.email()

        ));
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
