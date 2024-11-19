package org.fastcampus.admin.ui.query;

import org.fastcampus.admin.ui.dto.GetTableListResponse;
import org.fastcampus.admin.ui.dto.post.GetPostTableRequestDto;
import org.fastcampus.admin.ui.dto.post.GetPostTableResponseDto;
import org.fastcampus.admin.ui.dto.users.GetUserTableRequestDto;
import org.fastcampus.admin.ui.dto.users.GetUserTableResponseDto;

import java.util.List;

public interface AdminTableQueryRepository {
    GetTableListResponse<List<GetUserTableResponseDto>> getUserTableData(GetUserTableRequestDto dto);
    GetTableListResponse<List<GetPostTableResponseDto>> getPostTableData(GetPostTableRequestDto dto);
}
