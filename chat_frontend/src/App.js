import React, { useState } from "react";
import SockJsClient from "react-stomp";
import chatApi from "./services/chatapi";

import Chat from "./pages/Chat";
import Input from "./pages/Input";
import Login from "./pages/Login";

function App() {
  const [messages, setMessages] = useState([]);
  const [user, setUser] = useState({ name: "", color: "" });
  const [server, setServer] = useState("");

  const onMessageReceived = (msg) => {
    console.log("New Message Received!!", msg);
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
          <SockJsClient
            url={"http://localhost:9998/my-chat/"}
            topics={["/topic/group"]}
            onConnect={console.log("connected!")}
            onDisconnect={console.log("disconnected!")}
            onMessage={(msg) => onMessageReceived(msg)}
            debug={false}
          />
          <Input handleOnSubmit={handleMessageSubmit} />
          <Chat messages={messages} currentUser={user} />
        </div>
      ) : (
        <Login handleOnSubmit={handleLoginSubmit} />
      )}
    </>
  );
}

export default App;
