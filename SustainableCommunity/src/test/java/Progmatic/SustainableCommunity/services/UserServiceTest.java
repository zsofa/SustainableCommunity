package Progmatic.SustainableCommunity.services;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserServiceTest {


    @Mock
    UserService userService;

    /*@Test
    void getUserByEmail() {
        this.entityManager.persist(new AppUser("userName", "email", UserRole.SELLER));
    }*/

   /* @Test
    void entityTest() throws EmailNotFoundException {
        Mockito.when(userService.loadUserByEmail("user@gmail.com")).thenReturn(
                new AppUser("user","user@gmail.com", UserRole.CUSTOMER));
    }*/




    // le akarok tesztelni lekérdezéseket az adatbázisból, hogy működnek e!!
    // repository lekérdezés tesztelést megtaláltam

}