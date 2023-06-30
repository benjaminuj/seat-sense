package project.seatsence.src.store.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.Day;

@Getter
@Setter
public class AdminStoreResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // TODO user 연결하기

    private List<String> wifiList;
    private String name;
    private String introduction;
    private String location;

    // TODO 대표 이미지 업로드 설정(필수)

    private String mainImage;
    private int totalFloor;
    private Category category;
    private List<Day> dayOff;
    private String monOpenTime;
    private String monCloseTime;
    private String tueOpenTime;
    private String tueCloseTime;
    private String wedOpenTime;
    private String wedCloseTime;
    private String thuOpenTime;
    private String thuCloseTime;
    private String friOpenTime;
    private String friCloseTime;
    private String satOpenTime;
    private String satCloseTime;
    private String sunOpenTime;
    private String sunCloseTime;
    private String breakTime;
    private String useTimeLimit;
    private String memo;
    private int avgUseTime;
    private String createdBy;
    private String lastModifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BaseTimeAndStateEntity.State state;
}