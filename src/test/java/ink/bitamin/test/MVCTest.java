package ink.bitamin.test;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:dispatcherServlet.xml"})
@Rollback(value = true)
@Transactional
public class MVCTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void pageMvcTest() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("password", "word");
        String jsonString = JSONObject.toJSONString(map);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/account/users/pageNum/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageSize", "12")
                .content(jsonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        mvcResult.getRequest().setCharacterEncoding("utf-8");
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);

    }


}
