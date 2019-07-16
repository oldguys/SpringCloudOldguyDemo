package com.example.oldguy.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @ClassName: PermsForm
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 9:17
 **/
@Data
public class PermsForm {

    @NotBlank(message = "userId 不能为空！")
    private String userId;

    @NotEmpty(message = "roles 不能为空！")
    private Set<String> roles;

    private Boolean logicalAnd = true;

}
