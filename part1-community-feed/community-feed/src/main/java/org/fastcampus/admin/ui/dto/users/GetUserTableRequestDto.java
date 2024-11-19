package org.fastcampus.admin.ui.dto.users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fastcampus.common.domain.Pageable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserTableRequestDto {

    private String name;
    private Pageable pageable;

    public GetUserTableRequestDto(String name, Integer pageIndex, Integer pageSize) {
        this.name = name;
        this.pageable = new Pageable(
                pageIndex != null ? pageIndex : 1,
                pageSize != null ? pageSize : 10
        );
    }

    public int getOffset() {
        return pageable.getOffset();
    }

    public int getLimit() {
        return pageable.getLimit();
    }

    public int getPageIndex() {
        return pageable.getPageIndex();
    }
}
