package tkx42.openchatroom.OpenChatroomServer.model;

import java.util.ArrayList;
import java.util.List;

public class MessageList {
    private final int CHUNK_SIZE = 16;
    private final List<Message> messages;

    public MessageList() {
        messages = new ArrayList<>();
    }

    public Message addMessage(Message message) {
        if (messages.contains(message)) return null;
        messages.add(message);
        return message;
    }

    private int chunkEnd(int index, int size) {
        return size - index * CHUNK_SIZE;
    }

    public List<Message> getChunk(int index) {
        int start = (index + 1) * CHUNK_SIZE;

        if (start - 16 > messages.size()) return null;

        start = Math.max(messages.size() - start, 0);

        List<Message> result = new ArrayList<>();
        for (int i = start; i < chunkEnd(index, messages.size()); i++) {
            result.add((messages.get(i)));
        }

        return result;
    }

    public int getChunkSize() {
        return CHUNK_SIZE;
    }
}
