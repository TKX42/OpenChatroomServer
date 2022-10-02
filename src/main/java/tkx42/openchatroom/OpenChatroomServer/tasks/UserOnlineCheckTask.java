package tkx42.openchatroom.OpenChatroomServer.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tkx42.openchatroom.OpenChatroomServer.model.Room;
import tkx42.openchatroom.OpenChatroomServer.model.User;
import tkx42.openchatroom.OpenChatroomServer.service.RoomService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional  // https://stackoverflow.com/questions/11746499/how-to-solve-the-failed-to-lazily-initialize-a-collection-of-role-hibernate-ex
public class UserOnlineCheckTask {
    private final int INTERVAL = 3100;
    private static final Logger log = LoggerFactory.getLogger(UserOnlineCheckTask.class);
    private final long onlinePingInterval = 3000;
    private final long maxOfflineDuration = (3 * 24 * 60 * 60 * 1000); // 3 days
    private final RoomService roomService;

    public UserOnlineCheckTask(RoomService roomService) {
        this.roomService = roomService;
    }

    @Scheduled(fixedRate = INTERVAL)
    public void checkUserOnlineStatus() {
        for (Room room : roomService.getRooms()) {
            List<User> toBeRemoved = new ArrayList<>();
            List<User> users = room.getUsers();
            for (int i = 0; i < users.size(); i++) {        // for loop to fix ConcurrentModificationException
                User user = users.get(i);
                // Users who didn't ping for longer than onlinePingInterval are set to be offline
                long timeSinceLastPing = Duration.between(user.getLastPing(), LocalDateTime.now()).toMillis();
                boolean online = timeSinceLastPing < onlinePingInterval;

                if (online != user.isOnline()) {
                    log.info("Set online status of {} to {}", user.getName(), online);
                }
                roomService.setUserOnline(user, room, online);

                // After the maximum offline duration, the user gets removed from the room and the name freed
                if (timeSinceLastPing > maxOfflineDuration) {
                    log.info("Listing user {} for removal", user.getName());
                    toBeRemoved.add(user);
                }
            }

            for(User user : toBeRemoved) {
                log.info("Removing user {}", user.getName());
                roomService.removeUser(user, room);
            }
        }
    }
}