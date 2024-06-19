package shinhan.server_parent.domain.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @GetMapping("/temp")
    public String getStock(){
        return "adf";
    }


}
