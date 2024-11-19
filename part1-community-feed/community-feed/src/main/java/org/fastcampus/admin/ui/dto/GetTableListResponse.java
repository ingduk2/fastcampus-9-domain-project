package org.fastcampus.admin.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetTableListResponse<T> {

    private int totalCount;
    private T tableData;
}
