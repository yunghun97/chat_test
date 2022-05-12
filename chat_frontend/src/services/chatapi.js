import Axios from "axios";

const api = Axios.create({
  baseURL: "http://k6b1012.p.ssafy.io:9998/kafka",
});

const chatAPI = {
  getMessages: (groupId) => {
    console.log("Calling get messages from API");
  },

  sendMessage: async (server, username, text) => {
    let msg = {
      server: server,
      author: username,
      content: text,
    };
    return await api.post(`/publish`, msg, {
      headers: { "Content-Type": "application/json" },
    });
  },  
};

export default chatAPI;
