import React, { useState } from "react";
import chatApi from "./services/chatapi.js";
import SockJsClient from 'react-stomp';

import Chat from "./pages/Chat.js";
import Input from "./pages/Input.js";
import Login from "./pages/Login.js";

import {
  StompSessionProvider,
  useSubscription,
} from "react-stomp-hooks";

function App() {
  const [messages, setMessages] = useState([]);
  const [user, setUser] = useState({ name: "", color: "" });
  const [server, setServer] = useState("");

  const onMessageReceived = (msg) => {
    console.log("New Message Received!!"+msg);    
    setMessages(messages.concat(msg));
  };

  const handleLoginSubmit = (name) => {
    setUser({ name, color: randomColor() });
  };
  const serverOnChange = (e) => {
    setServer(e.target.value);
  };
  const handleMessageSubmit = async (msg) => {
    await chatApi
      .sendMessage(server, user.name, msg)
      .then((res) => {
        console.log("sent", res);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  const randomColor = () => {
    return "#" + Math.floor(Math.random() * 0xffffff).toString(16);
  };

  return (
    <>
      <h2>서버 : {server}</h2>
      <h2>닉네임 : {user.name}</h2>
      <input
        placeholder="서버 입력하세요"
        value={server}
        onChange={serverOnChange}
      />
      {user.name !== "" ? (
        <div className="chat-container">        
        <StompSessionProvider
          url={"http://k6b1021.p.ssafy.io:9998/my-chat"}
          //All options supported by @stomp/stompjs can be used here
        >
        <SubscribingComponent />
        </StompSessionProvider>
          <Input handleOnSubmit={handleMessageSubmit} />
          <Chat messages={messages} currentUser={user} />
        </div>
      ) : (
        <Login handleOnSubmit={handleLoginSubmit} />
      )}
    </>
  );
  function SubscribingComponent() {
    const [lastMessage, setLastMessage] = useState("No message received yet");
  
    //Subscribe to /topic/test, and use handler for all received messages
    //Note that all subscriptions made through the library are automatically removed when their owning component gets unmounted.
    //If the STOMP connection itself is lost they are however restored on reconnect.
    //You can also supply an array as the first parameter, which will subscribe to all destinations in the array
    useSubscription(`/topic/${server}`, (message) => onMessageReceived(JSON.parse(message.body)));
  }
}
export default App;
