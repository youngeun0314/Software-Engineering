import React from 'react';
import { View, Button } from 'react-native';

export default function Home({ navigation }) {
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Button
        title="로그인"
        onPress={() => navigation.navigate('OrderManagement')}
      />
    </View>
  );
}