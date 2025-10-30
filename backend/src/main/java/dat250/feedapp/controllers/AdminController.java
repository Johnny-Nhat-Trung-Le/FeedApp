package dat250.feedapp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class AdminController {
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin/secret")
    public String secret() {
        return "This is a secret message only for ADMIN";
    }
}
