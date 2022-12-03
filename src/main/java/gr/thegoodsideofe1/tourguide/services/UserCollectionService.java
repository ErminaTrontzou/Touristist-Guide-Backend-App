package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.aes.AES_ENCRYPTION;
import gr.thegoodsideofe1.tourguide.controllers.Responder;
import gr.thegoodsideofe1.tourguide.entities.User;
import gr.thegoodsideofe1.tourguide.entities.UserCollection;
import gr.thegoodsideofe1.tourguide.repositories.UserCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserCollectionService {
    @Autowired
    UserCollectionRepository userCollectionRepository;
    @Autowired
    AES_ENCRYPTION aes_encryption;
    @Autowired
    UserService userService;

    public ResponseEntity<?> listAllCollections(Map<String, String> requestBody) {
        if (requestBody.containsKey("Bearer") && !requestBody.get("Bearer").isEmpty()) {
            String requestJWTToken = requestBody.get("Bearer");
            try {
                String[] userDetails = this.getUserDetailsFromJWT(requestJWTToken);
                User loginUser = userService.getUserByParams(userDetails[1], userDetails[0], userDetails[2], userDetails[3]);
                if (loginUser != null) {
                    //User is Logged IN
                    if (loginUser.getIsAdmin()) {
                        //User is Admin
                        return new ResponseEntity<>(userCollectionRepository.findAll(), HttpStatus.OK);
                    }
                    //User is NOT Admin
                    return new ResponseEntity<>(noAccessToInformation(), HttpStatus.UNAUTHORIZED);
                }
                //User is NOT Logged In
                return new ResponseEntity<>(userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            } catch (Exception e){
                //Exception During JWT Decrypt
                return new ResponseEntity<>(userIsNotLoggedIN(), HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(noValidJWTResponse(), HttpStatus.UNAUTHORIZED);
    }

    public void saveCollection(UserCollection collection){
        userCollectionRepository.save(collection);
    }

    public UserCollection getCollection(Integer id){
        return userCollectionRepository.findById(id).get();
    }

    public void deleteCollection(Integer id){
        userCollectionRepository.deleteById(id);
    }

    protected String[] getUserDetailsFromJWT(String token) throws Exception {
        String decryptedString = aes_encryption.decrypt(token);
        return decryptedString.split(",");
    }

    protected HashMap<String, String> noValidJWTResponse(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "Provide Valid JWT");
        return hashMapToReturn;
    }

    protected HashMap<String, String> userIsNotLoggedIN(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "NOT Valid JWT");
        return hashMapToReturn;
    }

    protected HashMap<String, String> noAccessToInformation(){
        HashMap<String, String> hashMapToReturn = new HashMap<>();
        hashMapToReturn.put("status", "error");
        hashMapToReturn.put("message", "You have no access to this information");
        return hashMapToReturn;
    }
}
