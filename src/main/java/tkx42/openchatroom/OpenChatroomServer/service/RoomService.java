package tkx42.openchatroom.OpenChatroomServer.service;

import org.springframework.stereotype.Service;
import tkx42.openchatroom.OpenChatroomServer.model.Room;
import tkx42.openchatroom.OpenChatroomServer.model.User;
import tkx42.openchatroom.OpenChatroomServer.repository.RoomRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public boolean roomNameExists(String name) {
        return roomRepository.getRooms().stream().anyMatch(room -> Objects.equals(room.getName(), name));
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

    public User getUser(Room room, UUID userUUID) {
        return room.getUsers().stream().filter(
                        user -> user.getUuid().equals(userUUID))
                .findFirst().orElse(null);
    }

    public boolean userJoinedRoom(User user, Room room) {
        return room.getUsers().stream().anyMatch(
                x -> x.getUuid().equals(user.getUuid()));
    }
}
