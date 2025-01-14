package project.seatsence.src.utilization.dto.response.reservation;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.src.utilization.domain.reservation.ReservationStatus;

@Getter
@AllArgsConstructor
@Builder
public class AdminReservationListResponse {
    private List<ReservationResponse> reservationResponseList;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ReservationResponse {
        private Long id;
        private String name;
        private ReservationStatus reservationStatus;
        private String storeSpaceName;
        private String reservationUnitReservedByUser;
        private String reservedPlace; // 유저가 예약한 좌석의 관리 id나 유저가 예약한 스페이스 이름
        private LocalDateTime startSchedule;
        private LocalDateTime endSchedule;
        private LocalDateTime createdAt;
    }
}
