import React from 'react';
import { View, Text, FlatList } from 'react-native';

const OrderStatus = ({ orderData }) => {  // orderData를 props로 받도록 수정
    // 각 status에 따른 주문을 필터링
    const ordersByStatus = {
      신규주문: orderData.filter(order => order.status === '신규주문'),
      예약대기: orderData.filter(order => order.status === '예약대기'),
      주문접수: orderData.filter(order => order.status === '주문접수'),
      상품준비완료: orderData.filter(order => order.status === '상품준비완료'),
      완료: orderData.filter(order => order.status === '완료'),
    };
  
    const renderOrderLine = (orders, status) => (
        <View style={{ marginVertical: 10 }}>
          <Text style={{ fontSize: 18, fontWeight: 'bold' }}>{status}</Text>
          <FlatList
            data={orders}
            renderItem={({ item }) => (
              <View style={{ padding: 10, borderBottomWidth: 1, borderColor: '#ddd' }}>
                <Text>{item.details}</Text>
              </View>
            )}
            keyExtractor={(item) => item.id.toString()}
          />
        </View>
      );
  
    return (
        <View style={{ padding: 10 }}>
          {renderOrderLine(ordersByStatus.신규주문, '신규주문')}
          {renderOrderLine(ordersByStatus.예약대기, '예약대기')}
          {renderOrderLine(ordersByStatus.주문접수, '주문접수')}
          {renderOrderLine(ordersByStatus.상품준비완료, '상품준비완료')}
          {renderOrderLine(ordersByStatus.완료, '완료')}
        </View>
    );
  };
  
  export default OrderStatus;