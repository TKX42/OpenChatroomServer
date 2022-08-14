package tkx42.openchatroom.OpenChatroomServer.model;

public class Room {
    private final String name;
    private final boolean listed;
    private int msgTimeout;

    public Room(String name, boolean listed, int msgTimeout) {
        this.name = name;
        this.listed = listed;
        this.msgTimeout = msgTimeout;
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
}
