package tkx42.openchatroom.OpenChatroomServer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

public class MessageList {
    @Id
    String id;
    @Transient
    private final int chunkSize;
    private List<Message> messages;     // not final because mongodb

    public MessageList() {
        messages = new ArrayList<>();
        chunkSize = 16;
    }

    public Message addMessage(Message message) {
        if (messages.contains(message)) return null;
        messages.add(message);
        return message;
    }

    private int chunkEnd(int index, int size) {
        return size - index * chunkSize;
    }

    public List<Message> getChunk(int index) {
        int start = (index + 1) * chunkSize;

        if (start - 16 > messages.size()) return null;

        start = Math.max(messages.size() - start, 0);

        List<Message> result = new ArrayList<>();
        for (int i = start; i < chunkEnd(index, messages.size()); i++) {
            result.add((messages.get(i)));
        }

        return result;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public String getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
