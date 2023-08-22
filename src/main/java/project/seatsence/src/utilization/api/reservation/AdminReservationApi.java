package project.seatsence.src.utilization.api.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import project.seatsence.global.response.SliceResponse;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.dto.reservation.ReservationMapper;
import project.seatsence.src.utilization.dto.reservation.response.AdminReservationListResponse;
import project.seatsence.src.utilization.service.reservation.AdminReservationService;

@RestController
@RequestMapping("/v1/reservations/admins")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "06. [Reservation - Admin]")
public class AdminReservationApi {

    private final AdminReservationService adminReservationService;

    @Operation(summary = "admin 예약 승인")
    @PostMapping("/{store-id}/approve/")
    public void reservationApprove(
            @PathVariable("store-id") Long storeId,
            @RequestParam("reservation-id") Long reservationId) {
        adminReservationService.reservationApprove(reservationId);
    }

    @Operation(summary = "admin 예약 거절")
    @PostMapping("/{store-id}/reject/")
    public void reservationReject(
            @PathVariable("store-id") Long storeId,
            @RequestParam("reservation-id") Long reservationId) {
        adminReservationService.reservationReject(reservationId);
    }

    @Operation(summary = "admin 예약 전체 리스트")
    @GetMapping("/{store-id}/all-list")
    public SliceResponse<AdminReservationListResponse.ReservationResponse> entireReservationList(
            @PathVariable("store-id") Long storeId,
            @ParameterObject @PageableDefault(page = 1, size = 15) Pageable pageable) {

        Slice<Reservation> reservationSlice =
                adminReservationService.getAllReservationAndState(storeId, pageable);

        SliceResponse<AdminReservationListResponse.ReservationResponse> sliceResponse =
                SliceResponse.of(reservationSlice.map(ReservationMapper::toReservationResponse));

        return sliceResponse;
    }

    @Operation(summary = "admin 예약 대기 리스트")
    @GetMapping("/{store-id}/pending-list")
    public SliceResponse<AdminReservationListResponse.ReservationResponse> pendingReservationList(
            @PathVariable("store-id") Long storeId,
            @ParameterObject @PageableDefault(page = 1, size = 15) Pageable pageable) {

        Slice<Reservation> reservationSlice =
                adminReservationService.getPendingReservation(storeId, pageable);

        SliceResponse<AdminReservationListResponse.ReservationResponse> sliceResponse =
                SliceResponse.of(reservationSlice.map(ReservationMapper::toReservationResponse));

        return sliceResponse;
    }

    @Operation(summary = "admin 예약 처리 완료 리스트")
    @GetMapping("/{store-id}/processed-list")
    public SliceResponse<AdminReservationListResponse.ReservationResponse> processedReservationList(
            @PathVariable("store-id") Long storeId,
            @ParameterObject @PageableDefault(page = 1, size = 15) Pageable pageable) {

        Slice<Reservation> reservationSlice =
                adminReservationService.getProcessedReservation(storeId, pageable);
        SliceResponse<AdminReservationListResponse.ReservationResponse> sliceResponse =
                SliceResponse.of(reservationSlice.map(ReservationMapper::toReservationResponse));

        return sliceResponse;
    }
}
