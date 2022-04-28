import Axios from "axios";

const api = Axios.create({
  baseURL: "http://k6b102.p.ssafy.io:9998/kafka",
});

const chatAPI = {
  getMessages: (groupId) => {
    console.log("Calling get messages from API");
    return api.get(`/messages/${groupId}`);
  },

  sendMessage: (username, text) => {
    let msg = {
      author: username,
      content: text,
    };
    return api.post(`/publish`, msg, {
      headers: { "Content-Type": "application/json" },
    });
  },
};

export default chatAPI;