package tkx42.openchatroom.OpenChatroomServer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;

public class Room {
    @NonNull
    private final String name;
    private final boolean listed;
    private int msgTimeout;
    @JsonIgnore
    private final MessageList messageList;
    private final HashSet<User> users;

    public Room(String name, boolean listed, int msgTimeout) {
        if (name.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is blank!");
        this.name = name;
        this.listed = listed;
        this.msgTimeout = msgTimeout;
        messageList = new MessageList();
        users = new HashSet<>();
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

    public boolean addUser(User user) {
        return users.add(user);
    }

    public HashSet<User> getUsers() {
        return users;
    }
}
