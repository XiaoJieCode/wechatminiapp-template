package cn.wscsoft.backend.common.dto;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * 用户查询请求
 *
 * @author 观止
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;
    /**
     * 要查询的userIds
     */
    private Collection<Long> userIds;

    @Serial
    private static final long serialVersionUID = 1L;
}