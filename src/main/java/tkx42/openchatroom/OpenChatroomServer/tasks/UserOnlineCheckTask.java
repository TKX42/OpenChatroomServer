package tkx42.openchatroom.OpenChatroomServer.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tkx42.openchatroom.OpenChatroomServer.model.Room;
import tkx42.openchatroom.OpenChatroomServer.model.User;
import tkx42.openchatroom.OpenChatroomServer.service.RoomService;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class UserOnlineCheckTask {
    private final int INTERVAL = 10000;
    private static final Logger log = LoggerFactory.getLogger(UserOnlineCheckTask.class);
    private final long onlinePingInterval = 6000;
    private final long maxOfflineDuration = (3 * 24 * 60 * 60 * 1000); // 3 days
    private final RoomService roomService;

    public UserOnlineCheckTask(RoomService roomService) {
        this.roomService = roomService;
    }

    @Scheduled(fixedRate = INTERVAL)
    public void checkUserOnlineStatus() {
        for (Room room : roomService.getRooms()) {
            for (User user : room.getUsers()) {
                // Users who haven't ping for longer than onlinePingInterval are set to be offline
                long timeSinceLastPing = Duration.between(user.getLastPing(), LocalDateTime.now()).toMillis();
                user.setOnline(timeSinceLastPing < onlinePingInterval);
                log.info("Set online status of {} to {}", user.getName(), user.isOnline());

                // After the maximum offline duration, the user gets removed from the room and the name freed
                if (timeSinceLastPing > maxOfflineDuration) {
                    log.info("Removing user {}", user.getName());
                    room.getUsers().remove(user);
                }
            }
        }
    }
}