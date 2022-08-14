package tkx42.openchatroom.OpenChatroomServer.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainResource {
    @GetMapping("/")
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok().build();
    }
}
