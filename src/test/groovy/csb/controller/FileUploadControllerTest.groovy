package csb.controller

import org.springframework.http.MediaType
import org.springframework.util.MimeType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import org.springframework.test.web.servlet.MockMvc

import org.junit.Test

/**
 * Created by dneufeld on 12/1/15.
 */
class FileUploadControllerTest {

    @Test
    public void testPojoPing() throws Exception {
        FileUploadController controller = new FileUploadController()
        assert "FileUploadController is alive."==controller.ping()
    }

    @Test
    public void testFullStackPing() throws Exception {
        FileUploadController controller = new FileUploadController()
        MockMvc mockMvc = standaloneSetup( controller ).build()
        assert mockMvc.perform(get( "/fileupload/ping" ))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType( "text/plain;charset=ISO-8859-1" )))
                .andExpect(content().string( "FileUploadController is alive." ))
    }

}
