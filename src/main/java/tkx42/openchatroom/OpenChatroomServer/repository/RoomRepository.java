package tkx42.openchatroom.OpenChatroomServer.repository;

import org.springframework.stereotype.Component;
import tkx42.openchatroom.OpenChatroomServer.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomRepository {
    private final List<Room> rooms;

    public RoomRepository() {
        rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Room> getListedRooms() {
        return rooms.stream().filter(Room::isListed).collect(Collectors.toList());
    }
}
