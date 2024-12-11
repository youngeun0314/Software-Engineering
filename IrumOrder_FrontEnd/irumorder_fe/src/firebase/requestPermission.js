import { messaging } from "./firebase";
import { getToken } from "firebase/messaging";

const requestPermission = async () => {
  try {
    console.log("푸시 알림 권한 요청 중...");
    const token = await getToken(messaging, { vapidKey: "BJBmmxpN8SblmPzO89aVwQEwSgvUXmfCzFbC17DLaUHKLzyfimDoopzSVejqLWDB-WLhJlp3S1SKwjl_DjB83vc" });
    if (token) {
      console.log("FCM Token:", token);
      // TODO: 서버에 토큰 저장 로직 추가
    } else {
      console.log("푸시 알림 토큰을 가져오지 못했습니다.");
    }
  } catch (error) {
    console.error("토큰 요청 중 오류 발생:", error);
  }
};

export default requestPermission;
