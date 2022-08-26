package tkx42.openchatroom.OpenChatroomServer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    @JsonIgnore     // UUID should not be exposed for example when retrieving listed rooms
    private UUID uuid;
    @JsonProperty(required = true)
    @NonNull
    private final String name;
    private boolean online;
    private LocalDateTime lastPing;

    @JsonCreator
    public User(@JsonProperty("name") String name) {
        if (name.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is blank!");
        this.name = name;
        uuid = UUID.randomUUID();
        lastPing = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public LocalDateTime getLastPing() {
        return lastPing;
    }

    public void setLastPing(LocalDateTime lastPing) {
        this.lastPing = lastPing;
    }

    // Users of the same name are not allowed
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) return false;
        return ((User) obj).getName().equals(getName());
    }

    // hashCode shouldn't be used for comparison of two users (for example in HashSet). The overwritten equals method should be used instead
    public int hashCode() {
        return 0;
    }
}
