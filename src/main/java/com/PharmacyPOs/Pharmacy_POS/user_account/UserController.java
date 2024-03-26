package com.PharmacyPOs.Pharmacy_POS.user_account;

import com.PharmacyPOs.Pharmacy_POS.stock_management.Product;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//import org.springframework.http.ResponseEntity;
//import com.PharmacyPOs.Pharmacy_POS.user_account.PwdConfiguration;
import java.lang.String;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@CrossOrigin
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private final UserService userService;

//    private final PwdConfiguration pwdConfiguration;

    public UserController(UserService userService) {
//        this.pwdConfiguration = pwdConfiguration;
        this.userService = userService;
    }


    @GetMapping("/")
    public String Home(){
        return "/signup";
    }

    @GetMapping("/signup")
    public String UserSignUp(){
        return "signup";
    }

    @GetMapping("/login")
    public String Userlogin(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/dash")
    public String Dashboard(User user){
        return "dash";
    }

    @GetMapping("/pos.html")
    public String POS(User user){
        return "pos";
    }

    @GetMapping("/stocks.html")
    public String Stocks(Model user){
        user.addAttribute("product", new Product());
        return "stocks.html";
    }

    @GetMapping("/reports.html")
    public String FinancialReport(User user){
        return "reports";
    }

    @GetMapping("/settings.html")
    public String Settings(User user){
        return "settings";
    }

    //  Register
    @PostMapping("/register")
    public RedirectView registerUser(@RequestParam("email") String email , @RequestParam("password") String password, @RequestParam("firstname") String firstname, @RequestParam("surname") String surname,
                                     @RequestParam("phoneNumber") String phoneNumber, @RequestParam("pharmacyName") String pharmacyName,
                                     @RequestParam("location") String location, @RequestParam("pharmacyType") String pharmacyType){

//        String hashPassword = passwordEncoder(password);

        User registeredUser = userService.registerUser(email, password, firstname, surname, phoneNumber, location, pharmacyName, pharmacyType);
        return new RedirectView("/dash");
//        return null;

    }

    //  Login
    @PostMapping("/login")
    public RedirectView loginUser (@RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   RedirectAttributes redirectAttributes){

        User registeredUser = userService.findUserByEmail(email);
        if (registeredUser == null){
            // user not found, return a not found response
           return new RedirectView("/register");
       }

//       boolean checkPassword = passwordDecoder(password, registeredUser.getPassword());

        if(password.equals(registeredUser.getPassword())){
            // Add the logged-in user infomation to a flash attribute
            redirectAttributes.addFlashAttribute("user", registeredUser);

            // Redirect to the dashboard upon successful login
           return new RedirectView("/dash");
        } else{
            // Incorrect password, return unauthorized response
            return new RedirectView("/login");
        }

        /*if(!checkPassword){
            return new RedirectView("/login");
        }

        // Add the logged-in user infomation to a flash attribute
        redirectAttributes.addFlashAttribute("user", registeredUser);

        // Redirect to the dashboard upon successful login
        return new RedirectView("/admin");*/


  }

    public  String passwordEncoder (String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public boolean passwordDecoder(String enteredPassword, String hashedPassword){
        return BCrypt.checkpw(enteredPassword, hashedPassword);
    }

}
