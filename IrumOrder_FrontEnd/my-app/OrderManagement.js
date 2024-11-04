import React, { useState } from 'react';
import { View, Button } from 'react-native';
import OrderStatus from './OrderStatus';
import ReservationStatus from './ReservationStatus';

export default function OrderManagement() {
  const [currentTab, setCurrentTab] = useState('orderStatus');

  // 임시 주문 데이터 - 실제 데이터로 교체 예정
  const [orderData, setOrderData] = useState([
    { id: 1, status: '신규주문', details: '주문 번호: 101' },
    { id: 2, status: '예약대기', details: '주문 번호: 102' },
    { id: 3, status: '주문접수', details: '주문 번호: 103' },
    { id: 4, status: '상품준비완료', details: '주문 번호: 104' },
    { id: 5, status: '완료', details: '주문 번호: 105' },
  ]);

  return (
    <View style={{ flex: 1, padding: 20 }}>
      <View style={{ flexDirection: 'row', justifyContent: 'space-between', marginBottom: 20 }}>
        <Button title="주문현황" onPress={() => setCurrentTab('orderStatus')} />
        <Button title="예약현황" onPress={() => setCurrentTab('reservationStatus')} />
      </View>

      {/* OrderStatus에 orderData를 props로 전달 */}
      {currentTab === 'orderStatus' ? <OrderStatus orderData={orderData} /> : <ReservationStatus />}
    </View>
  );
}