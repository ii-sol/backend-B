package sinhan.server3.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @GetMapping("/temp")
    public String getStock(){
        return "addff";
    }

}
