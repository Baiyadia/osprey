package com.kaiqi.osprey.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBookReqVO {
    private Long bookId;
    private String nickname;
    private String address;
    /**
     * 1正常 -1删除
     */
    private Integer status;
}
