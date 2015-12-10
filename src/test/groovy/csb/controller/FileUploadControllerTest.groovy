package csb.controller
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

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

    @Test
    public void upload() throws Exception {

        def fileBytes = "LAT, LON, BAT_TTIME\n42.9946, -50.2891, 99.9".getBytes()
        MockMultipartFile uploadFile = new MockMultipartFile("file", "myxyzfile.xyz", null, fileBytes);

        FileUploadController controller = new FileUploadController()
        MockMvc mockMvc = standaloneSetup( controller ).build()
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/fileupload/upload")
                .file(uploadFile)
                .param("csbMetadataInput", "{\"shipname\":\"Kilo Moana\",\"soundermake\":\"\",\"imonumber\":\"\",\"soundermodel\":\"\",\"draft\":\"\",\"sounderserialno\":\"\",\"longitudinalOffsetFromGPStoSonar\":\"\",\"lateralOffsetFromGPStoSonar\":\"\",\"velocity\":\"\",\"gpsmake\":\"\",\"gpsmodel\":\"\",\"dataProvider\":\"SEAID\"}"))
                .andExpect(status().is(200))
                .andExpect(content().string("success"));
    }
}
