package hr.fer.progi.sinappsa.backend.service;

import hr.fer.progi.sinappsa.backend.service.dto.query.CreateQueryDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.GetNoticeQueriesDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.GetUserQueriesDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.RejectQueryDTO;
import org.springframework.http.ResponseEntity;

public interface QueryService {

    ResponseEntity<?> createQuery(CreateQueryDTO dto);

    ResponseEntity<?> getNoticeQueries(GetNoticeQueriesDTO dto);

    ResponseEntity<?> getCurrentUserQueries(GetUserQueriesDTO dto);

    ResponseEntity<?> rejectQuery(RejectQueryDTO dto);

    void acceptQuery(Long queryId);

}
