import Axios from "axios";

const api = Axios.create({
  baseURL: "http://localhost:9998/kafka",
});

const chatAPI = {
  getMessages: (groupId) => {
    console.log("Calling get messages from API");
  },

  sendMessage: (server, username, text) => {
    let msg = {      
      server: server,
      author: username,
      content: text,
    };
    return api.post(`/publish`, msg, {
      headers: { "Content-Type": "application/json" },
    });
  },
};

export default chatAPI;