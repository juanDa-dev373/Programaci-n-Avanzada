package co.edu.uniquindio.proyecto;

import co.edu.uniquindio.proyecto.dto.CreateCommentDTO;
import co.edu.uniquindio.proyecto.dto.ResponseCommentDTO;
import co.edu.uniquindio.proyecto.model.documents.Comment;
import co.edu.uniquindio.proyecto.services.interfaces.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Test
    public void createCommentTest() throws Exception{
        CreateCommentDTO createCommentDTO = new CreateCommentDTO(
          "comment1",
          LocalDateTime.now(),
          "client2",
          "negocio3",
          "muy buen negocio, la atencion todo estubo estupendo"
        );
        commentService.createComentary(createCommentDTO);

        Assertions.assertEquals("comment1", createCommentDTO.id());
    }
    @Test
    public void responseTest() throws Exception{
        ResponseCommentDTO responseCommentDTO = new ResponseCommentDTO(
          "comment1",
          "response1",
          LocalDateTime.now(),
          "cliente3",
          "negocio3",
          "muchas gracias por sus palabras"
        );
        commentService.ResponseComentary(responseCommentDTO);
        Comment comments = commentService.getComment("comment1", "negocio3");
        Assertions.assertEquals(responseCommentDTO.idResponse(),comments.getAnswer().getId());

    }
    @Test
    public void listComment() throws Exception{

    }
    @Test
    public void calificationTest() throws Exception{

    }

}
