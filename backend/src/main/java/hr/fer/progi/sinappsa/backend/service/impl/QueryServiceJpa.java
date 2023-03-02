package hr.fer.progi.sinappsa.backend.service.impl;

import hr.fer.progi.sinappsa.backend.entity.AppUser;
import hr.fer.progi.sinappsa.backend.entity.Notice;
import hr.fer.progi.sinappsa.backend.entity.Query;
import hr.fer.progi.sinappsa.backend.entity.enums.QueryStatus;
import hr.fer.progi.sinappsa.backend.repository.AppUserRepository;
import hr.fer.progi.sinappsa.backend.repository.NoticeRepository;
import hr.fer.progi.sinappsa.backend.repository.QueryRepository;
import hr.fer.progi.sinappsa.backend.service.AppUserService;
import hr.fer.progi.sinappsa.backend.service.QueryService;
import hr.fer.progi.sinappsa.backend.service.dto.query.CreateQueryDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.GetNoticeQueriesDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.GetUserQueriesDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.QueryResponseDTO;
import hr.fer.progi.sinappsa.backend.service.dto.query.RejectQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QueryServiceJpa implements QueryService {

    private final AppUserService appUserService;

    private final AppUserRepository appUserRepository;

    private final NoticeRepository noticeRepository;

    private final QueryRepository queryRepository;

    @Autowired
    public QueryServiceJpa(AppUserService appUserService, QueryRepository queryRepository,
        AppUserRepository appUserRepository,
        NoticeRepository noticeRepository) {
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
        this.noticeRepository = noticeRepository;
        this.queryRepository = queryRepository;
    }

    @Override
    public ResponseEntity<?> createQuery(CreateQueryDTO dto) {
        final Notice notice = noticeRepository.findById(dto.getNoticeId()).get();
        final AppUser sender = appUserRepository.findByUserId(dto.getSenderId());
        final AppUser receiver = appUserRepository.findByUserId(notice.getAppUser().getId());
        final QueryStatus status = QueryStatus.IN_PROGRESS;
        final String queryMsg = dto.getQueryMsg();

        Query query = new Query(queryMsg, sender, receiver, notice, status);

        ResponseEntity<?> response = appUserService.mailAfterSentQuery(receiver, sender, queryMsg);

        if (response.getStatusCode() == HttpStatus.OK) {
            queryRepository.save(query);
        }

        return response;
    }

    @Override
    public ResponseEntity<?> getNoticeQueries(GetNoticeQueriesDTO dto) {
        List<Query> queries =
            queryRepository.findAllByNoticeIdAndStatusEquals(dto.getNoticeId(), QueryStatus.IN_PROGRESS);
        Notice notice = noticeRepository.findById(dto.getNoticeId()).get();
        boolean userIsOwner = Objects.equals(notice.getAppUser().getId(), dto.getAppUserId());

        if (!userIsOwner) {
            queries = queries.stream()
                .filter(query -> Objects.equals(query.getSender().getId(), dto.getAppUserId()))
                .collect(Collectors.toList());
        }

        List<QueryResponseDTO> queryDTO = queries.stream().map(QueryResponseDTO::new).toList();
        return new ResponseEntity<>(
            queryDTO,
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> getCurrentUserQueries(GetUserQueriesDTO dto) {
        if (!Objects.equals(dto.getUserProfileId(), dto.getAppUserId())) {
            return new ResponseEntity<>(
                Collections.emptyList(),
                HttpStatus.OK
            );
        }

        List<QueryResponseDTO> queryDTO = queryRepository
                .findAllBySenderIdAndStatusEquals(dto.getAppUserId(), QueryStatus.IN_PROGRESS)
                .stream()
                .map(QueryResponseDTO::new)
                .toList();
        return new ResponseEntity<>(
            queryDTO,
            HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<?> rejectQuery(RejectQueryDTO dto) {
        final Query query = queryRepository.findById(dto.getQueryId()).get();

        if (!Objects.equals(query.getReceiver().getId(), dto.getAppUserId())) {
            return new ResponseEntity<>(
                "Ne možete odbiti upit jer niste primili taj upit",
                HttpStatus.BAD_REQUEST
            );
        }

        query.setStatus(QueryStatus.REJECTED);
        queryRepository.save(query);
        return new ResponseEntity<>(
            HttpStatus.OK
        );
    }

    @Override
    public void acceptQuery(Long queryId) {
        final Query query = queryRepository.findById(queryId).get();

        query.setStatus(QueryStatus.ACCEPTED);
        queryRepository.save(query);
    }

}
