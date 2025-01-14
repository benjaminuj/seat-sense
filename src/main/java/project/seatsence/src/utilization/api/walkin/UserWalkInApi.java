package project.seatsence.src.utilization.api.walkin;

import static project.seatsence.global.constants.Constants.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.global.config.security.JwtProvider;
import project.seatsence.global.response.SliceResponse;
import project.seatsence.src.utilization.domain.walkin.WalkIn;
import project.seatsence.src.utilization.dto.request.ChairUtilizationRequest;
import project.seatsence.src.utilization.dto.request.SpaceUtilizationRequest;
import project.seatsence.src.utilization.dto.response.walkin.UserWalkInListResponse;
import project.seatsence.src.utilization.service.walkin.UserWalkInService;

@RestController
@RequestMapping("/v1/walk-in/users")
@Tag(name = "08. [WalkIn - User]", description = "유저에 관한 바로 사용 API")
@Validated
@RequiredArgsConstructor
public class UserWalkInApi {
    private final UserWalkInService userWalkInService;

    @Operation(summary = "유저 의자 바로사용", description = "유저가 '바로사용'하고싶은 날짜에 특정 의자를 '바로사용'요청합니다.")
    @PostMapping("/chair")
    public void inputChairWalkIn(
            @RequestHeader(AUTHORIZATION_HEADER) String accessToken,
            @CookieValue(COOKIE_NAME_PREFIX_SECURE + REFRESH_TOKEN_NAME) String refreshToken,
            @Valid @RequestBody ChairUtilizationRequest chairUtilizationRequest)
            throws JsonProcessingException {
        String userEmail = JwtProvider.getUserEmailFromValidToken(accessToken, refreshToken);

        userWalkInService.inputChairWalkIn(userEmail, chairUtilizationRequest);
    }

    @Operation(summary = "유저 스페이스 바로사용", description = "유저가 '바로사용'하고싶은 날짜에 특정 스페이스를 '바로사용'요청합니다.")
    @PostMapping("/space")
    public void inputSpaceWalkIn(
            @RequestHeader(AUTHORIZATION_HEADER) String accessToken,
            @CookieValue(COOKIE_NAME_PREFIX_SECURE + REFRESH_TOKEN_NAME) String refreshToken,
            @Valid @RequestBody SpaceUtilizationRequest spaceUtilizationRequest)
            throws JsonProcessingException {
        String userEmail = JwtProvider.getUserEmailFromValidToken(accessToken, refreshToken);

        userWalkInService.inputSpaceWalkIn(userEmail, spaceUtilizationRequest);
    }

    @Operation(summary = "유저 바로 사용 리스트")
    @GetMapping("/my-list")
    public SliceResponse<UserWalkInListResponse.WalkInResponse> getUserWalkInList(
            @RequestHeader(AUTHORIZATION_HEADER) String accessToken,
            @CookieValue(COOKIE_NAME_PREFIX_SECURE + REFRESH_TOKEN_NAME) String refreshToken,
            @ParameterObject @PageableDefault(page = 1, size = 15) Pageable pageable) {

        String userEmail = JwtProvider.getUserEmailFromValidToken(accessToken, refreshToken);
        Slice<WalkIn> walkInSlice = userWalkInService.getAllWalkIn(userEmail, pageable);

        SliceResponse<UserWalkInListResponse.WalkInResponse> sliceResponse =
                userWalkInService.toSliceResponse(walkInSlice);

        return sliceResponse;
    }
}
