package tkx42.openchatroom.OpenChatroomServer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {
    @JsonIgnore     // UUID should not be exposed for example when retrieving listed rooms
    private final UUID uuid;
    private final String name;

    @JsonCreator
    public User(@JsonProperty("name") String name) {
        this.name = name;
        uuid = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    // Users of the same name are not allowed
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) return false;
        return ((User)obj).getName().equals(getName());
    }

    // hashCode shouldn't be used for comparison of two users (for example in HashSet). The overwritten equals method should be used instead
    public int hashCode() {
        return 0;
    }
}
