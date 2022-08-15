package tkx42.openchatroom.OpenChatroomServer.model;

import java.util.List;

public class Room {
    private final String name;
    private final boolean listed;
    private int msgTimeout;
    private final MessageList messageList;

    public Room(String name, boolean listed, int msgTimeout) {
        this.name = name;
        this.listed = listed;
        this.msgTimeout = msgTimeout;
        messageList = new MessageList();
    }

    public String getName() {
        return name;
    }

    public boolean isListed() {
        return listed;
    }

    public int getMsgTimeout() {
        return msgTimeout;
    }

    public void setMsgTimeout(int msgTimeout) {
        this.msgTimeout = msgTimeout;
    }

    public MessageList getMessageList() {
        return messageList;
    }
}
