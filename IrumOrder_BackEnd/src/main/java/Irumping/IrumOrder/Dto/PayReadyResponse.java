package Irumping.IrumOrder.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayReadyResponse {
    private String tid;
    private String next_redirect_mobile_url; //모바일 웹일경우 주소
    //private String android_app_scheme; //카카오페이 결제 화면으로 이동하는 Android 앱 스킴(Scheme) - 내부 서비스용
    //private String ios_app_scheme; //카카오페이 결제 화면으로 이동하는 iOS 앱 스킴 - 내부 서비스용
}
