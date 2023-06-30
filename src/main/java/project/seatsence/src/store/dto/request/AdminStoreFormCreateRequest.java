package project.seatsence.src.store.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
@Validated
public class AdminStoreFormCreateRequest {

    @NotBlank(message = "스페이스의 이름을 입력해주세요.")
    private String name;

    @Positive(message = "스페이스의 가로 길이를 입력해주세요.")
    private int width;

    @Positive(message = "스페이스의 세로 길이를 입력해주세요.")
    private int height;

    @Positive(message = "스페이스 입구의 x 좌표를 설정해주세요.")
    private int entranceX;

    @Positive(message = "스페이즈의 입구의 y 좌표를 설정해주세요.")
    private int entranceY;

    @Valid private List<@Valid Table> tableList;

    @Validated
    @Getter
    public static class Table {
        @PositiveOrZero private int tableX;

        @PositiveOrZero private int tableY;

        @Valid private List<@Valid Chair> chairList;

        @Validated
        @Getter
        public static class Chair {
            @PositiveOrZero private int chairX;
            @PositiveOrZero private int chairY;
        }
    }
}