package project.seatsence.src.store.dto.response.admin.basic;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreOwnedStoreResponse {

    private List<StoreResponse> storeResponseList;

    @AllArgsConstructor
    @Getter
    public static class StoreResponse {
        private Long storeId;
        private String storeName;
        private String introduction;
        private String mainImage;
        Boolean isOpenNow;
        Boolean isClosedToday;
    }
}
