package tkx42.openchatroom.OpenChatroomServer.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class MainResource {
    @GetMapping("/")
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/deployment")
    public String deploymentTest() {
        return "automatic deployment test 3";
    }
}
