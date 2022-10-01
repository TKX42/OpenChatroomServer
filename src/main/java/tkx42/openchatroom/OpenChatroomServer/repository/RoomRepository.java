package tkx42.openchatroom.OpenChatroomServer.repository;

import org.springframework.data.repository.CrudRepository;
import tkx42.openchatroom.OpenChatroomServer.model.Room;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, String> {
    List<Room> findAll();
}
