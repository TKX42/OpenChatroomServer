package tkx42.openchatroom.OpenChatroomServer.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Embeddable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Embeddable
public class Message {
    @Id
    private UUID uuid;
    private String content;
    private ZonedDateTime sent;
    private MessageCreator creator;

    public Message(String content, ZonedDateTime sent, MessageCreator creator) {
        this.content = content;
        this.sent = sent;
        this.creator = creator;
        this.uuid = UUID.randomUUID();
    }

    public Message() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getSent() {
        return sent;
    }

    public void setSent(ZonedDateTime sent) {
        this.sent = sent;
    }

    public MessageCreator getCreator() {
        return creator;
    }

    public void setCreator(MessageCreator creator) {
        this.creator = creator;
    }
}
