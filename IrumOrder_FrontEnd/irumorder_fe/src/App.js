import React, { useState } from 'react';
import StoreSelection from './components/StoreSelection/StoreSelection';
import MenuView from './components/MenuView/MenuView';

const App = () => {
  const [selectedStore, setSelectedStore] = useState('');

  const handleStoreSelect = (store) => {
    setSelectedStore(store);
  };

  return (
    <div>
      {!selectedStore ? (
        <StoreSelection onStoreSelect={handleStoreSelect} />
      ) : (
        <MenuView selectedStore={selectedStore} />
      )}
    </div>
  );
};

export default App;
