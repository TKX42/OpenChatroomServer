package tkx42.openchatroom.OpenChatroomServer.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private final UUID uuid;
    private final String content;
    private final LocalDateTime sent;
    private final User user;

    public Message(String content, LocalDateTime sent, User user) {
        this.content = content;
        this.sent = sent;
        this.user = user;
        uuid = UUID.randomUUID();
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

    public UUID getUuid() {
        return uuid;
    }
}
