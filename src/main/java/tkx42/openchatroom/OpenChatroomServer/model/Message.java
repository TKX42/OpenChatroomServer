package tkx42.openchatroom.OpenChatroomServer.model;

import java.time.LocalDate;

public class Message {
    private final String content;
    private final LocalDate sent;
    private final User user;

    public Message(String content, LocalDate sent, User user) {
        this.content = content;
        this.sent = sent;
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getSent() {
        return sent;
    }

    public User getUser() {
        return user;
    }
}
