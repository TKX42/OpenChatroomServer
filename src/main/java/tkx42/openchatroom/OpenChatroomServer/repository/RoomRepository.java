package tkx42.openchatroom.OpenChatroomServer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tkx42.openchatroom.OpenChatroomServer.model.Room;

public interface RoomRepository extends MongoRepository<Room, String> {
}
