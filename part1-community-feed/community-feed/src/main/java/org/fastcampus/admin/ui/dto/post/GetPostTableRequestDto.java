package org.fastcampus.admin.ui.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fastcampus.common.domain.Pageable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetPostTableRequestDto {

    private Long postId;
    private Pageable pageable;

    public GetPostTableRequestDto(Long postId, Integer pageIndex, Integer pageSize) {
        this.postId = postId;
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
