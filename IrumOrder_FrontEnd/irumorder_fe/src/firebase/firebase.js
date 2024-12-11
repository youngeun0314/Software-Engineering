// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getMessaging } from "firebase/messaging";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyCPx0BkCl3LL5_39Tojp217Ol57cWfZzrg",
  authDomain: "irumorder-55e95.firebaseapp.com",
  projectId: "irumorder-55e95",
  storageBucket: "irumorder-55e95.firebasestorage.app",
  messagingSenderId: "392986347225",
  appId: "1:392986347225:web:a156ef96cbf7ffc2c0e0c8"
};

const app = initializeApp(firebaseConfig);
export const messaging = getMessaging(app);
