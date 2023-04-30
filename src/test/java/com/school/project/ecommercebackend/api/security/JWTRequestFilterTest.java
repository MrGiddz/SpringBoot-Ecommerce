package com.school.project.ecommercebackend.api.security;

import com.school.project.ecommercebackend.model.LocalUser;
import com.school.project.ecommercebackend.model.dao.LocalUserDAO;
import com.school.project.ecommercebackend.service.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JWTRequestFilterTest {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private LocalUserDAO localUserDAO;
    private final String AUTHENTICATED_PATH = "/auth/me";

    @Test
    public void testUnauthenticatedRequest() throws Exception{
        mvc.perform(get(AUTHENTICATED_PATH)).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void testBadToken() throws Exception{
        mvc.perform(get(AUTHENTICATED_PATH).header("Authorization", "BadTokenThatIsNotValid"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        mvc.perform(get(AUTHENTICATED_PATH).header("Authorization","Bearer BadTokenThatIsNotValid"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void testUnverifiedUser() throws Exception{
        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserB").get();
        String token = jwtService.generateJWT(user);
        mvc.perform(get(AUTHENTICATED_PATH).header("Authorization", "Bearer " + token))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    public void testsuccessful() throws Exception {
        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateJWT(user);
        mvc.perform (get (AUTHENTICATED_PATH).header("Authorization", "Bearer " + token))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}
