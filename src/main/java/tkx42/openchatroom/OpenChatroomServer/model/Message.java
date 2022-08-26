package tkx42.openchatroom.OpenChatroomServer.model;

import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;

public class Message {
    @Id
    private String id;
    private final String content;
    private final ZonedDateTime sent;
    private final MessageCreator creator;

    public Message(String content, ZonedDateTime sent, MessageCreator creator) {
        this.content = content;
        this.sent = sent;
        this.creator = creator;
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

    public String getId() {
        return id;
    }
}
