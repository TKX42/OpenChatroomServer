package tkx42.openchatroom.OpenChatroomServer.model;

import java.time.LocalDateTime;

public class Message {
    private final String content;
    private final LocalDateTime sent;
    private final User user;

    public Message(String content, LocalDateTime sent, User user) {
        this.content = content;
        this.sent = sent;
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSent() {
        return sent;
    }

    public User getUser() {
        return user;
    }
}
