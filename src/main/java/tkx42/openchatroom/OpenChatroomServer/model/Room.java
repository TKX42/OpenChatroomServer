package tkx42.openchatroom.OpenChatroomServer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ResponseStatusException;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Room {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
    private boolean listed;
    private int msgTimeout;
    @JsonIgnore
    private MessageList messageList;
    @ElementCollection
    private List<User> users;

    public Room(String name, boolean listed, int msgTimeout) {
        if (name.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is blank!");
        this.name = name;
        this.listed = listed;
        this.msgTimeout = msgTimeout;
        messageList = new MessageList();
        users = new ArrayList<>();
    }

    public Room() {
        messageList = new MessageList();
        users = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public boolean isListed() {
        return listed;
    }

    public void setListed(boolean listed) {
        this.listed = listed;
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

    public void setMessageList(MessageList messageList) {
        this.messageList = messageList;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean addUser(User user) {
        if(users.contains(user)) return false;
        return users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }
}
