package tkx42.openchatroom.OpenChatroomServer.model;

/*
This class is used to save the user of a message he has written. We don't want to store this via the User class because these objects are perhaps temporary
(for example, an inactive user who has been offline for x days (-> tkx42.openchatroom.OpenChatroomServer.tasks.UserOnlineCheckTask) is removed from the user list of a room.
containing a reference to this user in an old message would be bad)
 */

import javax.persistence.Embeddable;

@Embeddable
public class MessageCreator {
    private String name;

    public MessageCreator() {
    }

    public MessageCreator(User user) {
        this.name = user.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
