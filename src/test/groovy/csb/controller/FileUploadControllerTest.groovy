package csb.controller
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
/**
 * Created by dneufeld on 12/1/15.
 */
class FileUploadControllerTest {

    @Test
    public void pojoPing() throws Exception {
        FileUploadController controller = new FileUploadController()
        assert "FileUploadController is alive."==controller.ping()
    }

    @Test
    public void stackPing() throws Exception {
        FileUploadController controller = new FileUploadController()
        MockMvc mockMvc = standaloneSetup( controller ).build()
        assert mockMvc.perform(get( "/fileupload/ping" ))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType( "text/plain;charset=ISO-8859-1" )))
                .andExpect(content().string( "FileUploadController is alive." ))
    }

}
