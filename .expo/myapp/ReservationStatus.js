import React, { useState } from 'react';
import { View, Text, FlatList, TouchableOpacity } from 'react-native';

const ReservationStatus = () => {
  const [selectedHour, setSelectedHour] = useState(null);
  const [selectedMinute, setSelectedMinute] = useState(null);
  const [reservationDetails, setReservationDetails] = useState([
    { hour: 9, count: 1 },
    { hour: 10, count: 0 },
    { hour: 11, count: 2 },
    { hour: 12, count: 2 },
    { hour: 13, count: 1 },
    { hour: 14, count: 0 },
    { hour: 15, count: 0 },
    { hour: 16, count: 2 },
    { hour: 17, count: 1 },
    { hour: 18, count: 0 },
    { hour: 19, count: 0 },
  ]);

  const handleHourSelect = (hour) => {
    setSelectedHour(hour);
    setSelectedMinute(null);
  };

  const handleMinuteSelect = (minute) => {
    setSelectedMinute(minute);
  };

  return (
    <View style={{ padding: 10 }}>
      <Text style={{ fontSize: 18, fontWeight: 'bold', marginBottom: 10 }}>예약 현황</Text>

      {/* 시간대 목록 세로 정렬 */}
      <FlatList
        data={reservationDetails}
        keyExtractor={(item) => item.hour.toString()}
        renderItem={({ item }) => (
          <TouchableOpacity onPress={() => handleHourSelect(item.hour)} style={{ padding: 10 }}>
            <Text style={{ fontSize: 16 }}>{`${item.hour}시 (${item.count}건)`}</Text>
          </TouchableOpacity>
        )}
      />

      {selectedHour && (
        <>
          <Text style={{ fontSize: 16, marginTop: 20 }}>{`${selectedHour}시 예약`}</Text>

          {/* 10분 간격 표시 */}
          <FlatList
            horizontal
            data={Array.from({ length: 6 }, (_, i) => i * 10)}
            keyExtractor={(item) => item.toString()}
            renderItem={({ item }) => (
              <TouchableOpacity onPress={() => handleMinuteSelect(item)} style={{ margin: 5 }}>
                <Text style={{ fontSize: 16 }}>{`${item}분`}</Text>
              </TouchableOpacity>
            )}
          />
        </>
      )}

      {selectedMinute !== null && (
        <View style={{ marginTop: 20 }}>
          <Text style={{ fontSize: 16 }}>{`${selectedHour}시 ${selectedMinute}분 예약 목록`}</Text>
          {/* 주문번호 예시 */}
          <FlatList
            data={['주문번호: 201', '주문번호: 202', '주문번호: 203']}
            keyExtractor={(item) => item}
            renderItem={({ item }) => (
              <Text style={{ fontSize: 14, padding: 5 }}>{item}</Text>
            )}
          />
        </View>
      )}
    </View>
  );
};

export default ReservationStatus;
