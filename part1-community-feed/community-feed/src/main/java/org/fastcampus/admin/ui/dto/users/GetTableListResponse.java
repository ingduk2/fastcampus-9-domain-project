package org.fastcampus.admin.ui.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetTableListResponse<T> {

    private int totalCount;
    private List<T> tableData;
}
