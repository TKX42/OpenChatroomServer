package tkx42.openchatroom.OpenChatroomServer.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tkx42.openchatroom.OpenChatroomServer.model.Room;
import tkx42.openchatroom.OpenChatroomServer.model.User;
import tkx42.openchatroom.OpenChatroomServer.service.RoomService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomResource {
    private final RoomService roomService;

    public RoomResource(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        if(roomService.roomNameExists(room.getName())) {
            // rooms with the same name are not allowed
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(roomService.addRoom(room));
    }

    @GetMapping("/listed")
    public ResponseEntity<List<Room>> listedRooms() {
        return ResponseEntity.ok(roomService.getListedRooms());
    }

    @PostMapping("/join")
    public ResponseEntity<UUID> joinRoom(@RequestBody User user, @RequestParam String roomName) {
        Room room = roomService.getRoom(roomName);
        if(room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if(!room.addUser(user)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(user.getUuid());
    }
}
