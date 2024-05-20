package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.model.documents.Business;
import co.edu.uniquindio.proyecto.services.interfaces.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.services.interfaces.ModeratorService;

import java.util.List;

@RestController
@RequestMapping("/api/moderadores")
@RequiredArgsConstructor
public class ModeratorController {

    private final ModeratorService moderatorService;

    @PostMapping("/verifyAndApproveBusiness")
    public ResponseEntity<MensajeDTO<String>> verifyAndApproveBusiness(@Valid @RequestBody HistoryReviewDTO reviewDTO) throws Exception {
        String message = moderatorService.verifyAndApproveBusiness(reviewDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, message));
    }

    @PostMapping("/rejectBusiness")
    public ResponseEntity<MensajeDTO<String>> rejectBusiness(@Valid @RequestBody HistoryReviewDTO reviewDTO) throws Exception {
        String message = moderatorService.rejectBusiness(reviewDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, message));
    }

    @PostMapping("/deactivateUserAccount")
    public ResponseEntity<MensajeDTO<String>> deactivateUserAccount(@RequestHeader("Authorization") String token,@Valid @RequestParam("moderatorId") String moderatorId,@Valid  @RequestParam("userId") String userId) throws Exception {
        String message = moderatorService.deactivateUserAccount(token.replace("Bearer ", ""),moderatorId, userId);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, message));
    }

    @GetMapping("/getListHistoryReviews")
    public ResponseEntity<MensajeDTO<List<ReviewDTO>>> getListHistoryReviews(@Valid @RequestParam("moderatorId") String moderatorId) throws Exception {
        List<ReviewDTO> reviewDTOList = moderatorService.getListHistoryReviews(moderatorId);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,  reviewDTOList));
    }

    @GetMapping("/getAllBusinessPending")
    ResponseEntity<MensajeDTO<List<Business>>> getAllBusinessPending() throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, moderatorService.allBusinessPending()));
    }

    @PostMapping("/logOutUser")
    public ResponseEntity<MensajeDTO<String>> logOutUser( @RequestHeader("Authorization") String token) throws Exception {
        moderatorService.logOutUser(token.replace("Bearer ", ""));
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Cierre exitoso" ));
    }
}
