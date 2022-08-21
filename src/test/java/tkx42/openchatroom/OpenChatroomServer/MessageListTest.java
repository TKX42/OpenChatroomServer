package tkx42.openchatroom.OpenChatroomServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tkx42.openchatroom.OpenChatroomServer.model.Message;
import tkx42.openchatroom.OpenChatroomServer.model.MessageList;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MessageListTest {
    MessageList messageList;

    @BeforeEach
    void beforeEach() {
        messageList = new MessageList();
    }

    @Test
    void addTestSmallerThanChunk() {
        messageList.addMessage(new Message("Content", ZonedDateTime.now(), null));
        messageList.addMessage(new Message("Content", ZonedDateTime.now(), null));
        assertEquals(2, messageList.getChunk(0).size());
    }

    @Test
    void basicChunkTest() {
        Message msg1 = new Message("Content1", ZonedDateTime.now(), null);
        Message msg2 = new Message("Content2", ZonedDateTime.now(), null);
        Message msg3 = new Message("Content3", ZonedDateTime.now(), null);
        Message msg4 = new Message("Content4", ZonedDateTime.now(), null);
        messageList.addMessage(msg1);
        messageList.addMessage(msg2);
        messageList.addMessage(msg3);
        messageList.addMessage(msg4);
        assertEquals(msg1, messageList.getChunk(0).get(0));
        assertEquals(msg2, messageList.getChunk(0).get(1));
        assertEquals(msg3, messageList.getChunk(0).get(2));
        assertEquals(msg4, messageList.getChunk(0).get(3));
    }

    // Test the order of elements inside a chunk
    @Test
    void chunk2Test() {
        for (int i = 0; i < messageList.getChunkSize() * 3; i++) {
            messageList.addMessage(new Message("Content #" + i, null, null));
        }

        assertEquals("Content #27", messageList.getChunk(1).get(11).getContent());
        assertEquals("Content #28", messageList.getChunk(1).get(12).getContent());
    }

    @Test
    void outOfRangeChunkTest() {
        messageList.addMessage(new Message("Content", ZonedDateTime.now(), null));
        messageList.addMessage(new Message("Content", ZonedDateTime.now(), null));
        assertNull(messageList.getChunk(1));
    }

    // We have a message list containing 26 messages. First Chunk should be the last 16 msgs, second chunk should be the first 10 msgs
    // There should not be a third chunk
    @Test
    void notWholeChunkTest() {
        for (int i = 0; i < messageList.getChunkSize() + 10; i++) {
            messageList.addMessage(new Message("Content", ZonedDateTime.now(), null));
        }

        assertEquals(16, messageList.getChunk(0).size());
        assertEquals(10, messageList.getChunk(1).size());
        assertNull(messageList.getChunk(2));
    }

    @Test
    void notWholeChunkTest2() {
        for (int i = 0; i < messageList.getChunkSize(); i++) {
            messageList.addMessage(new Message("Content #" + i, ZonedDateTime.now(), null));
        }

        messageList.addMessage(new Message("EXTRA #1", ZonedDateTime.now(), null));
        messageList.addMessage(new Message("EXTRA #2", ZonedDateTime.now(), null));

        assertEquals(2, messageList.getChunk(1).size());
        assertEquals("Content #0", messageList.getChunk(1).get(0).getContent());
        assertEquals("Content #1", messageList.getChunk(1).get(1).getContent());

        assertEquals("Content #2", messageList.getChunk(0).get(0).getContent());
        assertEquals("EXTRA #1", messageList.getChunk(0).get(14).getContent());
        assertEquals("EXTRA #2", messageList.getChunk(0).get(15).getContent());
    }
}
