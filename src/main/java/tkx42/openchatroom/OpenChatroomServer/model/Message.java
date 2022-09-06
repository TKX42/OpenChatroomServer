package tkx42.openchatroom.OpenChatroomServer.model;

import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Message {
    @Id
    private UUID uuid;
    private final String content;
    private final ZonedDateTime sent;
    private final MessageCreator creator;

    public Message(String content, ZonedDateTime sent, MessageCreator creator) {
        this.content = content;
        this.sent = sent;
        this.creator = creator;
        this.uuid = UUID.randomUUID();
    }

    public String getContent() {
        return content;
    }

    public ZonedDateTime getSent() {
        return sent;
    }

    public MessageCreator getCreator() {
        return creator;
    }

    public UUID getUuid() {
        return uuid;
    }
}
