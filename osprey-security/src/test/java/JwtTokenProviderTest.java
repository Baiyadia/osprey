import com.kaiqi.osprey.security.jwt.model.JwtUserDetails;
import com.kaiqi.osprey.security.jwt.token.JwtTokenProvider;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

/**
 * @author wangs
 * @date 2017/12/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JwtTestConfig.class)
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private final String TOKEN =
            "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1MDg4OTMzYS00MjFkLTQxZGMtYjQ5NC0yY2E5OTY4NmUyYjAiLCJ1aWQiOiJyN1JsVVE0OS9xU21zVU9WaTAxRXd3PT0iLCJwaG8iOiIxMjM0MzQzNDM0Iiwic3RhIjowLCJ2YXMiOjAsInBkdSI6MCwiaWF0IjoxNTE2ODA5NjAwLCJleHAiOjE1MTc0MTQ0MDB9.55VX4kUypmdnrf2AeIV7VqLAu9rAnKgml4ZxpWUYqFRU42AfaJSqdcKG69ioJxbVuYCJ7dNOFgDNWhGhs9VV3w";

    @Test
    public void generateTokenTest() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 12, 25, 0, 0, 0);
        final Date createdDate = calendar.getTime();

        final JwtUserDetails userDetails = JwtUserDetails.builder()
                                                         .userId(1000L)
                                                         .mobile("1234343434")
                                                         .created(createdDate)
                                                         .status(0)
                                                         .build();
        final String token = this.jwtTokenProvider.generateToken(userDetails);
        Assert.assertThat(token, IsEqual.equalTo(this.TOKEN));
    }

    @Test
    public void parseTokenTest() {
        final JwtUserDetails jwtUserDetails = this.jwtTokenProvider.getJwtUserDetails(this.TOKEN);
        Assert.assertThat(jwtUserDetails.getUserId(), IsEqual.equalTo(1000L));
    }
}
