package cn.wscsoft.backend.common.dto;

/**
 * 微信的小程序登录session响应
 * @param sessionKey
 * @param openId
 * @param unionId
 */
public record UserSessionInfoRecord(String sessionKey, String openId, String unionId) {

}
