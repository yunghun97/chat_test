import React from "react";

function Chat({ messages, currentUser }) {
  return (
    <div>
      <h3>{currentUser.name}의 채팅창</h3>
      {messages.map((msg, index) => (
        <div key={index}>
          <span
            style={{
              fontSize: "1.2rem",
              color:
                msg.author === currentUser.name ? currentUser.color : "#000000",
            }}
          >{`[${msg.author}]`}</span>
          <span style={{ fontSize: "1.2rem" }}>{` ${msg.content} `}</span>
          <span style={{ fontSize: "0.7rem" }}>{`(${msg.timestamp})`}</span>
        </div>
      ))}
    </div>
  );
}

export default Chat;
