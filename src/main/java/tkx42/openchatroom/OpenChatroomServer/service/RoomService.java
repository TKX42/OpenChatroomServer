package tkx42.openchatroom.OpenChatroomServer.service;

import org.springframework.stereotype.Service;
import tkx42.openchatroom.OpenChatroomServer.model.Room;
import tkx42.openchatroom.OpenChatroomServer.repository.RoomRepository;

import java.util.List;
import java.util.Objects;

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
        if(roomRepository.getRooms().contains(room)) return null;
        roomRepository.addRoom(room);
        return room;
    }

    public List<Room> getListedRooms() {
        return roomRepository.getListedRooms();
    }
}
