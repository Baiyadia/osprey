package com.kaiqi.osprey.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wangs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressResVO {
    private String nickname;
    private List<AddressBookItemVO> address;
    private Long bookId;
    private Integer type;
}
