import "./Paymentcomplete.css";
import { Link } from "react-router-dom";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getUserId, setUserId } from "../context/userStorage";

function Paymentcomplete() {
  const nav = useNavigate();
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const handleApproval = async () => {
      const urlParams = new URLSearchParams(window.location.search);
      const pg_token = urlParams.get("pg_token"); // URL에서 pg_token 추출
      const userId = localStorage.getItem("userId"); // 로컬 스토리지에서 userId 가져오기
      const orderId = localStorage.getItem("orderId"); // 로컬 스토리지에서 orderId 가져오기

      console.log("pg_token:", pg_token);
      console.log("userId:", userId);
      console.log("orderId:", orderId);

      if (!pg_token || !userId || !orderId) {
        console.error("결제 승인에 필요한 정보가 누락되었습니다.");
        alert("결제 승인에 필요한 정보가 없습니다. 다시 시도해주세요.");
        return;
      }

      try {
        setIsLoading(true);

        const approvalPayload = {
          pg_token,
          userId,
          orderId,
          cid: "TC0ONETIME", // 고정된 값
        };

        console.log("결제 승인 요청 payload:", approvalPayload);

        const approvalResponse = await fetch(`/order/pay/approval`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(approvalPayload),
        });

        if (!approvalResponse.ok) {
          const errorText = await approvalResponse.text();
          console.error("결제 승인 실패 메시지:", errorText);
          throw new Error("결제 승인이 실패했습니다.");
        }

        const approvalData = await approvalResponse.json();
        console.log("결제 승인 성공 데이터:", approvalData);

        alert("결제가 성공적으로 완료되었습니다!");

        // userId를 Context로 저장
        setUserId(userId);

        // 결제 승인 완료 후 로컬 스토리지 정리
        localStorage.removeItem("userId");
        localStorage.removeItem("orderId");

        // userId와 동일한 key를 가진 데이터 삭제
        localStorage.removeItem(userId);
        console.log(`LocalStorage에서 userId(${userId})와 동일한 key가 삭제되었습니다.`);
      } catch (error) {
        console.error("결제 승인 요청 중 오류 발생:", error);
        alert("결제 승인 처리 중 문제가 발생했습니다. 다시 시도해주세요.");
      } finally {
        setIsLoading(false);
      }
    };

    handleApproval();
  }, []);

  return (
    <div className="complete-container">
      <div className="complete-text">
        <p className="text1">주문이 정상적으로 완료되었어요!</p>
        <p className="text2">메뉴가 나올때까지 잠시만 기다려 주세요.</p>
      </div>
      <div className="check-image-container">
        <img
          src={`${process.env.PUBLIC_URL}/Group_119.png`}
          alt="check"
          className="check-image"
        />
      </div>
      <div className="character-image-container">
        <img
          src={`${process.env.PUBLIC_URL}/Group_241.png`}
          alt="character"
          className="character-image"
        />
      </div>
      <div className="complete-button-container">
        <Link to={"/main"}>
          <button type="complete" className="complete-button">
            <img
              src={`${process.env.PUBLIC_URL}/complete.png`}
              alt="완료"
              className="complete-image"
            />
          </button>
        </Link>
      </div>
    </div>
  );
}

export default Paymentcomplete;
