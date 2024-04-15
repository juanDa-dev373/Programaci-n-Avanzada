package co.edu.uniquindio.proyecto.controllers;

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
    private final BusinessService businessService;

    @GetMapping("/{moderatorId}")
    public ResponseEntity<MensajeDTO<AccountDetailDTO>> getModeratorById(@Valid @PathVariable String moderatorId) throws Exception {
        AccountDetailDTO accountDetailDTO = moderatorService.getModeratorById(moderatorId);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,  accountDetailDTO));
    }

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
    public ResponseEntity<MensajeDTO<String>> deactivateUserAccount(@Valid @RequestParam("moderatorId") String moderatorId,@Valid  @RequestParam("userId") String userId) throws Exception {
        String message = moderatorService.deactivateUserAccount(moderatorId, userId);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, message));
    }

    @PostMapping("/activateUserAccount")
    public ResponseEntity<MensajeDTO<String>> activateUserAccount(@Valid @RequestParam("moderatorId") String moderatorId,@Valid  @RequestParam("userId") String userId) throws Exception {
        String message = moderatorService.activateUserAccount(moderatorId, userId);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, message));
    }

    @PostMapping("/markCommentAsInappropriate")
    public ResponseEntity<MensajeDTO<String>> markCommentAsInappropriate(@Valid @RequestParam("moderatorId") String moderatorId, @Valid @RequestParam("commentId") String commentId) throws Exception {
        String message = moderatorService.markCommentAsInappropriate(moderatorId, commentId);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, message));
    }

    @GetMapping("/getListHistoryReviews")
    public ResponseEntity<MensajeDTO<List<ReviewDTO>>> getListHistoryReviews(@Valid @RequestParam("moderatorId") String moderatorId) throws Exception {
        List<ReviewDTO> reviewDTOList = moderatorService.getListHistoryReviews(moderatorId);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,  reviewDTOList));
    }
    @PostMapping("/registerReview")
    public ResponseEntity<MensajeDTO<String>> addHistroyReview(@Valid @RequestBody RegistrerReviewDTO registrerReviewDTO) throws Exception {
        businessService.registrerReview(registrerReviewDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se Registro la Revición"));
    }
}
