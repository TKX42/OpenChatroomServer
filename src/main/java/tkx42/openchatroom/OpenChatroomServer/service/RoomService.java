package tkx42.openchatroom.OpenChatroomServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tkx42.openchatroom.OpenChatroomServer.model.Message;
import tkx42.openchatroom.OpenChatroomServer.model.MessageCreator;
import tkx42.openchatroom.OpenChatroomServer.model.Room;
import tkx42.openchatroom.OpenChatroomServer.model.User;
import tkx42.openchatroom.OpenChatroomServer.repository.RoomRepository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public boolean roomNameIsAvailable(String name) {
        return roomRepository.findAll().stream().noneMatch(room -> Objects.equals(room.getName(), name));
    }

    public Room addRoom(Room room) {
        if (roomRepository.findAll().contains(room)) return null;
        roomRepository.save(room);
        return room;
    }

    public Room getRoom(String name) {
        return roomRepository.findAll().stream().filter(
                        room -> room.getName().equals(name))
                .findFirst().orElse(null);
    }

    public List<Room> getListedRooms() {
        return roomRepository.findAll().stream().filter(Room::isListed).collect(Collectors.toList());
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
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

    public void join(User user, Room room) {
        if (!room.addUser(user)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
        }
        roomRepository.save(room);
    }

    public Message send(Message message, Room room, User user) {
        MessageCreator creator = new MessageCreator(user);
        Message messageToSend = new Message(message.getContent(), ZonedDateTime.now(), creator);
        Message result = room.getMessageList().addMessage(messageToSend);
        roomRepository.save(room);
        return result;
    }

    public void ping(User user, Room room) {
        user.setLastPing(LocalDateTime.now());
        roomRepository.save(room);
    }

    public void setUserOnline(User user, Room room, boolean online) {
        user.setOnline(online);
        roomRepository.save(room);
    }

    public void removeUser(User user, Room room) {
        room.getUsers().remove(user);
        roomRepository.save(room);
    }
}
