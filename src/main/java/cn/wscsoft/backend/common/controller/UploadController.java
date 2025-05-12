package cn.wscsoft.backend.common.controller;

import cn.wscsoft.backend.common.dto.BaseResponse;
import cn.wscsoft.backend.common.dto.BizException;
import cn.wscsoft.backend.common.dto.ErrorCode;
import cn.wscsoft.backend.common.enums.FileUploadBizEnum;
import cn.wscsoft.backend.common.service.impl.FileUploadService;
import cn.wscsoft.backend.common.util.Context;
import cn.wscsoft.backend.common.util.ThrowUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/upload")
@RestController
public class UploadController {

    private final FileUploadService fileUploadService;

    public UploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/file")
    public BaseResponse<String> uploadFile(@RequestParam("file") MultipartFile file,
                                           @RequestParam("biz") String bizString,
                                           @RequestParam(value = "bizId", required = false) String bizId) {
        FileUploadBizEnum biz = FileUploadBizEnum.getEnumByValue(bizString);
        ThrowUtils.throwIf(biz == null, ErrorCode.PARAMS_ERROR);
        try {
            return BaseResponse.success(
                    fileUploadService.uploadFile(
                            file,
                            biz,
                            bizId,
                            Context.getUserId()));
        } catch (IOException e) {
            throw new BizException(e);
        }
    }


}
