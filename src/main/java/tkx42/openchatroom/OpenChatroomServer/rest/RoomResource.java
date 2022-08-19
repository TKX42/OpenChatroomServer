package tkx42.openchatroom.OpenChatroomServer.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tkx42.openchatroom.OpenChatroomServer.model.Message;
import tkx42.openchatroom.OpenChatroomServer.model.Room;
import tkx42.openchatroom.OpenChatroomServer.model.User;
import tkx42.openchatroom.OpenChatroomServer.service.RoomService;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(path = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomResource {
    private final RoomService roomService;

    public RoomResource(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        if (!roomService.roomNameIsAvailable(room.getName())) {
            // rooms with the same name are not allowed
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Room with same name already exists!");
        }

        return ResponseEntity.ok(roomService.addRoom(room));
    }

    @GetMapping("/listed")
    public ResponseEntity<List<Room>> listedRooms() {
        return ResponseEntity.ok(roomService.getListedRooms());
    }

    @PostMapping("/{room}/join")
    public ResponseEntity<UUID> joinRoom(@PathVariable(name = "room") String roomName, @RequestBody User user) {
        Room room = getRoom(roomName);

        if (!room.addUser(user)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
        }
        user.setOnline(true);

        return ResponseEntity.ok(user.getUuid());
    }

    @PostMapping("/{room}/send")
    public ResponseEntity<Message> send(@PathVariable(name = "room") String roomName, @RequestBody Message message, @RequestHeader(name = "user") UUID userUUID) {
        Room room = getRoom(roomName);
        User user = getUser(userUUID, room);

        return ResponseEntity.ok(roomService.send(message, room, user));
    }

    @GetMapping("/{room}/messages")
    public ResponseEntity<List<Message>> messages(@PathVariable(name = "room") String roomName, @RequestHeader(name = "user") UUID userUUID, @RequestParam(required = false, defaultValue = "0") int chunk) {
        Room room = getRoom(roomName);
        User user = getUser(userUUID, room);

        if (!roomService.userJoinedRoom(user, room))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User didn't join room!");

        return ResponseEntity.ok(room.getMessageList().getChunk(chunk));
    }

    @GetMapping("/{room}/{name}/available")
    public ResponseEntity<Boolean> nameIsAvailable(@PathVariable(name = "room") String roomName, @PathVariable String name) {
        Room room = getRoom(roomName);
        return ResponseEntity.ok(roomService.usernameIsAvailable(name, room));
    }

    @GetMapping("/{room}")
    public ResponseEntity<Room> room(@PathVariable(name = "room") String roomName) {
        Room room = getRoom(roomName);
        return ResponseEntity.ok(room);
    }

    @PostMapping("/{room}/ping")
    public ResponseEntity<Boolean> ping(@PathVariable(name = "room") String roomName, @RequestHeader(name = "user") UUID userUUID) {
        Room room = getRoom(roomName);
        User user = getUser(userUUID, room);
        roomService.ping(user);
        return ResponseEntity.ok(true);
    }

    private Room getRoom(String roomName) {
        Room room = roomService.getRoom(roomName);
        if (room == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find room.");
        return room;
    }

    private User getUser(UUID userUUID, Room room) {
        User user = roomService.getUser(room, userUUID);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find user.");
        return user;
    }


    public void checkObject(Object obj) {
        if (obj == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
