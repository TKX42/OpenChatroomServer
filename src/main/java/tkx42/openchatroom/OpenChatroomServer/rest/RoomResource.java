package tkx42.openchatroom.OpenChatroomServer.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tkx42.openchatroom.OpenChatroomServer.model.Room;
import tkx42.openchatroom.OpenChatroomServer.service.RoomService;

import java.util.List;

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
}
