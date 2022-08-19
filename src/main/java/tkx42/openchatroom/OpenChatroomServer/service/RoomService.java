package tkx42.openchatroom.OpenChatroomServer.service;

import org.springframework.stereotype.Service;
import tkx42.openchatroom.OpenChatroomServer.model.Message;
import tkx42.openchatroom.OpenChatroomServer.model.Room;
import tkx42.openchatroom.OpenChatroomServer.model.User;
import tkx42.openchatroom.OpenChatroomServer.repository.RoomRepository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public boolean roomNameIsAvailable(String name) {
        return roomRepository.getRooms().stream().noneMatch(room -> Objects.equals(room.getName(), name));
    }

    public Room addRoom(Room room) {
        if (roomRepository.getRooms().contains(room)) return null;
        roomRepository.addRoom(room);
        return room;
    }

    public Room getRoom(String name) {
        return roomRepository.getRooms().stream().filter(
                        room -> room.getName().equals(name))
                .findFirst().orElse(null);
    }

    public List<Room> getListedRooms() {
        return roomRepository.getListedRooms();
    }

    public List<Room> getRooms() {
        return roomRepository.getRooms();
    }

    public User getUser(Room room, UUID userUUID) {
        return room.getUsers().stream().filter(
                        user -> user.getUuid().equals(userUUID))
                .findFirst().orElse(null);
    }

    public boolean userJoinedRoom(User user, Room room) {
        return room.getUsers().stream().anyMatch(
                x -> x.getUuid().equals(user.getUuid()));
    }

    public boolean usernameIsAvailable(String name, Room room) {
        return room.getUsers().stream().noneMatch(user -> user.getName().equals(name));
    }

    public Message send(Message message, Room room, User user) {
        Message messageToSend = new Message(message.getContent(), ZonedDateTime.now(), user);
        return room.getMessageList().addMessage(messageToSend);
    }

    public void ping(User user) {
        user.setLastPing(LocalDateTime.now());
    }
}
