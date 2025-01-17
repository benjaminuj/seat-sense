package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.INACTIVE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.dao.StoreMemberRepository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.domain.StorePosition;
import project.seatsence.src.store.dto.request.admin.member.StoreMemberRegistrationRequest;
import project.seatsence.src.store.dto.request.admin.member.StoreMemberUpdateRequest;
import project.seatsence.src.store.dto.response.admin.member.StoreMemberListResponse;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreMemberService {

    private final StoreMemberRepository storeMemberRepository;
    private final UserService userService;
    private final StoreService storeService;

    public StoreMember findByIdAndState(Long id) {
        return storeMemberRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(STORE_MEMBER_NOT_FOUND));
    }

    public Boolean memberExists(User user, Long storeId) {
        return !storeMemberRepository.existsByUserIdAndStoreIdAndState(
                user.getId(), storeId, ACTIVE);
    }

    public void storeMemberRegistration(
            Long storeId, StoreMemberRegistrationRequest storeMemberRegistrationRequest)
            throws JsonProcessingException {

        User user =
                userService.findUserByUserEmailAndState(storeMemberRegistrationRequest.getEmail());

        if (!memberExists(user, storeId)) {
            throw new BaseException(STORE_MEMBER_ALREADY_EXIST);
        }

        Store store = storeService.findByIdAndState(storeId);

        ObjectMapper objectMapper = new ObjectMapper();
        String permissionByMenu =
                objectMapper.writeValueAsString(
                        storeMemberRegistrationRequest.getPermissionByMenu());

        StoreMember newStoreMember =
                new StoreMember(user, store, StorePosition.MEMBER, permissionByMenu);
        storeMemberRepository.save(newStoreMember);
    }

    public List<StoreMember> findAllByStoreIdAndPosition(Long id) {

        List<StoreMember> memberList =
                storeMemberRepository.findAllByStoreIdAndPositionAndState(
                        id, StorePosition.MEMBER, ACTIVE);
        if (memberList == null || memberList.isEmpty()) throw new BaseException(SUCCESS_NO_CONTENT);
        return memberList;
    }

    public void update(Long storeId, StoreMemberUpdateRequest storeMemberUpdateRequest)
            throws JsonProcessingException {

        StoreMember storeMember = findByIdAndState(storeMemberUpdateRequest.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        String permissionByMenu =
                objectMapper.writeValueAsString(storeMemberUpdateRequest.getPermissionByMenu());
        storeMember.setPermissionByMenu(permissionByMenu);
    }

    public void delete(Long id) {
        StoreMember storeMember = findByIdAndState(id);
        storeMember.setState(INACTIVE);
    }

    public String getPermissionByMenu(Long storeId, String userEmail) {

        User user = userService.findByEmailAndState(userEmail);
        StoreMember storeMember =
                storeMemberRepository
                        .findByStoreIdAndUserIdAndState(storeId, user.getId(), ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));

        return storeMember.getPermissionByMenu();
    }

    public List<StoreMemberListResponse.StoreMemberResponse> toStoreMemberResponseList(
            List<StoreMember> storeMembers) {
        return storeMembers.stream().map(this::toStoreMemberResponse).collect(Collectors.toList());
    }

    private StoreMemberListResponse.StoreMemberResponse toStoreMemberResponse(
            StoreMember storeMember) {
        return StoreMemberListResponse.StoreMemberResponse.builder()
                .id(storeMember.getId())
                .name(storeMember.getUser().getName())
                .email(storeMember.getUser().getEmail())
                .permissionByMenu(storeMember.getPermissionByMenu())
                .build();
    }
}
